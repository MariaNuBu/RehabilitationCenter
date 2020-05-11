package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javax.persistence.NoResultException;

import db.interfaces.*;
import db.jpa.JPAUserManager;
import db.sqlite.*;
import pojos.*;
import pojos.users.User;

public class Main
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
				User staff = null;
				System.out.println("You are a member of the staff of Human Resources, introduce your credentials");
				while (staff == null)
				{
					String username = DataObtention.readName("Username: ");
					System.out.println("Password: ");
					String pass =DataObtention.readLine();
					
					try
					{
						staff = userManager.checkPasswordStaff(username, pass);
					}
					catch(NoResultException e)
					{
						System.out.println("Wrong credentials, please try again");
					}
				}				
				StaffMenu staffMenu = new StaffMenu();
				staffMenu.staffMenu(dm,ptm,pm,am,userManager);
				break;
			case 2:
				System.out.println("Please introduce your credentials");
				String username = "";
				User user = null;
				while(user==null)
				{
					username = DataObtention.readName("Username: ");
					System.out.println("Password: ");
					String password = DataObtention.readLine();
					try
					{
						user = userManager.checkPassword(username, password);
					}
					catch(NoResultException e)
					{
						System.out.println("Wrong credentials, please try again");
					}
				}				
				if (user.getRole().getRole().equalsIgnoreCase("doctor"))
				{
					patientsOrAppointments(username);
				}
				else if (user.getRole().getRole().equalsIgnoreCase("patient")) 
				{
					patientChoose(username, pm, dm, ptm, am);
				}
				else if (user.getRole().getRole().equalsIgnoreCase("physical therapist"))
				{
					choose(username);
				}
				else 
				{
					System.out.println("Invalid role.");
				}
			case 0 :
				System.out.println("You have succesfully exit the program. Goodbye!");
				break;
		}
		
	}

	private static void patientChoose(String username, PatientManager pm, DoctorManager dm, PhysicalTherapistManager ptm, AppointmentManager am) throws Exception
	{
		Integer patID = pm.searchPatientByEmail(username);
		while (true)
		{
			PatientMenu patientmenu = new PatientMenu();
			int option = DataObtention.readInt("Welcome patient, choose an option\n 1.-Change password\n 2.-Read appointments\n 3.-Add appointment\n 4.-Exit");
			switch(option)
			{
				case 1:
					changePassword(username);
					break;
				case 2:
					patientmenu.readAppointments(patID, pm, ptm, dm,am);					
					break;
				case 3:
					patientmenu.addAppointment(patID, pm, ptm, dm, am);
					break;
				case 4:
					break;
			}
			if(option==4)
			{
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
					doctorMenu.doctorMenu(dm,pm,username);
					
				}catch(Exception e) {
					e.printStackTrace();
				}
				break;
			case 2:
				DoctorMenu doctorMenuApp = new DoctorMenu();
				try{
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
		if(option==4)
		{
			break;
		}
		}
	}

	
	private static void choose(String username) throws Exception 
	{
		while(true)
		{
			int option =DataObtention.readInt("Welcome physical therapist, choose an option\n 1.-Check patients\n 2.-Check appointments\n 3.-Change password\n 4.Exit");
			PhysicalMenu physicalMenu= new PhysicalMenu();
			switch(option)
			{
				case 1:					
					try
					{
						physicalMenu.readTreatment(username,ptm,pm);					
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					try
					{	
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
			if(option==4)
			{
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