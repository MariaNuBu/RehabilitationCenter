package db.sqlite;

import java.sql.*;
import db.interfaces.DBManager;

public class SQLiteManager implements DBManager {

	private Connection c;
		
	public SQLiteManager() 
	{
		super();
	}
	
	public Connection getConnection() 
	{
		return c;
	}
	
	@Override
	public void connect()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			this.c=DriverManager.getConnection("jdbc:sqlite:./db/rehabilitationcenter.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON"); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void disconnect() 
	{	
		try
		{
			c.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	@Override
	public void createTables()
	{
		try {
			Statement st1=c.createStatement();
			String sq1= "CREATE TABLE patients " 
					+	"(ID	      INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ 	"Name 	      TEXT  NOT NULL,"
					+   "Adress       TEXT  NOT NULL,"
					+   "DOB          DATE  NOT NULL,"
					+   "Phone 		  FLOAT NOT NULL,"
					+   "Email 		  TEXT  NOT NULL,"
					+   "SportType    TEXT,"
					+   "Disability   TEXT,"
					+   "MHID         INTEGER,"
					+   "PTID         INTEGER,"
					+   "FOREIGN KEY MHID REFERENCES medicalHistory(1),"
					+   "FOREIGN KEY PTID REFERENCES physicalTherapist(1)";
			Statement st2=c.createStatement();
			String sq2="CREATE TABLE medicalHistory " 
					+	"(ID	      INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ 	"Name 	      TEXT  NOT NULL,"
					+   "DOB          DATE  NOT NULL,"					
					+   "Diseases	  TEXT,"
					+   "Allergies    TEXT,"
					+   "Surgeries    TEXT,"
					+   "WeightKg     FLOAT,"
					+   "HeightCm     INTEGER,"
					+   "FOREIGN KEY MHID REFERENCES medicalHistory(1),"
					+   "FOREIGN KEY PTID REFERENCES physicalTherapist(1)";
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

}
