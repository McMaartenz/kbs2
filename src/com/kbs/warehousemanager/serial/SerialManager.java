package com.kbs.warehousemanager.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.awt.*;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

public class SerialManager
{
	private static final String CONNECTION_ERROR = "Failed to connect";
	private static final String NOT_ENOUGH_PORTS = "There were not enough ports for two robots";

	private Queue<String> orderpickRobotBuffer = new LinkedList<>();
	private Queue<String> inpakRobotBuffer = new LinkedList<>();

	public final Object orderpickRobotBufferLock = new Object();
	public final Object inpakRobotBufferLock = new Object();

	private Serial orderpickRobot;
	private Serial inpakRobot;

	public volatile boolean uitgetikt;

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
			orderpickRobot = new Serial(availablePorts[1]);
			inpakRobot = new Serial(availablePorts[0]);
		}

		if (orderpickRobot.good())
		{
			System.out.println("Connected to orderpick robot");
			orderpickRobot.addSerialListener((in) ->
			{
				synchronized(orderpickRobotBufferLock)
				{
					String[] incomingBuffer = in.split("\n");
					for (String line : incomingBuffer)
					{
						if (line.startsWith("uitgetikt"))
						{
							uitgetikt = true;
							continue;
						}
						orderpickRobotBuffer.add(line);
					}
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
					String[] incomingBuffer = in.split("\n");
					inpakRobotBuffer.addAll(Arrays.asList(incomingBuffer));
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

	/**
	 * Close serial manager & resources
	 * @param sm object
	 */
	public static void close(SerialManager sm)
	{
		if (sm != null)
		{
			if (sm.good(Robot.INPAK_ROBOT))
			{
				sm.getRobot(Robot.INPAK_ROBOT).close();
			}

			if (sm.good(Robot.ORDERPICK_ROBOT))
			{
				sm.getRobot(Robot.ORDERPICK_ROBOT).close();
			}
		}
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

	private Queue<String> getBufferOf(Robot robot)
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
	 * @return response string or ErrorNoResponse if timeout triggered
	 */
	public String receiveFrom(Robot robot)
	{
		ExecutorService executor = Executors.newSingleThreadExecutor();

		final Future<String> response = executor.submit(() ->
		{
			while (true)
			{
				if (!getBufferOf(robot).isEmpty())
				{
					synchronized (getLockOf(robot))
					{
						return getBufferOf(robot).poll();
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

		return "ErrorNoResponse";
	}

	/**
	 * Sends a packet and receives a response
	 * @param s Packet data
	 * @param robot Robot to send to
	 * @param expectResponse Should we wait for a response?
	 * @return ErrorNoResponse in case of timeout (500ms),
	 *         ErrorSerialFault when port is closed or I/O exception is thrown,
	 *         ErrorNotExpected when <b>expectResponse</b> is false,
	 *         response of packet
	 */
	public String sendPacket(String s, Robot robot, boolean expectResponse)
	{
		if (good(robot))
		{
			Serial serial = getRobot(robot);

			serial.send(s);

			return expectResponse ? receiveFrom(robot) : "ErrorNotExpected";
		}
		else
		{
			return "ErrorSerialFault";
		}
	}

	/**
	 * Send a packet of points
	 * @param points Point data / path
	 * @param robot Robot to send to
	 * @param expectResponse Should we wait for a response?
	 * @return See {@link #sendPacket}'s return
	 */
	public String sendPointsPacket(Point[] points, Robot robot, boolean expectResponse)
	{
		StringBuilder sb = new StringBuilder("pos!");

		// Format string with a single leading zero (02d: decimal with leading zero of max length 2)
		sb.append(String.format("%02d", points.length));
		for (Point point : points)
		{
			sb.append(point.x);
			sb.append(point.y);
		}
		sb.append('\n');

		return sendPacket(sb.toString(), robot, expectResponse);
	}

	/**
	 * Perform the path on the order pick robot
	 * @param points points to visit, sorted
	 * @return true on success, or false on fail
	 */
	public boolean performPath(Point[] points)
	{
		if (good(Robot.ORDERPICK_ROBOT))
		{
			String response;
			int attemptCount = 0;
			do
			{
				response = sendPointsPacket(points, Robot.ORDERPICK_ROBOT, true);

				System.out.println("Sent points packet - response: " + response);
				if (response.startsWith("Error"))
				{
					if (response.startsWith("ErrorPacketTooLong"))
					{
						System.err.println("Irrecoverable issue: packet too long");
						return false;
					}

					if (++attemptCount > 5)
					{
						System.err.println("Failed to communicate with orderpick robot");
						return false;
					}

					try
					{
						Thread.sleep(500);
					}
					catch (InterruptedException ie)
					{
						ie.printStackTrace();
					}
				}
			}
			while (!response.startsWith("OK"));

			for (int currentPoint = 0; currentPoint < points.length; currentPoint++)
			{
				Point currentPointObj = points[currentPoint];
				System.out.format("Robot going to point id %d, at (%d, %d)\n", currentPoint, currentPointObj.x, currentPointObj.y);

				uitgetikt = false;
				response = sendPacket("step\n", Robot.ORDERPICK_ROBOT, true);
				if (!response.startsWith("OK"))
				{
					System.err.println("Error: " + response);
					return false;
				}
				if (response.startsWith("OKEndPoint") && currentPoint != (points.length - 1))
				{
					System.err.println("Error mismatch: expected OKEndPoint, but received OK");
					return false;
				}

				long timeoutDate = System.currentTimeMillis() + 7500;
				while (!uitgetikt)
				{
					Thread.onSpinWait();
					if (timeoutDate > System.currentTimeMillis())
					{
						System.err.println("Timeout reached for step packet");
						return false;
					}
				}
			}
		}
		else
		{
			System.err.println("Error: Orderpick robot is not available");
			return false;
		}

		return true;
	}
}
