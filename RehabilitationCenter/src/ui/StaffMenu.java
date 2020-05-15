package ui;


import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

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
	
	public void staffMenu(DoctorManager dm,PhysicalTherapistManager ptm,PatientManager pm,AppointmentManager am,UserManager um) throws Exception
	{
		Boolean loop = true;
		while(loop)
		{
			Integer option = DataObtention.readInt("Choose the action: \n1.-Register\n2.-Fire workers\n3.-Exit");
			switch (option)
			{
				case 1:
					Integer role =DataObtention.readInt("Choose the role you want to register: \n1.-Patient\n2.-Patient through XML\n3.-Doctor\n4.-Physical Therapist\n5.-Back");
					switch (role)
					{
						case 1:
							registerPatient(pm,ptm,um);
							break;
						case 2:
							registerPatientXML(pm,ptm,um);
							break;
						case 3:				
							registerDoctor(dm,um);
							break;
						case 4:
							registerPhysicalTherapist(ptm,um);
							break;
						case 5:
							break;
					}
					break;
				case 2:
					Integer rolefire =DataObtention.readInt("Choose the role of the worker you want to fire: \n 1.-Doctor\n 2.-Physical Therapist\n 3.-Exit");
					switch (rolefire)
					{
						case 1:
							fireDoctor(dm,am,um);
							break;
						case 2:				
							firePhysicalTherapist(pm,am,ptm,um);
							break;
						case 3:
							break;
					}
					break;
				case 3:
					System.out.println("Hope to see you soon!");
					loop = false;
					break;
			}			
		}			
	}
	
	//TODO añadir el paciente obtenido a la base , os tengo que preguntar 
	private void registerPatientXML(PatientManager pm,PhysicalTherapistManager ptm,UserManager um) throws Exception {
		//Create JAXBContext
		JAXBContext context = JAXBContext.newInstance(Patient.class);
		//Get the unmarshaller
		Unmarshaller unmarshal = context.createUnmarshaller();
		// Unmarshall the Patient from a file 
	    System.out.println("Type the file name for the XML document (expected in the xmls folder):");
		String fileName=DataObtention.readLine();
		File file =new File("./xmls/"+fileName);
		Patient patient =(Patient) unmarshal.unmarshal(file);
		//Print the patient 
		System.out.println("Added to the database:"+patient);
		ArrayList<PhysicalTherapist> pts=ptm.showPhysicalTherapists(patient.getSport());
		if (pts.isEmpty())
		{
			System.out.println("No physical therpist specialized in "+patient.getSport());
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
		User newpatient = new User();		
		String userName = patient.geteMail();
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
		newpatient = new User(userName,hash,patientRole);
		um.createUser(newpatient);
		System.out.println("Register completed succesfully!");
		
		
	}

	public void registerPatient(PatientManager pm,PhysicalTherapistManager ptm,UserManager um) throws IOException
	{
		
		String name=DataObtention.readName("Name and Surname: ");
		System.out.println("Date of Birth: ");
		String newDOBDate = DataObtention.readLine();
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));
		System.out.println("Address: ");
		String address= DataObtention.readLine();
		System.out.println("Email: ");
		String email= DataObtention.readLine();
		Integer phone= DataObtention.readInt("Phone number: ");
		System.out.println("Type of sport: ");
		String sport= DataObtention.readLine();
		System.out.println("Disabilities: ");
		String disabilities= DataObtention.readLine();
		System.out.println("Diseases: ");
		String diseases= DataObtention.readLine();
		System.out.println("Allergies: ");
		String allergies= DataObtention.readLine();
		System.out.println("Surgeries: ");
		String surgeries= DataObtention.readLine();
		Float weightKG=DataObtention.readFloat("Weight(kg): ");
		Integer height=DataObtention.readInt("Height(cm): ");
		ArrayList<PhysicalTherapist> pts=ptm.showPhysicalTherapists(sport);
		if (pts.isEmpty())
		{
			System.out.println("No physical therpist specialized in "+sport);
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
		um.createUser(patient);
		System.out.println("Register completed succesfully!");
		
	}
	public void registerDoctor(DoctorManager dm,UserManager um) throws IOException
	{
		String name=DataObtention.readName("Name and Surname: ");
		System.out.println("Date of Birth: ");
		String newDOBDate = DataObtention.readLine();
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));
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
		um.createUser(doctor);
		System.out.println("Register completed succesfully!");
		
		
	}
	public void registerPhysicalTherapist(PhysicalTherapistManager ptm,UserManager um) throws IOException
	{
		
		String name=DataObtention.readName("Name and Surname: ");
		System.out.println("Date of Birth: ");
		String newDOBDate = DataObtention.readLine();
		Date DOB = Date.valueOf(LocalDate.parse(newDOBDate, formatter));
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
		//TODO ESTO DA ERROR
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
		System.out.println(email);
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
		um.createUser(physicalTherpist);
		System.out.println("Register completed succesfully!");		
	}
	
	public void fireDoctor (DoctorManager dm, AppointmentManager am, UserManager um)
	{
		List<Doctor> doctors = new ArrayList<Doctor>();
		doctors = dm.listDoctors();
		for (Doctor doctor : doctors) 
		{
			System.out.println(doctor);
		}
		Integer ID = DataObtention.readInt("Introduce the ID of the doctor you want to fire");
		Doctor tofire = dm.getDoctor(ID);
		um.fireWorkers(tofire.getId());
		//probar borrar esto y que borre todo
		am.deleteAppointmentDoctor(ID);
		dm.deleteDoctor(ID);
		
	}
	
	public void firePhysicalTherapist (PatientManager pm,AppointmentManager am,PhysicalTherapistManager ptm,UserManager um)
	{
		List<PhysicalTherapist> physicalTherapists = new ArrayList<PhysicalTherapist>();
		physicalTherapists = ptm.listPhysicalTherapists();
		for (PhysicalTherapist physicalTherapist : physicalTherapists) 
		{
			System.out.println(physicalTherapist);
		}
		Integer ID = DataObtention.readInt("Introduce the ID of the physical therapist you want to fire");
		PhysicalTherapist tofire = ptm.getPhysicalTherapist(ID);
		Integer userID = um.getUser(tofire.geteMail());
		um.fireWorkers(userID);
		//TODO probar a borrar esta linea y borrar todas las tablas
		am.deleteAppointmentPhysicalTherapist(ID);
		ptm.deletePhysicalTherapist(ID);
		//We need to reasign a physical therapist to the patients
		ArrayList<Patient> patients = new ArrayList <Patient>();
		patients = ptm.getAllPatients(ID);
		for (Patient patient : patients) 
		{
			System.out.println(patient);
			physicalTherapists = ptm.showPhysicalTherapists(patient.getSport());
			if (physicalTherapists.isEmpty())
			{
				physicalTherapists = ptm.showAllPhysicalTherapists();
			}
			for (PhysicalTherapist physicalTherapist : physicalTherapists) 
			{
				System.out.println(physicalTherapist);
			}
			Integer newptID = DataObtention.readInt("Type the ID of the physical therapist you want for that patient: ");
			pm.changePhysicalTherapist(patient,newptID);
		}
		
	}
	
}
