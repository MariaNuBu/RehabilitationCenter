package ui;
//import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import db.interfaces.*;
import db.jpa.JPAUserManager;
import db.sqlite.*;
import ui.PatientMenu;
import pojos.*;
import pojos.users.User;

public class MainTest 
{
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;
	private static DoctorManager dm;

	private static AppointmentManager am;

	private static UserManager userManager;
	

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	public static void main(String [] args) throws NumberFormatException, IOException,Exception
	{
		db= new SQLiteManager();
		db.connect();
		db.createTables();
		pm = db.getPatientManager();
		dm=db.getDoctorManager();
		ptm=db.getPhysicalTherapistManager();
		am = db.getAppointmentManager();
		//TODO por que ahora la fecha explota y antes no ;pero ya funciona bien sale la lista con todo menos la fecha
		/*
		ptm.insertPhysicalTherapist(pt1);
		ptm.insertPhysicalTherapist(pt2);
		ptm.insertPhysicalTherapist(pt3);
		*/
		/*
		ArrayList<PhysicalTherapist> pts=ptm.showPhisicalTherapists("Tennis");
		for (PhysicalTherapist physicalTherapist : pts) 
		{
			System.out.println(physicalTherapist);	
		}
		if (pts.isEmpty())
		{
			
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
		userManager = new JPAUserManager();
		userManager.connect();
		/*
		
		System.out.println("WELCOME TO OUR PARALIMPICS REHABILITATION CENTER");
		
		System.out.println("Who are you ?\n1.-Patient\n2.-Doctor\n3.-PhysicalTherapist");
		
		int option = Integer.parseInt(reader.readLine());
		switch(option)
		{
		case 1:
			PatientMenu patientMenu=new PatientMenu();
			try {
				patientMenu.registerPatient();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case 2:
			try {
				patientsOrAppointments();
			} catch (Exception e) {
				e.printStackTrace();
			}
			break;
		case 3:
			try
			{
				choose();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			break;
		}
		*/
		
		int option=DataObtention.readInt("What do you wanna do?\n1.-Register\n2.-Log in\n0.-Exit");
		switch(option)
		{
			case 1:
				System.out.println("You are a member of the staff of Human Resources, introduce your credentials");
				String username = DataObtention.readName("Username: ");
				System.out.println("Password: ");
				String pass =DataObtention.readLine();
				User staff = userManager.checkPasswordStaff(username, pass);
				if (staff==null)
				{
					System.out.println("The credentials are wrong, are you sure you are in Human Resources?");
				}
				else
				{
					StaffMenu staffMenu = new StaffMenu(); 		
					staffMenu.staffMenu(dm,ptm,pm,userManager);
				}
				break;
			case 2:
				String userName = DataObtention.readName("Username: ");
				System.out.println("Password: ");
				String password = DataObtention.readLine();
				User user = userManager.checkPassword(userName, password);
				if (user == null) 
				{
					System.out.println("Wrong credentials, please try again!");
				}
				else if (user.getRole().getRole().equalsIgnoreCase("doctor"))
				{
					patientsOrAppointments(userName);
				}
				else if (user.getRole().getRole().equalsIgnoreCase("patient")) 
				{
					//TODO poner el menu del paciente
				}
				else if (user.getRole().getRole().equalsIgnoreCase("physical therapist"))
				{
					choose(userName);
				}
				else 
				{
					System.out.println("Invalid role.");
				}
			case 0 :
				System.out.println("You have succesfully exit the program. Goodbye!");
		}
	}
	
	private static void patientsOrAppointments(String userName) throws Exception {
		while(true){
		
		int option = DataObtention.readInt("Welcome doctor, choose an option\n "+"Welcome doctor, choose an option\n "+"2.Check appointments\n ");
		switch(option){
		case 1:
			DoctorMenu doctorMenu= new DoctorMenu();
			try {
				
				doctorMenu.doctorMenu(dm,pm,userName);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			DoctorMenu doctorMenuApp = new DoctorMenu();
			try{
				
				doctorMenuApp.doctorAppointmentMenu(dm, am, pm,userName);
				
			}catch(Exception e){
				e.printStackTrace();
				}
			}
			break;
		}
		
	}
	
	private static void choose(String userName) throws Exception 
	{
		while(true)
		{
			int option =DataObtention.readInt("Welcome physical therapist, choose an option\n "+"1.Check patients\n "+"2.Check appointments\n ");
			PhysicalMenu physicalMenu= new PhysicalMenu();
			switch(option)
			{
				case 1:					
					try
					{
						physicalMenu.readTreatment(userName,ptm,pm);					
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					try
					{				
						physicalMenu.checkAppointments(userName,ptm);						
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
			}
		}
		
	}
	
	
	
	
	
	
}
