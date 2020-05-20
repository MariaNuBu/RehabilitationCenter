package ui;


import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
		for (Patient patient : patients) {
			System.out.println(patient);
		}
		if(patients.isEmpty())
		{
			System.out.println("There are no patients whith this name");
		}

		int patId= DataObtention.readInt("Please introduce the id of the patient you want to work with ");
		doctorSubMenu(patId,docID,pm,dm);
	}

	public void doctorAppointmentMenu(DoctorManager dm, AppointmentManager am, PatientManager pm,String userName) throws Exception{
		int docID = dm.searchDoctorByEmail(userName);
		doctorAppointmentSubMenu(docID, dm, am, pm);
	}

	private static void doctorSubMenu(Integer patID,Integer docID,PatientManager pm,DoctorManager dm)throws Exception{
		while (true) {
			int choice = DataObtention.readInt("Hello doctor , what do you want to do ? \n"+"1. Read the Medical History of your patient \n"+
					"2. Modify the Medical History of your patient \n"+"3. Create a new treatment for your patient \n"+
					"4. Modify one of the treatments of your patient \n"+"5. Delete one of the treatments of your patient \n"+
					"6. List all of the treatments of your patient \n"+"7. Read one of the treatments of your patient \n"+"0. Back \n");
			switch (choice) {
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

	 private static void modifyMedicalHistory(Integer patID,PatientManager pm,DoctorManager dm) throws NumberFormatException, IOException {
			showMedicalHistory(patID,pm);
			Patient p = pm.getPatient(patID);
			MedicalHistory mhToModify = pm.getMedicalHistory(p);
			System.out.println("Actual diseases: "+ mhToModify.getDiseases());
			System.out.println("Type the new disease of the patient or press enter to leave it as it is :");
			String newdisease= DataObtention.readLine();
			if(newdisease.equals("")) {
				newdisease = mhToModify.getDiseases();
			}
			System.out.println("Actual allergies: "+ mhToModify.getAllergies());
			System.out.println("Type the new allergie of the patient or press enter to leave it as it is :");
			String newallergie= DataObtention.readLine();
			if(newallergie.equals("")) {
				newallergie = mhToModify.getAllergies();
			}
			System.out.println("Actual surgeries: "+ mhToModify.getSurgeries());
			System.out.println("Type the new surgerie of the patient or press enter to leave it as it is :");
			String newsurgerie=DataObtention.readLine();
			if(newsurgerie.equals("")) {
				newsurgerie = mhToModify.getAllergies();
			}
			System.out.println("Actual weight: " + mhToModify.getWeightKg());
			System.out.println("Type the new weight of the patient or press enter to leave it as it is :");
			String newWeight =DataObtention.readLine();
			Float floatnewWeight;
			if(newWeight.equals("")) {
				floatnewWeight = mhToModify.getWeightKg();
			}
			else {
				floatnewWeight = Float.parseFloat(newWeight);
			}
			System.out.println("Actual height: " + mhToModify.getHeightCm());
			System.out.println("Type the new height of the patient or press enter to leave it as it is :");
			String newHeight = DataObtention.readLine();
			Integer intnewHeight;
			if(newHeight.equals("")) {
				intnewHeight = mhToModify.getHeightCm();
			}
			else {
				intnewHeight = Integer.parseInt(newHeight);
			}

			int id = mhToModify.getID();
			MedicalHistory updatedMedicalHistory = new MedicalHistory(id,mhToModify.getName(),mhToModify.getDOB(),newdisease,newallergie,
					newsurgerie,floatnewWeight,intnewHeight);
			dm.modifyMH(updatedMedicalHistory);
			System.out.println("The medical history of your patient has been successfully modified");

	}



	 private static void showMedicalHistory(Integer patID,PatientManager pm){
		 MedicalHistory mh= new MedicalHistory();
		 	Patient p = pm.getPatient(patID);
		 	System.out.println("Patient: \n-----------------------------------\n"+p);
		 	mh= pm.getMedicalHistory(p);
			System.out.println("This is the actual medical history of your patient:\n--------------------------------\n");
				System.out.println(mh);

			}

	private static void createTreatment(Integer patID,Integer docID,PatientManager pm,DoctorManager dm) throws IOException {
		listTreatments(patID,dm);
		System.out.println("For a new treatment:");
		String type =DataObtention.readName("Type of the new treatment: ");
		Integer length = DataObtention.readInt("Length of the new treatment: ");
		Treatment newTreatment = new Treatment(type,length);
		dm.createTreatment(newTreatment,patID,docID,pm);
		System.out.println("You have created a new treatment succesfully!");
	}


	private static void doctorAppointmentSubMenu(Integer docID, DoctorManager dm, AppointmentManager am, PatientManager pm) throws Exception {
		while(true){

			int option =DataObtention.readInt("Select one of this options\n "+"1.Read appointments\n "+"2.Add appointment\n "+"3.Modify appointment\n "+"4.Delete appointment\n"+"0.Return ");
			switch(option){

			case 1:
				List<Patient>currentPatients = dm.getDoctorsPatients(docID);
				listCurrentPatients(currentPatients);
				System.out.println("APPOINTMENTS");
				am.readAppointments(docID,pm,dm);
				break;
			case 2:
				List<Patient>currentPatients2 = dm.getDoctorsPatients(docID);
				listCurrentPatients(currentPatients2);
				System.out.println("---------------------------");
				System.out.println("CURRENT APPOINTMENTS");
				am.readAppointments(docID,pm,dm);
				Integer patId = DataObtention.readInt("Choose a patients ID for the new appointment");
				Patient patient = pm.getPatient(patId);
				PhysicalTherapist physicalTherapist = patient.getPhysicalTerapist();
				Doctor doctor = dm.getDoctor(docID);
				Appointment appointment = introduceDateAndTime();
				am.addAppointment(appointment, patient, doctor, physicalTherapist);
				break;
			case 3:
				List<Patient>currentPatients3 = dm.getDoctorsPatients(docID);
				listCurrentPatients(currentPatients3);
				System.out.println("CURRENT APPOINTMENTS");
				am.readAppointments(docID,pm,dm);
				Integer idAp =DataObtention.readInt("Select the ID of the appointment you want to modify");
				Appointment appointmentToModify = am.getAppointment(idAp);
				Appointment modifiedAppointment = modifyAppointment(appointmentToModify);
				//TODO
				/*
				LinkedList<ArrayList<Appointment>> appointmentsToCheck = am.checkCurrentAppointments(docID, appointmentToModify.getPat().getId());
				boolean taken = checkAppointments(appointmentsToCheck, modifiedAppointment, appointmentToModify);
				while(taken=true){
				modifiedAppointment = modifyAppointment(appointmentToModify);
				appointmentsToCheck = am.checkCurrentAppointments(docID, appointmentToModify.getPat().getId());
				taken = checkAppointments(appointmentsToCheck, modifiedAppointment, appointmentToModify);
				}
				*/
				am.modifyAppointment(modifiedAppointment);
				break;
			case 4:
				List<Patient>currentPatients4 = dm.getDoctorsPatients(docID);
				listCurrentPatients(currentPatients4);
				System.out.println("CURRENT APPOINTMENTS");
				am.readAppointments(docID,pm,dm);
				Integer apID = DataObtention.readInt("Select the ID of the appointment you want to delete");
				Appointment appointmentToDelete = am.getAppointment(apID);
				System.out.println("Are you sure you want to delete this appointment?-->Y/N");
				String answer = DataObtention.readLine();
				if(answer.equals("Y")){
					am.deleteAppointment(appointmentToDelete);
				}
				break;
			case 0:
				return;
			}

		}

	}




	private static void modifyTreatment(Integer patID,DoctorManager dm) throws NumberFormatException, IOException {
		 List<Treatment>treatmentList= new ArrayList<Treatment>();
			treatmentList= dm.listTreatments(patID);
			if(treatmentList.isEmpty())
			{
				System.out.println("This patient doesn't have any treatments yet");
			}
			else
			{
				System.out.println("This are the actual treatments of your patient:");
				for (Treatment treatment:treatmentList) {
					System.out.println(treatment);
				}
				System.out.println("-------------------------------------------------------");
				int treatID=DataObtention.readInt("Please, input the ID of the treatment you want to modify :");
				Treatment treatmentToModify= dm.getTreatment(treatID);
				System.out.println("Actual Type: "+treatmentToModify.getType());
				//If the user does not type anything the type of treatment  won´t change
				System.out.println("Type the new type of treatment or press enter to leave it as it is :");
				String newType= DataObtention.readLine();
				if(newType.equals("")) {
					newType=treatmentToModify.getType();
				}
				System.out.println("Actual Lenght: "+treatmentToModify.getLenght());
				//If the user does not type anything the type of treatment  won´t change
				System.out.println("Type the new lenght for the treatment or press enter to leave it as it is :");
				String newLength= DataObtention.readLine();
				int intnewLength;
				if(newLength.equals("")) {
					intnewLength=treatmentToModify.getLenght();
				}else {
					intnewLength=Integer.parseInt(newLength);
				}
				int id=treatmentToModify.getId();
				Treatment updatedTreatment= new Treatment(id,newType,intnewLength);
				dm.modifyTreatment(updatedTreatment);
				System.out.println("The treatment has been successfully modified");
			} 
	 }
	
	 private static void listTreatments(Integer patID,DoctorManager dm) {
		 List<Treatment>treatmentList= new ArrayList<Treatment>();
			treatmentList= dm.listTreatments(patID);
			if(treatmentList.isEmpty())
			{
				System.out.println("This patient doesn't have any treatments yet");
			}
			else
			{
				System.out.println("This are the actual treatments of your patient:");
				for (Treatment treatment:treatmentList) {
					System.out.println("ID: "+treatment.getId()+", Type: "+treatment.getType());
				}
			}		
	 }


	private static Appointment introduceDateAndTime() throws Exception{
		System.out.println("Introduce a date: yyyy-mm-dd");
		String dateString = DataObtention.readLine();
		Date appointmentDate = Date.valueOf(LocalDate.parse(dateString, formatter));
		System.out.println("Introduce the time: hh:mm:ss");
		String timeString =DataObtention.readLine();
		Time appointmentTime = Time.valueOf(timeString);
		Appointment appointment = new Appointment(appointmentDate, appointmentTime);
		return appointment;
		}




	private static void deleteTreatment(Integer patID,DoctorManager dm,PatientManager pm) throws NumberFormatException, IOException {
		 List<Treatment>treatmentList= new ArrayList<Treatment>();
			treatmentList= dm.listTreatments(patID);
			if(treatmentList.isEmpty())
			{
				System.out.println("This patient doesn't have any treatments yet");
			}
			else
			{
				System.out.println("This are the actual treatments of your patient:");
				for (Treatment treatment:treatmentList) {
					System.out.println(treatment);
				}
				System.out.println("-------------------------------------------------------");
				int treatID=DataObtention.readInt("Please, input the ID of the treatment you want to delete :");
				Treatment treatmentToDelete= dm.getTreatment(treatID);
				dm.deleteTreatment(treatmentToDelete,patID);
			}		
	}
	private static void readTreatment(Integer patID,DoctorManager dm) throws NumberFormatException, IOException {
		System.out.println("-------------------------------------------------------");
		int treatID=DataObtention.readInt("Please, input the ID of the treatment you want to read :");
		Treatment treatmentToRead= dm.getTreatment(treatID);
		if(treatmentToRead==null) {
			System.out.println("The chosen ID doesn't correspond to any existing treatment ");
		}else {
		System.out.println(dm.readTreatment(treatmentToRead));
		}
	}

	private static void listCurrentPatients(List<Patient>currentPatients){
		System.out.println("This are your current patients");
		for(int i=0; i<currentPatients.size();i++){
			Patient patient = currentPatients.get(i);
			System.out.println(patient.toString());
		}
	}

	public static Appointment modifyAppointment(Appointment appointmentToModify) throws Exception{
		Appointment modifiedAppointment = null;
		System.out.println("Current date --> "+ appointmentToModify.getDate());
		System.out.println("Current time --> "+ appointmentToModify.getTime());
		System.out.println("Introduce new date [yyyy-mm-dd] or press enter");
		String newDate = DataObtention.readLine();
		Date date;
		if(newDate.equals("")){
			date = appointmentToModify.getDate();
		}else{
			date = Date.valueOf(LocalDate.parse(newDate, formatter));
		}
		System.out.println("Introduce new time [HH:mm:ss] or press enter");
		String newTime = DataObtention.readLine();
		Time time;
		if(newTime.equals("")){
			time = appointmentToModify.getTime();
		}else{
			time = Time.valueOf(newTime);
		}
		modifiedAppointment = new Appointment(appointmentToModify.getId(), date, time);
		return modifiedAppointment;

	}

	public static boolean checkAppointments(LinkedList<ArrayList<Appointment>>appointments, Appointment modifiedAppointment, Appointment appointmentToModify) throws Exception{
		boolean appointmentTaken1 = false;
		boolean appointmentTaken2 = false;
		boolean taken = false;
		ArrayList<Appointment> doctorsAppointments = appointments.get(0);
		Date dateToCheck = modifiedAppointment.getDate();
		Time timeToCheck = modifiedAppointment.getTime();
		for(int i=0; i<doctorsAppointments.size(); i++){
			Date date = doctorsAppointments.get(i).getDate();
			Time time = doctorsAppointments.get(i).getTime();
			if(date.equals(dateToCheck) && time.equals(timeToCheck)){
				System.out.println("This date is reserved for another appointment");
				appointmentTaken1 = true;
				break;
			}
		}
		ArrayList<Appointment> patientAppointments = appointments.get(1);
		for(int j=0; j<patientAppointments.size(); j++){
			Date date = patientAppointments.get(j).getDate();
			Time time = patientAppointments.get(j).getTime();
			if(date.equals(dateToCheck) && time.equals(timeToCheck)){
				System.out.println("This patient has another appointment, select another date and time");
				appointmentTaken2 = true;
				break;
			}
		}
		if(appointmentTaken1 == true || appointmentTaken2 == true){
			taken = true;
		}

		return taken;
	}

}


