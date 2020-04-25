package ui;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
		userManager = new JPAUserManager();
		userManager.connect();
		
		BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
		System.out.println("WELCOME TO OUR PARALIMPICS REHABILITATION CENTER");
		/*
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
		
		System.out.println("What do you wanna do?\n1.-Register\n2.-Log in\n0.-Exit");
		int option=Integer.parseInt(reader.readLine());
		switch(option)
		{
			case 1:
				System.out.println("You are a member of the staff of Human Resources, introduce your credentials");
				System.out.println("Username: ");
				String username = reader.readLine();
				System.out.println("Password: ");
				String pass = reader.readLine();
				User staff = userManager.checkPasswordStaff(username, pass);
				if (staff==null)
				{
					System.out.println("The credentials are wrong, are you sure you are in Human Resources?");
				}
				else
				{
					StaffMenu staffMenu = new StaffMenu(); 					
				}
				break;
			case 2:
				System.out.println("Username: ");
				String userName = reader.readLine();
				System.out.println("Password: ");
				String password = reader.readLine();
				User user = userManager.checkPassword(userName, password);
				if (user == null) 
				{
					System.out.println("Wrong credentials, please try again!");
				}
				else if (user.getRole().getRole().equalsIgnoreCase("doctor"))
				{
					patientsOrAppointments();
				}
				else if (user.getRole().getRole().equalsIgnoreCase("patient")) 
				{
					//TODO poner el menu del paciente
				}
				else if (user.getRole().getRole().equalsIgnoreCase("physical therapist"))
				{
					choose();
				}
				else 
				{
					System.out.println("Invalid role.");
				}
				
		}
		
		
		
	}
	
	private static void patientsOrAppointments() throws Exception {
		while(true){
		System.out.println("Welcome doctor, choose an option");
		System.out.println("1.Check patients");
		System.out.println("2.Check appointments");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		int option = Integer.parseInt(reader.readLine());
		switch(option){
		case 1:
			DoctorMenu doctorMenu= new DoctorMenu();
			try {
				
				doctorMenu.doctorMenu(dm);
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			break;
		case 2:
			DoctorMenu doctorMenuApp = new DoctorMenu();
			try{
				
				doctorMenuApp.doctorAppointmentMenu();
				
			}catch(Exception e){
				e.printStackTrace();
				}
			}
			break;
		}
		
	}
	
	private static void choose() throws Exception 
	{
		while(true)
		{
			System.out.println("Welcome physical therapist, choose an option");
			System.out.println("1.Check patients");
			System.out.println("2.Check appointments");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			int option = Integer.parseInt(reader.readLine());
			PhysicalMenu physicalMenu= new PhysicalMenu();
			switch(option)
			{
				case 1:					
					try
					{
						physicalMenu.readTreatment();					
					}
					catch(Exception e) {
						e.printStackTrace();
					}
					break;
				case 2:
					try
					{				
						physicalMenu.checkAppointments();						
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
