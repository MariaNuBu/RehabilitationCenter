package db.sqlite;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import db.interfaces.AppointmentManager;
import db.interfaces.DBManager;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;

public class SQLiteManager implements DBManager 
{

	private Connection c;
	private PatientManager patient;
	private DoctorManager doctor;
	private PhysicalTherapistManager physicalTherapist;
	private AppointmentManager appointment;

	public SQLiteManager()
	{
		super();
	}

	public Connection getConnection()
	{
		return c;
	}
	public PatientManager getPatientManager() 
	{
		return patient;
	}
	public DoctorManager getDoctorManager() 
	{
		return doctor;
	}

	public PhysicalTherapistManager getPhysicalTherapistManager()
	{
		return physicalTherapist;
	}

	public AppointmentManager getAppointmentManager(){
		return appointment;
	}

	@Override
	public void connect()
	{
		try
		{
			Class.forName("org.sqlite.JDBC");
			this.c=DriverManager.getConnection("jdbc:sqlite:./db/RehabilitationCenter.db");
			c.createStatement().execute("PRAGMA foreign_keys=ON");
			patient = new SQLitePatientManager(c);
			doctor = new SQLiteDoctorManager(c);
			physicalTherapist = new SQLitePhysicalTherapistManager(c);
			appointment = new SQLiteAppointmentManager(c);

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
		try
		{
			Statement st6=c.createStatement();
			String sq6="CREATE TABLE  IF NOT EXISTS  physicalTherapist"
					+ "(ID				INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Name			TEXT NOT NULL,"
					+ "	Address			TEXT NOT NULL,"
					+ " DOB				DATE NOT NULL,"
					+ " Phone			INTEGER NOT NULL,"
					+ " Email			TEXT NOT NULL,"
					+ " SportType		TEXT NOT NULL,"
					+ " Salary			DOUBLE NOT NULL)";
			st6.executeUpdate(sq6);
			st6.close();
			Statement st2=c.createStatement();
			String sq2="CREATE TABLE IF NOT EXISTS   medicalHistory "
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
			String sq1= "CREATE TABLE IF NOT EXISTS  patient "
					+	"(ID			INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ 	"Name 	        TEXT  NOT NULL,"
					+   "Address        TEXT  NOT NULL,"
					+   "DOB            DATE  NOT NULL,"
					+   "Phone 		    INTEGER NOT NULL,"
					+   "Email 		    TEXT  NOT NULL,"
					+   "SportType      TEXT,"
					+   "Disability     TEXT,"
					+   "MHID           INTEGER,"
					+   "PTID           INTEGER,"
					+   "FOREIGN KEY (MHID) REFERENCES medicalHistory(ID),"
					+   "FOREIGN KEY (PTID) REFERENCES physicalTherapist(ID)"
					+ ")";
			st1.executeUpdate(sq1);
			st1.close();
			Statement st3=c.createStatement();
			String sq3="CREATE TABLE  IF NOT EXISTS  doctor"
					+  "(ID 		    INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "	Name			TEXT NOT NULL,"
					+ "	Address			TEXT NOT NULL,"
					+ "	DOB				DATE NOT NULL,"
					+ "	Phone			INTEGER NOT NULL,"
					+ "	Email			TEXT NOT NULL,"
					+ "	Specialty		TEXT NOT NULL,"
					+ "	Salary			DOUBLE NOT NULL)";
			st3.executeUpdate(sq3);
			st3.close();
			Statement st4=c.createStatement();
			String sq4="CREATE TABLE  IF NOT EXISTS  treatment"
					+ "(ID				INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ " Type			TEXT NOT NULL,"
					+ " Length			INTEGER NOT NULL,"
					+ " DOCID			INTEGER NOT NULL,"
					+ " FOREIGN KEY (DOCID) REFERENCES doctor(ID)	"
					+ ")";
			st4.executeUpdate(sq4);
			st4.close();
			Statement st5=c.createStatement();
			String sq5="CREATE TABLE  IF NOT EXISTS  appointment"
					+ "(ID				INTEGER PRIMARY KEY AUTOINCREMENT,"
					+ "	date			DATE NOT NULL,"
					+ " time			TIME NOT NULL,"
					+ " PATID			INTEGER NOT NULL,"
					+ " DOCID			INTEGER NOT NULL,"
					+ " PTID			INTEGER NOT NULL,"
					+ " FOREIGN KEY (PATID) REFERENCES patient(ID) ON DELETE CASCADE,"
					+ " FOREIGN KEY (DOCID) REFERENCES doctor(ID) ON DELETE CASCADE,"
					+ "	FOREIGN KEY (PTID) REFERENCES physicalTherapist(ID) ON DELETE CASCADE"
					+ ")";
			st5.executeUpdate(sq5);
			st5.close();
			Statement st7=c.createStatement();
			String sq7="CREATE TABLE  IF NOT EXISTS  PatientTreatment"
					+ "(PATID			INTEGER REFERENCES patient(ID),"
					+ " TREATID			INTEGER REFERENCES treatment(ID))";
			st7.executeUpdate(sq7);
			st7.close();
			Statement st8=c.createStatement();
			String sq8="CREATE TABLE IF NOT EXISTS	PatientDoctor"
					+ "(PATID			INTEGER REFERENCES patient(ID),"
					+ " DOCID			INTEGER REFERENCES doctor(ID))";
			st8.executeUpdate(sq8);
			st8.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

}
