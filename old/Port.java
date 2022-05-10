import com.fazecast.jSerialComm.SerialPort;

public class Port
{
	public static String[] porten;
	public int portNummer;

	public Port(int portNummer) throws ArrayIndexOutOfBoundsException
	{
		setPortNummer(portNummer);
	}

	public Port(String port) throws ArrayIndexOutOfBoundsException
	{
		for (int i = 0; i < porten.length; i++)
		{
			if (porten[i].equals(port))
			{
				setPortNummer(i);
				return;
			}
		}

		setPortNummer(-1);
	}

	public boolean isPort()
	{
		return portNummer != -1;
	}

	private void setPortNummer(int portNummer) throws ArrayIndexOutOfBoundsException
	{
		if (portNummer >= -1 && portNummer < porten.length)
		{
			this.portNummer = portNummer;
		}
		else
		{
			throw new ArrayIndexOutOfBoundsException();
		}
	}

	public static void updatePorten()
	{
		SerialPort[] porten = SerialPort.getCommPorts();
		Port.porten = new String[porten.length];
		for (int i = 0; i < porten.length; i++)
		{
			Port.porten[i] = porten[i].getSystemPortName();
		}
	}

	@Override
	public String toString()
	{
		return portNummer == -1 ? "Undefined" : porten[portNummer];
	}
}