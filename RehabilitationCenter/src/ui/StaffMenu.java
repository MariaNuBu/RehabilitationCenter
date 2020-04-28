package ui;


import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import db.interfaces.*;
import pojos.Doctor;
import pojos.MedicalHistory;
import pojos.Patient;
import pojos.PhysicalTherapist;
import pojos.users.Role;
import pojos.users.User;

public class StaffMenu 
{
	
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public void staffMenu(DoctorManager dm,PhysicalTherapistManager ptm,PatientManager pm,UserManager um) throws IOException
	{
		Integer role =DataObtention.readInt("Choose the role you want to register: \n1.-Patient\n2.-Doctor\n3.-Physical Therapist");
		switch (role)
		{
			case 1:
				registerPatient(pm,ptm,um);
				break;
			case 2:				
				registerDoctor(dm,um);
				break;
			case 3:
				registerPhysicalTherapist(ptm,um);
				break;
		}
		
	}
	
	public void registerPatient(PatientManager pm,PhysicalTherapistManager ptm,UserManager um) throws IOException
	{
		
		String name=DataObtention.readName("Name and Surname: ");
		System.out.println("Date of Birth: ");
		String newDOBDate = DataObtention.readLine();
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));;
		System.out.println("Address: ");
		String address= DataObtention.readLine();
		System.out.println("Email: ");
		String email= DataObtention.readLine();
		Integer phone= DataObtention.readInt("Phone number: ");;
		System.out.println("Type of sport: ");
		String sport= DataObtention.readLine();
		System.out.println("Disabilities: ");
		String disabilities= DataObtention.readLine();
		System.out.println("Diseases: ");
		String diseases= DataObtention.readLine();
		System.out.println("Allergies: ");
		String allergies= DataObtention.readLine();;
		System.out.println("Surgeries: ");
		String surgeries= DataObtention.readLine();;
		Float weightKG=DataObtention.readFloat("Weight(kg): ");
		Integer height=DataObtention.readInt("Height(cm): ");
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
		
		Integer ptid=DataObtention.readInt("Introduce the id of the Physical Therapist you want: ");
		PhysicalTherapist pt=ptm.getPhysicalTherapist(ptid);
		Patient p=new Patient(name, address, DOB, phone, email, sport, disabilities);
		MedicalHistory mh=new MedicalHistory(name, DOB, diseases, allergies, surgeries, weightKG, height);
		pm.addPatientandMedicalHistory(p, pt, mh);
		//Now we need to create a new user for that patient and check if the role patient has been created and if its not, create it
		Role patientRole=null;
		if(um.isCreated("Patient")==false)
		{
			patientRole = new Role("Patient");
			um.createRole(patientRole);
			//I do this to get the ID of the role I've created
			patientRole = um.getRoleByName("Patient");
		}
		else
		{
			patientRole = um.getRoleByName("Patient");
		}
		User patient = new User();		
		String userName = email;
		System.out.println("Choose the password for that user: ");
		String pass =DataObtention.readLine();
		MessageDigest md=null;
		try 
		{
			md = MessageDigest.getInstance("SHA-512");
		} 
		catch (NoSuchAlgorithmException e) 
		{			
			e.printStackTrace();
		}
		md.update(pass.getBytes());
		byte [] hash = md.digest();
		patient = new User(userName,hash,patientRole);
		System.out.println("Register completed succesfully!");
		
	}
	public void registerDoctor(DoctorManager dm,UserManager um) throws IOException
	{
		String name=DataObtention.readName("Name and Surname: ");
		System.out.println("Date of Birth: ");
		String newDOBDate = DataObtention.readLine();
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));;
		System.out.println("Address: ");
		String address=DataObtention.readLine();
		System.out.println("Email: ");
		String email=DataObtention.readLine();
		Integer phone=DataObtention.readInt("Phone number: ");
		System.out.println("Specialty: ");
		String specialty= DataObtention.readLine();
		Double salary = DataObtention.readDouble("Salary: ");
		Doctor doc = new Doctor(name, address, DOB, phone, email, specialty, salary);
		dm.createDoctor(doc);
		//Now we need to create a new user for that doctor and check if the role doctor has been created and if its not, create it
		Role doctorRole=null;
		if(um.isCreated("Doctor")==false)
		{
			doctorRole = new Role("Doctor");
			um.createRole(doctorRole);
			//I do this to get the ID of the role I've created
			doctorRole = um.getRoleByName("Doctor");
		}
		else
		{
			doctorRole = um.getRoleByName("Doctor");
		}
		User doctor = new User();		
		String userName = email;
		System.out.println("Choose the password for that user: ");
		String pass = DataObtention.readLine();
		MessageDigest md=null;
		try 
		{
			md = MessageDigest.getInstance("SHA-512");
		} 
		catch (NoSuchAlgorithmException e) 
		{			
			e.printStackTrace();
		}
		md.update(pass.getBytes());
		byte [] hash = md.digest();
		doctor = new User(userName,hash,doctorRole);
		System.out.println("Register completed succesfully!");
		
	}
	public void registerPhysicalTherapist(PhysicalTherapistManager ptm,UserManager um) throws IOException
	{
		
		String name=DataObtention.readName("Name and Surname: ");
		System.out.println("Date of Birth: ");
		String newDOBDate = DataObtention.readLine();
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));;
		System.out.println("Address: ");
		String address=DataObtention.readLine();
		System.out.println("Email: ");
		String email=DataObtention.readLine();
		Integer phone=DataObtention.readInt("Phone number: ");
		System.out.println("Type of sport: ");
		String sport=DataObtention.readLine();
		Double salary = DataObtention.readDouble("Salary: ");
		PhysicalTherapist pt = new PhysicalTherapist(name, address, DOB, phone, email, sport, salary);
		ptm.addPhysicalTherapist(pt);
		//Now we need to create a new user for that physical therapist and check if the role pysical therapist has been created and if its not, create it
		Role physicalTherapistRole=null;
		if(um.isCreated("Physical Therapist")==false)
		{
			physicalTherapistRole = new Role("Physical Therapist");
			um.createRole(physicalTherapistRole);
			//I do this to get the ID of the role I've created
			physicalTherapistRole = um.getRoleByName("Physical Therapist");
		}
		else
		{
			physicalTherapistRole = um.getRoleByName("Physical Therapist");
		}
		User physicalTherpist = new User();		
		String userName = email;
		System.out.println("Choose the password for that user: ");
		String pass = DataObtention.readLine();
		MessageDigest md=null;
		try 
		{
			md = MessageDigest.getInstance("SHA-512");
		} 
		catch (NoSuchAlgorithmException e) 
		{			
			e.printStackTrace();
		}
		md.update(pass.getBytes());
		byte [] hash = md.digest();
		physicalTherpist = new User(userName,hash,physicalTherapistRole);
		System.out.println("Register completed succesfully!");		
	}
	
}
