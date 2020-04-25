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
import pojos.*;

public class PhysicalMenu
{
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;
	
	public void registerPhysicalTherapist() throws IOException 
	{
		/*
		db= new SQLiteManager();
		db.connect();
		pm = db.getPatientManager();
		ptm = db.getPhysicalTherapistManager();
		*/
		//TODO puedo insertarlos con el JPA? o antes tengo que crear la tabla con JPA
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
		System.out.println("Sport Type: ");
		String sport=reader.readLine();
		System.out.println("Salary: ");
		Double salary = Double.parseDouble(reader.readLine());
		PhysicalTherapist pt = new PhysicalTherapist (name, address, DOB, phone, email, sport, salary);
	}
	public void readTreatment() throws IOException
	{
		db= new SQLiteManager();
		db.connect();
		pm = db.getPatientManager();
		ptm=db.getPhysicalTherapistManager();
		System.out.println("These are your patients");
		//TODO hay que cambiar el 1 por el id del physical
		ArrayList<Patient> patients = new ArrayList<Patient>();
		patients=ptm.getAllPatients(1);
		for (Patient patient : patients) 
		{	
			System.out.println(patient);
		}
		System.out.println("Type the ID of the patient you want to check the treatment");
		Integer patientID = Integer.parseInt(reader.readLine());
		Patient patientChoosen = pm.getPatient(patientID);
		ArrayList<Treatment> treatments = new ArrayList <Treatment>();
		treatments=pm.listTreatment(patientChoosen);
		for (Treatment treatment : treatments) 
		{
			System.out.println(treatment);
		}
	}
	public void checkAppointments() throws IOException
	{
		
	}

}
