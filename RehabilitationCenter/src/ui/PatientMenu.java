package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;
import db.sqlite.SQLiteManager;
import db.sqlite.SQLitePatientManager;
import db.sqlite.SQLitePhysicalTherapistManager;
import pojos.MedicalHistory;
import pojos.Patient;
import pojos.PhysicalTherapist;

public class PatientMenu
{
	private BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));;
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;

	public void registerPatient() throws IOException
	{
		db= new SQLiteManager();
		db.connect();
		pm = db.getPatientManager();
		ptm=db.getPhysicalTherapistManager();
		/*
		System.out.println("Name and Surname: ");
		String name=reader.readLine();
		System.out.println("Date of Birth: ");
		String newDOBDate = reader.readLine();
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));;
		System.out.println("Address: ");
		String address=reader.readLine();
		System.out.println("Email: ");
		String email=reader.readLine();
		System.out.println("Phone number: ");
		Integer phone=Integer.parseInt(reader.readLine());
		System.out.println("Type of sport: ");
		String sport=reader.readLine();
		System.out.println("Disabilities: ");
		String disabilities=reader.readLine();
		System.out.println("Diseases: ");
		String diseases=reader.readLine();
		System.out.println("Allergies: ");
		String allergies=reader.readLine();
		System.out.println("Surgeries: ");
		String surgeries=reader.readLine();
		System.out.println("Weight(kg): ");
		Float weight=Float.parseFloat(reader.readLine());
		System.out.println("Height(cm): ");
		Integer height=Integer.parseInt(reader.readLine());
		*/
		String name="Maria";
		String address="ss";
		String newDOBDate = "2000-11-22";
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));
		String email="@";
		Integer phone=123;
		String sport="Paddel";
		String disabilities="n";
		String diseases="n";
	
		String allergies="n";
		
		String surgeries="n";
		
		Float weight=(float) 54.5;
		
		Integer height=160;
		
		ArrayList<PhysicalTherapist> pts=ptm.showPhysicalTherapists(sport);		
		if (pts.isEmpty())
		{
			System.out.println("No physical therpist specialized in"+sport);
			System.out.println("Choose one of those: ");
			ArrayList<PhysicalTherapist> pysicals=ptm.showAllPhysicalTherapists();
			for (PhysicalTherapist physicalTherapist : pysicals) 
			{
				System.out.println(physicalTherapist);	
			}
		}
		else
		{
			for (PhysicalTherapist physicalTherapist : pts) 
			{
				System.out.println(physicalTherapist);	
			}
		}
		System.out.println("Introduce the id of the Physical Therapist you want: ");
		Integer ptid=Integer.parseInt(reader.readLine());
		PhysicalTherapist pt=ptm.getPhysicalTherapist(ptid);
		Patient p=new Patient(name, address, DOB, phone, email, sport, disabilities);
		MedicalHistory mh=new MedicalHistory(name, DOB, diseases, allergies, surgeries, weight, height);
		pm.addPatientandMedicalHistory(p, pt, mh);
		System.out.println("Register completed succesfully!");
	}

}
