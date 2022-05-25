package com.kbs.warehousemanager.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.*;

public class SerialManager
{
	private static final String CONNECTION_ERROR = "Failed to connect";
	private static final String NOT_ENOUGH_PORTS = "There were not enough ports for two robots";

	private String[] orderpickRobotBuffer = new String[0];
	private String[] inpakRobotBuffer = new String[0];

	public final Object orderpickRobotBufferLock = new Object();
	public final Object inpakRobotBufferLock = new Object();

	private Serial orderpickRobot;
	private Serial inpakRobot;

	public Serial getRobot(Robot robot)
	{
		if (robot == Robot.ORDERPICK_ROBOT)
		{
			return orderpickRobot;
		}
		else if (robot == Robot.INPAK_ROBOT)
		{
			return inpakRobot;
		}

		throw new IllegalArgumentException("Unknown robot");
	}

	/**
	 * Instantiate a new serial manager
	 * @param preferredRobot Preferred robot to connect to in case of only a single connection
	 */
	public SerialManager(Robot preferredRobot)
	{
		orderpickRobot = new Serial();
		inpakRobot = new Serial();

		SerialPort[] availablePorts;
		try
		{
			availablePorts = Serial.getAvailableSerialPorts();
		}
		catch (IOException ioe)
		{
			System.err.println(CONNECTION_ERROR + " to any comm ports");
			return;
		}

		if (availablePorts.length < 2)
		{
			if (preferredRobot == Robot.ORDERPICK_ROBOT)
			{
				System.err.println(NOT_ENOUGH_PORTS + ", connecting to orderpick robot");
				orderpickRobot = new Serial(availablePorts[0]);
			}
			else if (preferredRobot == Robot.INPAK_ROBOT)
			{
				System.err.println(NOT_ENOUGH_PORTS + ", connecting to inpak robot");
				inpakRobot = new Serial(availablePorts[0]);
			}
		}
		else
		{
			orderpickRobot = new Serial(availablePorts[0]);
			inpakRobot = new Serial(availablePorts[1]);
		}

		if (orderpickRobot.good())
		{
			System.out.println("Connected to orderpick robot");
			orderpickRobot.addSerialListener((in) ->
			{
				synchronized(orderpickRobotBufferLock)
				{
					orderpickRobotBuffer = in.split("\n");
				}
			});
		}
		else
		{
			System.err.println(CONNECTION_ERROR + " to orderpick robot");
		}

		if (inpakRobot.good())
		{
			System.out.println("Connected to inpak robot");
			inpakRobot.addSerialListener((in) ->
			{
				synchronized(inpakRobotBufferLock)
				{
					inpakRobotBuffer = in.split("\n");
				}
			});
		}
		else
		{
			System.err.println(CONNECTION_ERROR + " to inpak robot");
		}
	}

	/**
	 * Same as Serial
	 * @param robot Robot om te checken
	 * @return is good
	 * @see com.kbs.warehousemanager.serial.Serial#good()
	 */
	public boolean good(Robot robot)
	{
		return getRobot(robot).good();
	}

	private Object getLockOf(Robot robot)
	{
		if (robot == Robot.ORDERPICK_ROBOT)
		{
			return orderpickRobotBufferLock;
		}
		else if (robot == Robot.INPAK_ROBOT)
		{
			return inpakRobotBufferLock;
		}
		throw new IllegalArgumentException("No such robot");
	}

	private String[] getBufferOf(Robot robot)
	{
		if (robot == Robot.ORDERPICK_ROBOT)
		{
			return orderpickRobotBuffer;
		}
		else if (robot == Robot.INPAK_ROBOT)
		{
			return inpakRobotBuffer;
		}
		throw new IllegalArgumentException("No such robot");
	}

	/**
	 * Receive data from robot with a timeout of 500ms
	 * @param robot Robot to receive from
	 * @return response string or NoResponse if timeout triggered
	 */
	public String receiveFrom(Robot robot)
	{
		ExecutorService executor = Executors.newSingleThreadExecutor();

		final Future<String> response = executor.submit(() ->
		{
			while (true)
			{
				if (getBufferOf(robot).length != 0)
				{
					synchronized (getLockOf(robot))
					{
						return getBufferOf(robot)[0];
					}
				}
				Thread.onSpinWait();
			}
		});

		try
		{
			return response.get(500, TimeUnit.MILLISECONDS);
		}
		catch (InterruptedException | ExecutionException | TimeoutException e)
		{
			System.err.println("Packet timed out");
		}

		return "NoResponse";
	}

	/**
	 * Sends a packet and receives a response
	 * @param s Packet data
	 * @param robot Robot to send to
	 * @param expectResponse Should we wait for a response?
	 * @return NoResponse in case of timeout (500ms),
	 *         SerialFault when port is closed or I/O exception is thrown,
	 *         NotExpected when <b>expectResponse</b> is false,
	 *         response of packet
	 */
	public String sendPacket(String s, Robot robot, boolean expectResponse)
	{
		if (good(robot))
		{
			Serial serial = getRobot(robot);

			serial.send(s);

			return expectResponse ? receiveFrom(robot) : "NotExpected";
		}
		else
		{
			return "SerialFault";
		}
	}
}
