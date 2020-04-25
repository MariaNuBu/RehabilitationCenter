package db.sqlite;

import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import db.interfaces.AppointmentManager;
import db.interfaces.DBManager;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;

public class SQLiteManager implements DBManager {

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
			//this.c=DriverManager.getConnection("jdbc:sqlite:C:/Users/Teresa Romero/git/RehabilitationCenter2.0/RehabilitationCenter/db/RehabilitationCenter.db"); 
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
			/*
			java.sql.Date d = new java.sql.Date(new Date().getTime());
			Statement st12=c.createStatement();
			String sq12="INSERT INTO physicalTherapist (Name,Address,DOB,Phone,Email,SportType,Salary)"
					   +"VALUES('Guillermo','Antonio López','d',678907283,'pablo@gmail.com','Tennis',1324.22)";
			st12.executeUpdate(sq12);
			st12.close();
			Statement st13=c.createStatement();
			String sq13="INSERT INTO physicalTherapist (Name,Address,DOB,Phone,Email,SportType,Salary)"
					   +"VALUES('Paco Fernández','Marqués de Vadillo','1980-04-05',635907283,'paco@gmail.com','Swimming',1224.22)";
			st13.executeUpdate(sq13);
			st13.close();
			*/
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
			Statement st22=c.createStatement();
			
			String sq22="INSERT INTO PatientDoctor (PATID,DOCID) "
					+ "VALUES (1,2)";
			st22.executeUpdate(sq22);
			st22.close();
			
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
					+ " FOREIGN KEY (PATID) REFERENCES patient(ID),"
					+ " FOREIGN KEY (DOCID) REFERENCES doctor(ID),"
					+ "	FOREIGN KEY (PTID) REFERENCES physicalTherapist(ID)"
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
			Statement st9=c.createStatement();
			String sq9="CREATE TABLE	IF NOT EXISTS DoctorMedicalHistory"
					+ "(MHID			INTEGER REFERENCES medicalHistory(ID),"
					+ " DOCID			INTEGER REFERENCES doctor(ID))";
			st9.executeUpdate(sq9);
			st9.close();
			Statement st10=c.createStatement();
			String sq10="CREATE TABLE  IF NOT EXISTS PhysicalTherapistMedicalHistory"
					+ "(MHID			INTEGER REFERENCES medicalHistory(ID),"
					+ " PTID			INTEGER REFERENCES physicalTherapist(ID))";
			st10.executeUpdate(sq10);
			st10.close();
			Statement st11=c.createStatement();
			String sq11="CREATE TABLE IF NOT EXISTS  PhysicalTherapistTreatment"
					+ "(TREATID			INTEGER REFERENCES treatment(ID),"
					+ " PTID			INTEGER REFERENCES physicalTherapist(ID))";
			st11.executeUpdate(sq11);
			st11.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

	}

}
