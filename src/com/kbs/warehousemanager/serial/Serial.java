package com.kbs.warehousemanager.serial;

import com.fazecast.jSerialComm.SerialPort;
import arduino.Arduino;

import java.io.IOException;
import java.util.Arrays;
import java.util.function.Consumer;

public class Serial
{
	private Arduino arduino;
	private boolean listening;
	private Thread serialListenerThread;
	private boolean OK;

	/**
	 * Connect to a specific port
	 * @param port Comm port to connect to
	 */
	public Serial(SerialPort port)
	{
		connectTo(port);
	}

	/**
	 * Connect to the first port available
	 */
	public Serial()
	{
		SerialPort[] availablePorts = SerialPort.getCommPorts();
		if (availablePorts.length == 0)
		{
			System.err.println("There are no ports available");
			return;
		}

		connectTo(availablePorts[0]);
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

		arduino.serialWrite(string);
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
				arduino.getSerialPort().setComPortTimeouts(SerialPort.TIMEOUT_READ_SEMI_BLOCKING, 0, 0);

				try
				{

					while (listening)
					{
						if (!arduino.getSerialPort().isOpen())
						{
							throw new IOException("Serial port closed the connection");
						}

						String in = arduino.serialRead();
						if (!in.isEmpty())
						{
							eventHandler.accept(in);
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
			System.err.println("Port not available");
			return;
		}

		String portStr = port.getSystemPortName();
		arduino = new Arduino(portStr, 9600);
		if (!arduino.openConnection())
		{
			System.err.println("Port is busy, cannot connect");
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
