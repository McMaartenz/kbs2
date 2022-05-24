import javax.naming.AuthenticationException;

class Database
{ // TODO: Implementeer SQL connectie
	private static final String USER = "";
	private static final String PASSWORD = "";

	public void ConnectToDatabase() throws AuthenticationException
	{
		// connect(USER, PASSWORD);
	}

	public void disconnectFromDatabase()
	{
		// disconnect();
	}

	public void addRecordToDatabase(Object[] data)
	{

	}

	public void removeRecordFromDatabase(Object klantnr_pk)
	{

	}

	public void editRecordInDatabase(Object klantnr_pk, Object[] data)
	{

	}
}
