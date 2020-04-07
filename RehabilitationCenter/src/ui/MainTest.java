package ui;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
		PhysicalTherapist pt1=new PhysicalTherapist("Pablo","Antonio López",d,678907283,"pablo@gmail.com","Tennis",1324.22);
		PhysicalTherapist pt3=new PhysicalTherapist("Gabs","Montecarmelo",d,678907283,"gabs@gmail.com","Tennis",1324.22);
		PhysicalTherapist pt2=new PhysicalTherapist("Alberto","Serrano",d,678123173,"alberto@gmail.com","WeightLifting",34456.0);
		MedicalHistory mh=new MedicalHistory(1, "in",d, "diseases", "allergies", "surgeries", (float)123, 123);
		db= new SQLiteManager();
		db.connect();
		//"jdbc:sqlite:C:/Users/msmar/OneDrive/Documentos/UNI/2º_2ºCuatri/Bases de datos/Práctica/Test.db"
		db.createTables();
		pm = db.getPatient();
		ptm=db.getPhysicalTherapist();
		//TODO esto no funciona
		pm.addPatientandMedicalHistory(p, pt1, mh);
		//Funciona el insert
		//TODO por que ahora la fecha explota y antes no ;pero ya funciona bien sale la lista con todo menos la lista
		/*
		ptm.insert(pt1);
		ptm.insert(pt2);
		ptm.insert(pt3);
		ArrayList<PhysicalTherapist> pts=ptm.showPhisicalTherapists("Tennis");
		for(int i=0;i<pts.size();i++)
		{
			System.out.println(pts.get(i));			
		}
		*/
		
		
	}
}
