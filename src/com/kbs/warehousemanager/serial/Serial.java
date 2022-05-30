package com.kbs.warehousemanager.serial;

import com.fazecast.jSerialComm.SerialPort;
import arduino.Arduino;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.function.Consumer;

public class Serial
{
	private Arduino arduino;
	private boolean listening;
	private Thread serialListenerThread;
	private boolean OK;

	public final Object lock = new Object();

	/**
	 * Connect to a specific port
	 * @param port Comm port to connect to
	 */
	public Serial(SerialPort port)
	{
		connectTo(port);
	}

	/**
	 * Create a new serial that has no connection
	 */
	public Serial()
	{
		OK = false;
	}

	/**
	 * Connect to the first port available
	 */
	public static SerialPort[] getAvailableSerialPorts() throws IOException
	{
		SerialPort[] availablePorts = SerialPort.getCommPorts();
		if (availablePorts.length == 0)
		{
			throw new IOException("There are no ports to connect to");
		}

		return availablePorts;
	}

	/**
	 * Is the serial port available for read?
	 * @return Whether the connection was okay
	 */
	public boolean good()
	{
		return OK;
	}

	/**
	 * Send text to the arduino, \n must be manually added
	 * @param string Text to send
	 */
	public void send(String string)
	{
		if (!OK)
		{
			throw new IllegalStateException("Not yet initialised");
		}

		if (string.charAt(string.length()-1) != '\n')
		{
			System.err.println("String sent to Arduino did NOT contain a LF!");
		}

		// Synchronised: Do not allow port access when busy (causes deadlock)
		synchronized (lock)
		{
			arduino.serialWrite(string);
		}
	}

	/**
	 * Close the comm port & listener
	 */
	public void close()
	{
		if (!OK)
		{
			throw new IllegalStateException("Not yet initialised");
		}

		listening = false;
		if (serialListenerThread != null)
		{
			try
			{
				serialListenerThread.join();
			}
			catch (InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}

		arduino.closeConnection();
	}

	/**
	 * Add serial listener to listen to input sent
	 * @param eventHandler Handler to handle the event of type (x) -> { }
	 */
	public void addSerialListener(Consumer<String> eventHandler)
	{
		if (!OK)
		{
			throw new IllegalStateException("Not yet initialised");
		}

		listening = true;
		if (serialListenerThread == null)
		{
			serialListenerThread = new Thread(() ->
			{
				Thread.currentThread().setName("Serial Listener @ " + arduino.getPortDescription());

				try
				{
					while (listening)
					{
						if (!arduino.getSerialPort().isOpen())
						{
							throw new IOException(arduino.getPortDescription() + ": Serial port closed the connection");
						}

						String in;

						// Synchronised: Do not allow port access when busy (causes deadlock)
						synchronized (lock)
						{
							in = arduino.serialRead();
						}

						if (!in.isEmpty())
						{
							eventHandler.accept(in);
							System.err.println("DEBUG RECEIVE: " + in);
						}
						else
						{
							// Prevent constant locking of the port, to also allow writing to it *very important*
							try
							{
								Thread.sleep(5);
							}
							catch (InterruptedException ie)
							{
								ie.printStackTrace();
							}
						}
					}
				}
				catch (IOException ioe)
				{
					ioe.printStackTrace();
				}
			});

			serialListenerThread.start();
		}
	}

	/**
	 * Called by the class constructor, connects to the given port
	 * @param port Comm port to connect to
	 */
	private void connectTo(SerialPort port)
	{
		OK = false;
		listening = false;
		serialListenerThread = null;

		boolean validCommPort = false;
		SerialPort[] availablePorts = SerialPort.getCommPorts();
		for (SerialPort serialPort : availablePorts)
		{
			if (serialPort.getSystemPortName().equals(port.getSystemPortName()))
			{
				validCommPort = true;
			}
		}

		if (!validCommPort)
		{
			System.err.println(port.getSystemPortName() + ": Port not available");
			return;
		}

		String portStr = port.getSystemPortName();
		arduino = new Arduino(portStr, 9600);
		if (!arduino.openConnection())
		{
			System.err.println(portStr + ": Port is busy, cannot connect");
			return;
		}

		// Make sure Arduino is ready for serial
		try
		{
			Thread.sleep(1500);
		}
		catch (InterruptedException ie)
		{
			ie.printStackTrace();
		}

		OK = true;
	}
}
