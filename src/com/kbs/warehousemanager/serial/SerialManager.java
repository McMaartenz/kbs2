package com.kbs.warehousemanager.serial;

import com.fazecast.jSerialComm.SerialPort;

public class SerialManager
{
	private Serial orderpickRobot;
	private Serial inpakRobot;

	private boolean orderpickRobotAvailable;
	private boolean inpakRobotAvailable;

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
		orderpickRobotAvailable = false;
		inpakRobotAvailable = false;

		SerialPort[] availablePorts = SerialPort.getCommPorts();
		if (availablePorts.length < 2)
		{
			if (availablePorts.length == 0)
			{
				System.err.println("Unable to connect to any comm ports");
			}
			else
			{
				if (preferredRobot == Robot.ORDERPICK_ROBOT)
				{
					System.err.println("There were not enough comm ports, connecting to orderpick robot");
					orderpickRobot = new Serial(availablePorts[0]);
					orderpickRobotAvailable = orderpickRobot.good();
				}
				else if (preferredRobot == Robot.INPAK_ROBOT)
				{
					System.err.println("There were not enough comm ports, connecting to inpak robot");
					inpakRobot = new Serial(availablePorts[0]);
					inpakRobotAvailable = inpakRobot.good();
				}
			}
		}
		else
		{
			orderpickRobot = new Serial(availablePorts[0]);
			orderpickRobotAvailable = orderpickRobot.good();

			inpakRobot = new Serial(availablePorts[1]);
			inpakRobotAvailable = inpakRobot.good();
		}

		if (orderpickRobotAvailable)
		{
			System.out.println("Connected to orderpick robot");
			orderpickRobot.addSerialListener((in) ->
			{
				System.out.println("Received data: " + in);
			});
		}

		if (inpakRobotAvailable)
		{
			System.out.println("Connected to inpak robot");
			inpakRobot.addSerialListener((in) ->
			{
				System.out.println("Received data: " + in);
			});
		}
	}
}
