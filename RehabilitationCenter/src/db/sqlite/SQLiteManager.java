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
			Statement st6=c.createStatement();
			String sq6="CREATE TABLE    physicalTherapist"
					+ "(ID				INTEGER PRIMARY KEY AUTOINCREMENT"
					+ " Name			TEXT NOT NULL"
					+ "	Adress			TEXT NOT NULL"
					+ " DOB				DATE NOT NULL"
					+ " Phone			INTEGER NOT NULL"
					+ " Email			TEXT NOT NULL"
					+ " SportType		TEXT NOT NULL"
					+ " Salary			DOUBLE NOT NULL)";
			st6.executeUpdate(sq6);
			st6.close();
			Statement st2=c.createStatement();
			String sq2="CREATE TABLE    medicalHistory " 
					+	"(ID			INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ 	" Name 	        TEXT  NOT NULL,"
					+   " DOB           DATE  NOT NULL,"					
					+   " Diseases	    TEXT,"
					+   " Allergies     TEXT,"
					+   " Surgeries     TEXT,"
					+   " WeightKg      FLOAT,"
					+   " HeightCm      INTEGER)";
			st2.executeUpdate(sq2);
			st2.close();
			Statement st1=c.createStatement();
			String sq1= "CREATE TABLE   patient " 
					+	"(ID			INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ 	"Name 	        TEXT  NOT NULL,"
					+   "Adress         TEXT  NOT NULL,"
					+   "DOB            DATE  NOT NULL,"
					+   "Phone 		    FLOAT NOT NULL,"
					+   "Email 		    TEXT  NOT NULL,"
					+   "SportType      TEXT,"
					+   "Disability     TEXT,"
					+   "MHID           INTEGER,"
					+   "PTID           INTEGER,"
					+   "FOREIGN KEY MHID REFERENCES medicalHistory(1),"
					+   "FOREIGN KEY PTID REFERENCES physicalTherapist(1)";
			st1.executeUpdate(sq1);
			st1.close();			
			Statement st3=c.createStatement();
			String sq3="CREATE TABLE    doctor"
					+  "(ID 		    INTEGER PRIMARY KEY AUTOINCREMENT"
					+ "	Name			TEXT NOT NULL"
					+ "	Adress			TEXT NOT NULL"
					+ "	DOB				DATE NOT NULL"
					+ "	Phone			INTEGER NOT NULL"
					+ "	Email			TEXT NOT NULL"
					+ "	Specialty		TEXT NOT NULL"
					+ "	Salary			DOUBLE NOT NULL)";
			st3.executeUpdate(sq3);
			st3.close();
			Statement st4=c.createStatement();
			String sq4="CREATE TABLE    treatment"
					+ "(ID				INTEGER PRIMARY KEY AUTOINCREMENT"
					+ " Type			TEXT NOT NULL"
					+ " Length			INTEGER NOT NULL"
					+ " FOREIGN KEY DOCID REFERENCES doctor(1))";
			st4.executeUpdate(sq4);
			st4.close();
			Statement st5=c.createStatement();
			String sq5="CREATE TABLE    appointment"
					+ "(ID				INTEGER PRIMARY KEY AUTOINCREMENT"
					//TODO
					+ "	DateTime		DATETIME NOT NULL"
					+ " FOREIGN KEY PATID REFERENCES patient(1)"
					+ " FOREIGN KEY DOCID REFERENCES doctor(1)"
					+ "	FOREIGN KEY PTID REFERENCES physicalTherapist(1))";
			st5.executeUpdate(sq5);
			st5.close();
			Statement st7=c.createStatement();
			String sq7="CREATE TABLE    PatientTreatment"
					+ "(PATID			INTEGER REFERENCES patient(1)"
					+ " TREATID			INTEGER REFERENCES treatment(1))";
			st7.executeUpdate(sq7);
			st7.close();
			Statement st8=c.createStatement();
			String sq8="CREATE TABLE	PatientDoctor"
					+ "(PATID			INTEGER REFERENCES patient(1)"
					+ " DOCID			INTEGER REFERENCES doctor(1))";
			st8.executeUpdate(sq8);
			st8.close();
			Statement st9=c.createStatement();
			String sq9="CREATE TABLE	DoctorMedicalHistory"
					+ "(MHID			INTEGER REFERENCES medicalHistory(1)"
					+ " DOCID			INTEGER REFERENCES doctor(1))";
			st9.executeUpdate(sq9);
			st9.close();
			Statement st10=c.createStatement();
			String sq10="CREATE TABLE   PhysicalTherapistMedicalHistory"
					+ "(MHID			INTEGER REFERENCES medicalHistory(1)"
					+ " PTID			INTEGER REFERENCES physicalTherapist(1))";
			st10.executeUpdate(sq10);
			st10.close();
			Statement st11=c.createStatement();
			String sq11="CREATE TABLE   PhysicalTherapistTreatment"
					+ "(TREATID			INTEGER REFERENCES treatment(1)"
					+ " PTID			INTEGER REFERENCES physicalTherapist(1))";
			st11.executeUpdate(sq11);
			st11.close();		
			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

}
