package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import db.interfaces.*;
import db.sqlite.*;
import gui.PatientMenu;
import pojos.*;

public class MainTest 
{
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;
	
		
	public static void main(String [] args) throws NumberFormatException, IOException 
	{
		/*
		java.sql.Date d = new java.sql.Date(new Date().getTime());
		Patient p= new Patient("Maria","AntonioLopez",d,123,"email","tennis","none");
		PhysicalTherapist pt1=new PhysicalTherapist("Pablo","Antonio López",d,678907283,"pablo@gmail.com","Tennis",1324.22);
		PhysicalTherapist pt3=new PhysicalTherapist("Gabs","Montecarmelo",d,678907283,"gabs@gmail.com","Tennis",1324.22);
		PhysicalTherapist pt2=new PhysicalTherapist("Alberto","Serrano",d,678123173,"alberto@gmail.com","WeightLifting",34456.0);
		MedicalHistory mh=new MedicalHistory("Maria",d, "diseases", "allergies", "surgeries", (float)123, 123);
		db= new SQLiteManager();
		db.connect();
		db.createTables();
		pm = db.getPatient();
		ptm=db.getPhysicalTherapist();
		*/
		//TODO por que ahora la fecha explota y antes no ;pero ya funciona bien sale la lista con todo menos la fecha
		/*
		ptm.insertPhysicalTherapist(pt1);
		ptm.insertPhysicalTherapist(pt2);
		ptm.insertPhysicalTherapist(pt3);
		ArrayList<PhysicalTherapist> pts=ptm.showPhisicalTherapists("Tennis");
		for (PhysicalTherapist physicalTherapist : pts) 
		{
			System.out.println(physicalTherapist);	
		}
		*/
		// esto SI FUNCIONA!!!
		//pm.addPatientandMedicalHistory(p, ptm.getPhysicalTherapist(1), mh);
		//ESTO TAMBIEN FUNCIONA LO BUSCA BIEN!!
		/*
		ArrayList<Patient> searched=pm.searchPatientName("Maria");
		for (Patient patient : searched) 
		{
			System.out.println(patient);
		}
		*/
		//ESTO TAMBIEN FUNCIONA
		/*
		Patient get=pm.getPatient(1);
		System.out.println("conseguido\n"+get);
		PhysicalTherapist pget=ptm.getPhysicalTherapist(3);
		System.out.println("physical\n"+pget);
		MedicalHistory getmh=pm.getMedicalHistory(get);
		System.out.println("MH "+getmh);
		*/
		
		System.out.println("WELCOME TO OUR PARALIMPICS REHABILITATION CENTER");
		System.out.println("What are you ?\n1.-Patient\n2.-Doctor\n3.-PhysicalTherapist");
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));;
		int option = Integer.parseInt(reader.readLine());
		switch(option)
		{
		case 1:
			PatientMenu patientMenu=new PatientMenu();
		}
		;
		
	}
}
