package ui;


import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.xml.bind.ParseConversionEvent;

import db.interfaces.*;
import db.sqlite.SQLiteManager;
import pojos.*;


public class DoctorMenu {

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DBManager db;
	private static DoctorManager dm;
	private static AppointmentManager am;
	private static PatientManager pm;

	public  void doctorMenu(DoctorManager dm,PatientManager pm,String username) throws Exception
	{
		String name = DataObtention.readName("Please introduce the name of the patient you want to work with");
		Integer docID=dm.searchDoctorByEmail(username);
		List<Patient> patients = dm.SearchByName(name,docID);
		for (Patient patient : patients)
		{
			System.out.println(patient);
		}
		if(patients.isEmpty())
		{
			System.out.println("There are no patients whith this name");
		}
		else
		{
			int patId= DataObtention.readInt("Please introduce the id of the patient you want to work with ");
			doctorSubMenu(patId,docID,pm,dm);
		}		
	}

	public void doctorAppointmentMenu(DoctorManager dm, AppointmentManager am, PatientManager pm,String userName, PhysicalTherapistManager ptm) throws Exception
	{
		int docID = dm.searchDoctorByEmail(userName);
		doctorAppointmentSubMenu(docID, dm, am, pm, ptm);
	}

	private static void doctorSubMenu(Integer patID,Integer docID,PatientManager pm,DoctorManager dm)throws Exception
	{
		while (true) 
		{
			int choice = DataObtention.readInt("Hello doctor , what do you want to do ? \n"+"1. Read the Medical History of your patient \n"+
					"2. Modify the Medical History of your patient \n"+"3. Create a new treatment for your patient \n"+
					"4. Modify one of the treatments of your patient \n"+"5. Delete one of the treatments of your patient \n"+
					"6. List all of the treatments of your patient \n"+"7. Read one of the treatments of your patient \n"+"0. Back \n");
			switch (choice) 
			{
				case 1:
					showMedicalHistory(patID,pm);
					break;
				case 2:
					modifyMedicalHistory(patID,pm,dm);
					break;
				case 3:
					createTreatment(patID,docID,pm,dm);
					break;
				case 4:
					modifyTreatment(patID,dm);
					break;
				case 5:
					deleteTreatment(patID,dm,pm);
					break;
				case 6:
					listTreatments(patID,dm);
					//This only shows the ID and the type of the treatment
					break;
				case 7:
					readTreatment(patID,dm);
					//Is used after list to read a specific treatment as we need it's ID
					break;
				case 0:
					return;
				default:
					break;
			}
		}
	}

	 private static void modifyMedicalHistory(Integer patID,PatientManager pm,DoctorManager dm) throws NumberFormatException, IOException 
	 {
		showMedicalHistory(patID,pm);
		Patient p = pm.getPatient(patID);
		MedicalHistory mhToModify = pm.getMedicalHistory(p);
		System.out.println("Actual diseases: "+ mhToModify.getDiseases());
		System.out.println("Type the new disease of the patient or press enter to leave it as it is :");
		String newdisease= DataObtention.readLine();
		if(newdisease.equals("")) 
		{
			newdisease = mhToModify.getDiseases();
		}
		System.out.println("Actual allergies: "+ mhToModify.getAllergies());
		System.out.println("Type the new allergie of the patient or press enter to leave it as it is :");
		String newallergie= DataObtention.readLine();
		if(newallergie.equals(""))
		{
			newallergie = mhToModify.getAllergies();
		}
		System.out.println("Actual surgeries: "+ mhToModify.getSurgeries());
		System.out.println("Type the new surgeries of the patient or press enter to leave it as it is :");
		String newsurgerie=DataObtention.readLine();
		if(newsurgerie.equals("")) 
		{
			newsurgerie = mhToModify.getSurgeries();
		}
		System.out.println("Actual weight: " + mhToModify.getWeightKg());
		System.out.println("Type the new weight of the patient or press enter to leave it as it is :");
		String newWeight =DataObtention.readLine();
		Float floatnewWeight;
		if(newWeight.equals(""))
		{
			floatnewWeight = mhToModify.getWeightKg();
		}
		else 
		{
			floatnewWeight = Float.parseFloat(newWeight);
		}
		System.out.println("Actual height: " + mhToModify.getHeightCm());
		System.out.println("Type the new height of the patient or press enter to leave it as it is :");
		String newHeight = DataObtention.readLine();
		Integer intnewHeight;
		if(newHeight.equals("")) 
		{
			intnewHeight = mhToModify.getHeightCm();
		}
		else 
		{
			intnewHeight = Integer.parseInt(newHeight);
		}

		int id = mhToModify.getID();
		MedicalHistory updatedMedicalHistory = new MedicalHistory(id,mhToModify.getName(),mhToModify.getDOB(),newdisease,newallergie,
				newsurgerie,floatnewWeight,intnewHeight);
		dm.modifyMH(updatedMedicalHistory);
		System.out.println("The medical history of your patient has been successfully modified");
	}



	 private static void showMedicalHistory(Integer patID,PatientManager pm)
	 {
		MedicalHistory mh= new MedicalHistory();
	 	Patient p = pm.getPatient(patID);
	 	System.out.println("Patient: \n-----------------------------------\n"+p);
	 	mh= pm.getMedicalHistory(p);
		System.out.println("This is the actual medical history of your patient:\n--------------------------------\n");
		System.out.println(mh);
	}

	private static void createTreatment(Integer patID,Integer docID,PatientManager pm,DoctorManager dm) throws IOException 
	{
		listTreatments(patID,dm);
		System.out.println("For a new treatment:");
		String type =DataObtention.readName("Type of the new treatment: ");
		Integer length = DataObtention.readInt("Length of the new treatment (weeks): ");
		Treatment newTreatment = new Treatment(type,length);
		dm.createTreatment(newTreatment,patID,docID,pm);
		System.out.println("You have created a new treatment succesfully!");
	}


	private static void doctorAppointmentSubMenu(Integer docID, DoctorManager dm, AppointmentManager am, PatientManager pm, PhysicalTherapistManager ptm) throws Exception 
	{
		while(true)
		{
			int option =DataObtention.readInt("Select one of this options\n "+"1.Read appointments\n "+"2.Add appointment\n "+"3.Modify appointment\n "+"4.Delete appointment\n"+"0.Return ");
			switch(option)
			{

				case 1:
					List<Patient>currentPatients = dm.getDoctorsPatients(docID);
					if(currentPatients.isEmpty())
					{
						System.out.println("You don´t have any patients yet");
					}
					else
					{
						listCurrentPatients(currentPatients);
						System.out.println("---CURRENT APPOINTMENTS---");
						List<Appointment>currentAppointments = am.getDoctorsAppointments(docID, pm, ptm, dm);
						if(currentAppointments.isEmpty())
						{
							System.out.println("You don´t have any appointments yet");
						}
						else
						{
							for(Appointment appointment : currentAppointments)
							{
								System.out.println(appointment);
							}
						}
					}
					break;
				case 2:
					List<Patient>currentPatients2 = dm.getDoctorsPatients(docID);
					if(currentPatients2.isEmpty())
					{
						System.out.println("You don´t have any patients yet");
					}
					else
					{
						listCurrentPatients(currentPatients2);
						System.out.println("---------------------------");
						System.out.println("CURRENT APPOINTMENTS");
						List<Appointment>currentAppointments = am.getDoctorsAppointments(docID, pm, ptm, dm);
						if(currentAppointments.isEmpty())
						{
							System.out.println("You don´t have any appointments yet");
						}
						else
						{
							for(Appointment appointment : currentAppointments)
							{
								System.out.println(appointment);
							}
						}
						Integer patId = DataObtention.readInt("Choose a patients ID for the new appointment");
						Patient patient = pm.getPatient(patId);
						PhysicalTherapist physicalTherapist = patient.getPhysicalTerapist();
						Doctor doctor = dm.getDoctor(docID);
						Appointment appointment = null;
						boolean taken;
						try
						{
							appointment = introduceDateAndTime();
							List<Appointment> ptApps = am.getPhysicalTherapistAppointments(physicalTherapist.getId(), pm, ptm, dm);
							taken = checkAppointments(ptApps, appointment);
							while(taken==true)
							{
								System.out.println("Please try with another appointment");
								appointment = introduceDateAndTime();
								taken = checkAppointments(ptApps, appointment);
							}
							List<Appointment>patApps = am.getPatientsAppointments(patId, pm, ptm, dm);
							boolean taken2 = checkAppointments(patApps, appointment);
							while(taken2 == true)
							{
								System.out.println("Please try with another appointment");
								appointment = introduceDateAndTime();
								taken2 = checkAppointments(patApps, appointment);
							}
							am.addAppointment(appointment, patient, doctor, physicalTherapist);
							System.out.println("Appointment added successfully!");
						}
						catch(Exception e)
						{
							e.printStackTrace();
							System.out.println("Error while introducing the appointment");
						}
					}
					break;
				case 3:
					List<Patient>currentPatients3 = dm.getDoctorsPatients(docID);
					if(currentPatients3.isEmpty())
					{
						System.out.println("You don´t have any patients yet");
					}
					else
					{
						listCurrentPatients(currentPatients3);
						System.out.println("---------------------------");
						System.out.println("CURRENT APPOINTMENTS");
						List<Appointment>currentAppointments = am.getDoctorsAppointments(docID, pm, ptm, dm);
						if(currentAppointments.isEmpty())
						{
							System.out.println("You don´t have any appointments yet");
						}
						else
						{
							for(Appointment appointment : currentAppointments)
							{
								System.out.println(appointment);
							}
							Integer idAp =DataObtention.readInt("Select the ID of the appointment you want to modify");
							Appointment appointmentToModify = am.getAppointment(idAp);
							Patient pat = appointmentToModify.getPat();
							PhysicalTherapist pt = appointmentToModify.getPt();
							try
							{
								Appointment modifiedAppointment = modifyAppointment(appointmentToModify);
								List<Appointment> ptApps = am.getPhysicalTherapistAppointments(pt.getId(), pm, ptm, dm);
								boolean taken = checkAppointments(ptApps, modifiedAppointment);
								while(taken==true)
								{
									System.out.println("Please try with another appointment");
									modifiedAppointment = modifyAppointment(appointmentToModify);
									taken = checkAppointments(ptApps, modifiedAppointment);
								}
								List<Appointment>patApps = am.getPatientsAppointments(pat.getId(), pm, ptm, dm);
								boolean taken2 = checkAppointments(patApps, modifiedAppointment);
								while(taken2 == true)
								{
									System.out.println("Please try with another appointment");
									modifiedAppointment = modifyAppointment(appointmentToModify);
									taken2 = checkAppointments(ptApps, modifiedAppointment);
								}
								am.modifyAppointment(modifiedAppointment);
								System.out.println("Appointment added successfully!");
							}
							catch(Exception e)
							{
								e.printStackTrace();
								System.out.println("Error while introducing the appointment");
							}
						}
					}
					break;
				case 4:
					List<Patient>currentPatients4 = dm.getDoctorsPatients(docID);
					if(currentPatients4.isEmpty())
					{
						System.out.println("You don´t have any patients yet");
					}
					else
					{
						listCurrentPatients(currentPatients4);
						System.out.println("CURRENT APPOINTMENTS");
						List<Appointment>currentAppointments = am.getDoctorsAppointments(docID, pm, ptm, dm);
						if(currentAppointments.isEmpty())
						{
							System.out.println("You don´t have any appointments yet");
						}
						else
						{
							for(Appointment appointment : currentAppointments)
							{
								System.out.println(appointment);
							}
							Integer apID = DataObtention.readInt("Select the ID of the appointment you want to delete");
							Appointment appointmentToDelete = am.getAppointment(apID);
							System.out.println("Are you sure you want to delete this appointment?-->Y/N");
							String answer = DataObtention.readLine();
							if(answer.equalsIgnoreCase("Y"))
							{
								am.deleteAppointment(appointmentToDelete);
							}
						}
					}	
					break;
				case 0:
					return;
			}
		}
	}

	private static void modifyTreatment(Integer patID,DoctorManager dm) throws NumberFormatException, IOException 
	{
		 List<Treatment>treatmentList= new ArrayList<Treatment>();
		treatmentList= dm.listTreatments(patID);
		if(treatmentList.isEmpty())
		{
			System.out.println("This patient doesn't have any treatments yet");
		}
		else
		{
			System.out.println("This are the actual treatments of your patient:");
			for (Treatment treatment:treatmentList) 
			{
				System.out.println(treatment);
			}
			System.out.println("-------------------------------------------------------");
			int treatID=DataObtention.readInt("Please, input the ID of the treatment you want to modify :");
			Treatment treatmentToModify= dm.getTreatment(treatID);
			System.out.println("Actual Type: "+treatmentToModify.getType());
			//If the user does not type anything the type of treatment  won´t change
			System.out.println("Type the new type of treatment or press enter to leave it as it is :");
			String newType= DataObtention.readLine();
			if(newType.equals(""))
			{
				newType=treatmentToModify.getType();
			}
			System.out.println("Actual Lenght: "+treatmentToModify.getLenght());
			//If the user does not type anything the type of treatment  won´t change
			System.out.println("Type the new lenght for the treatment or press enter to leave it as it is :");
			String newLength= DataObtention.readLine();
			int intnewLength;
			if(newLength.equals("")) 
			{
				intnewLength=treatmentToModify.getLenght();
			}
			else
			{
				intnewLength=Integer.parseInt(newLength);
			}
			int id=treatmentToModify.getId();
			Treatment updatedTreatment= new Treatment(id,newType,intnewLength);
			dm.modifyTreatment(updatedTreatment);
			System.out.println("The treatment has been successfully modified");
		}
	 }

	 private static void listTreatments(Integer patID,DoctorManager dm)
	 {
		List<Treatment>treatmentList= new ArrayList<Treatment>();
		treatmentList= dm.listTreatments(patID);
		if(treatmentList.isEmpty())
		{
			System.out.println("This patient doesn't have any treatments yet");
		}
		else
		{
			System.out.println("This are the actual treatments of your patient:");
			for (Treatment treatment:treatmentList) 
			{
				System.out.println("ID: "+treatment.getId()+", Type: "+treatment.getType());
			}
		}
	 }


	 private static Appointment introduceDateAndTime() throws Exception
	 {
		Boolean dateincorrect = true;
		Boolean timeincorrect = true;
		String dateString="";
		Date appointmentDate=null;
		String timeString="";
		Time appointmentTime=null;
		while(dateincorrect)
		{
			try
			{
				System.out.println("Introduce a date: yyyy-mm-dd");
				dateString = DataObtention.readLine();
				appointmentDate = Date.valueOf(LocalDate.parse(dateString, formatter));
				dateincorrect=false;
			}
			catch (DateTimeParseException e)
			{
				System.out.println("Date wrong introduced");
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Date wrong introduced");
			}
		}
		while(timeincorrect)
		{
			try
			{
				System.out.println("Introduce an hour: hh:mm:ss");
				timeString = DataObtention.readLine();
				appointmentTime = Time.valueOf(timeString);
				timeincorrect=false;
			}
			catch (DateTimeParseException e)
			{
				System.out.println("Time wrong introduced");
			}
			catch(IllegalArgumentException ex)
			{
				System.out.println("Time wrong introduced");
			}
		}
		Appointment appointment = new Appointment(appointmentDate, appointmentTime);
		return appointment;
	}



	private static void deleteTreatment(Integer patID,DoctorManager dm,PatientManager pm) throws NumberFormatException, IOException
	{
		List<Treatment>treatmentList= new ArrayList<Treatment>();
		treatmentList= dm.listTreatments(patID);
		if(treatmentList.isEmpty())
		{
			System.out.println("This patient doesn't have any treatments yet");
		}
		else
		{
			System.out.println("This are the actual treatments of your patient:");
			for (Treatment treatment:treatmentList) 
			{
				System.out.println(treatment);
			}
			System.out.println("-------------------------------------------------------");
			int treatID=DataObtention.readInt("Please, input the ID of the treatment you want to delete :");
			Treatment treatmentToDelete= dm.getTreatment(treatID);
			dm.deleteTreatment(treatmentToDelete,patID);
		}
	}
	
	private static void readTreatment(Integer patID,DoctorManager dm) throws NumberFormatException, IOException 
	{
		System.out.println("-------------------------------------------------------");
		int treatID=DataObtention.readInt("Please, input the ID of the treatment you want to read :");
		Treatment treatmentToRead= dm.getTreatment(treatID);
		if(treatmentToRead==null) 
		{
			System.out.println("The chosen ID doesn't correspond to any existing treatment ");
		}
		else 
		{
			System.out.println(dm.readTreatment(treatmentToRead));
		}
	}

	private static void listCurrentPatients(List<Patient>currentPatients)
	{
		System.out.println("This are your current patients");
		for(int i=0; i<currentPatients.size();i++)
		{
			Patient patient = currentPatients.get(i);
			System.out.println(patient.toString());
		}
	}

	public static Appointment modifyAppointment(Appointment appointmentToModify) throws Exception
	{
		Appointment modifiedAppointment = null;
		System.out.println("Current date --> "+ appointmentToModify.getDate());
		System.out.println("Current time --> "+ appointmentToModify.getTime());
		String newDate = "";
		Date date=null;
		boolean dateincorrect = true;		
		while (dateincorrect)
		{
			System.out.println("Introduce new date [yyyy-mm-dd] or press enter");
			newDate = DataObtention.readLine();
			if(newDate.equals(""))
			{
				date = appointmentToModify.getDate();
			}
			else
			{
				try
				{
					date = Date.valueOf(LocalDate.parse(newDate, formatter));
					dateincorrect = false;
				}
				catch (DateTimeParseException e)
				{
					System.out.println("Date wrong introduced");
				}
				catch(IllegalArgumentException ex)
				{
					System.out.println("Date wrong introduced");
				}
			}			
		}			
		System.out.println("Introduce new time [HH:mm:ss] or press enter");
		String newTime = "";
		Time time = null;
		boolean timeincorrect = true;		
		while(timeincorrect)
		{
			System.out.println("Introduce new time [HH:mm:ss] or press enter");
			newTime = DataObtention.readLine();
			if(newTime.equals(""))
			{
				time = appointmentToModify.getTime();
			}
			else
			{
				try
				{
					time = Time.valueOf(newTime);
					timeincorrect = false;
				}
				catch (DateTimeParseException e)
				{
					System.out.println("Date wrong introduced");
					System.out.println("Introduce new time [HH:mm:ss] or press enter");
				}
				catch(IllegalArgumentException ex)
				{
					System.out.println("Date wrong introduced");
					System.out.println("Introduce new time [HH:mm:ss] or press enter");
				}
			}				
		}		
		modifiedAppointment = new Appointment(appointmentToModify.getId(), date, time);
		return modifiedAppointment;
	}
	
	private static boolean checkAppointments(List<Appointment>Apps, Appointment appointment) throws Exception
	{
		Date dateToCheck = appointment.getDate();
		Time timeToCheck = appointment.getTime();
		boolean taken = false;
		for(Appointment app: Apps)
		{
			Date date = app.getDate();
			Time time = app.getTime();
			if(date.equals(dateToCheck) && time.equals(timeToCheck))
			{
				System.out.println("Ups, looks like this appointment is not available");
				taken = true;
			}
		}
		return taken;
	}

}


