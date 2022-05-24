package com.kbs.warehousemanager.serial;

import com.fazecast.jSerialComm.SerialPort;

import java.io.IOException;

public class SerialManager
{
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
			System.err.println("Unable to connect to any comm ports");
			return;
		}

		if (availablePorts.length < 2)
		{
			if (preferredRobot == Robot.ORDERPICK_ROBOT)
			{
				System.err.println("There were not enough comm ports, connecting to orderpick robot");
				orderpickRobot = new Serial(availablePorts[0]);
			}
			else if (preferredRobot == Robot.INPAK_ROBOT)
			{
				System.err.println("There were not enough comm ports, connecting to inpak robot");
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
				System.out.println("Received data from orderpick: " + in);
			});
		}
		else
		{
			System.err.println("Could not connect to orderpick robot");
		}

		if (inpakRobot.good())
		{
			System.out.println("Connected to inpak robot");
			inpakRobot.addSerialListener((in) ->
			{
				System.out.println("Received data from inpak: " + in);
			});
		}
		else
		{
			System.err.println("Could not connect to inpak robot");
		}
	}
}
