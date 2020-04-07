package ui;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import db.interfaces.*;
import db.sqlite.*;
import pojos.*;

public class MainTest 
{
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;
	
		
	public static void main(String [] args) 
	{
		java.sql.Date d = new java.sql.Date(new Date().getTime());
		Patient p= new Patient(1,"Maria","AntonioLopez",d,123,"email","tennis","none");
		PhysicalTherapist pt=new PhysicalTherapist(1,"AObalo","ss",d,123,"aa","ww",324.0);
		MedicalHistory mh=new MedicalHistory(1, "in",d, "diseases", "allergies", "surgeries", (float)123, 123);
		db= new SQLiteManager();
		db.connect("jdbc:sqlite:C:/Users/msmar/OneDrive/Documentos/UNI/2º_2ºCuatri/Bases de datos/Práctica/Test.db");
		db.createTables();
		pm = db.getPatient();
		ptm=db.getPhysicalTherapist();
		//TODO esto explota 
		pm.addPatient(p, pt, mh);
		System.out.println("T");
	}
}
