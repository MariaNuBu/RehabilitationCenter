package ui;
import java.io.BufferedReader;
//import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));;
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
				//TODO poner el menu del patient Menu
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

		int option=DataObtention.readInt("What do you wanna do?\n1.-Log in staff\n2.-Log in\n0.-Exit");
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
					staffMenu.staffMenu(dm,ptm,pm,am,userManager);
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
					patientChoose(userName);
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

	private static void patientChoose(String username)
	{
		while (true)
		{
			int option = DataObtention.readInt("Welcome patient, choose an option\n 1.-Add appointment\n 2.-Change password\n 3.-Exit");
			switch(option)
			{
				case 1:
					break;
				case 2:
					changePassword(username);
					break;
				case 3:
					break;
			}
		}
	}


	private static void patientsOrAppointments(String username) throws Exception {
		while(true){

		int option = DataObtention.readInt("Welcome doctor, choose an option\n "+"1.-Check patients\n "+"2.-Check appointments\n 3.-Change password\n 4.-Exit ");
		switch(option)
		{
			case 1:
				DoctorMenu doctorMenu= new DoctorMenu();
				try {
					//TODO poner el username
					doctorMenu.doctorMenu(dm,pm,username);

				}catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				DoctorMenu doctorMenuApp = new DoctorMenu();
				try{
					//TODO poner el username
					doctorMenuApp.doctorAppointmentMenu(dm, am, pm,username);

				}catch(Exception e){
					e.printStackTrace();
					}
				break;
			case 3:
				changePassword(username);
				break;
			case 4:
				break;
		}



		}

	}
	//TODO poner el user name String userName
	private static void choose(String username) throws Exception
	{
		while(true)
		{
			int option =DataObtention.readInt("Welcome physical therapist, choose an option\n 1.Check patients\n 2.Check or add appointments\n 3.-Change password\n 4.Exit");
			PhysicalMenu physicalMenu= new PhysicalMenu();
			switch(option)
			{
				case 1:
					try
					{
						//TODO poner el username
						physicalMenu.readTreatment(username,ptm,pm);
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					try
					{
						//TODO poner el username
						physicalMenu.checkAppointments(username,ptm,am,pm,dm);
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					break;
				case 3:
					changePassword(username);
					break;
				case 4:
					break;
			}
		}

	}

	private static void changePassword (String username)
	{
		try
		{
			User user = null;
			while (user==null)
			{
				System.out.println("Introduce your current password");
				String actualpass = DataObtention.readLine();
				user = userManager.checkPassword(username, actualpass);
				if (user == null)
				{
					System.out.println("Wrong credentials, please try again!");
				}
			}
			System.out.println("Introduce your new password");
			String newpass = DataObtention.readLine();
			String checkpass="";
			while(!newpass.equals(checkpass))
			{
				System.out.println("Introduce yout new password again");
				checkpass=DataObtention.readLine();
			}
			userManager.changePassword(username,newpass);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}





}
