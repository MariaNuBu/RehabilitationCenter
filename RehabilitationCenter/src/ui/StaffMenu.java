package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	
	public void staffMenu(DoctorManager dm,PhysicalTherapistManager ptm,PatientManager pm,UserManager um) throws IOException
	{
		System.out.println("Choose the role you want to register: \n1.-Patient\n2.-Doctor\n3.-Physical Therapist");
		Integer role = Integer.parseInt(reader.readLine());
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
		Float weightKG=null;
		while (true)
        {
            System.out.println("Weight(kg): ");
            try
            {
                String weight = reader.readLine();
                weightKG = Float.parseFloat(weight);
                break;
            }
            catch (NumberFormatException e)
            {
                System.out.println("Not valid data, try again! ");
            }
            catch (IOException e) { }
        }
		System.out.println("Height(cm): ");
		Integer height=Integer.parseInt(reader.readLine());
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
		String pass = reader.readLine();
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
		System.out.println("Specialty: ");
		String specialty=reader.readLine();
		System.out.println("Salary: ");
		Double salary = Double.parseDouble(reader.readLine());
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
		String pass = reader.readLine();
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
		System.out.println("Salary: ");
		Double salary = Double.parseDouble(reader.readLine());
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
		String pass = reader.readLine();
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
