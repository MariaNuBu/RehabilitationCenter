package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.ParseConversionEvent;

import db.interfaces.*;
import db.sqlite.SQLiteManager;
import pojos.*;


public class DoctorMenu {
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));

	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
	private static DBManager db;
	private static DoctorManager dm;
	private static AppointmentManager am;


	private static PatientManager pm;
	public  void doctorMenu() throws Exception {
		
		System.out.println("Please introduce the name of the patient you want to work with");
		String name = reader.readLine();
		Integer docID=1;// cambiar cuando este JPA
		List<Patient> patients = dm.SearchByName(name,docID);
		for (Patient patient : patients) {
			System.out.println(patient.toString());
		}
		System.out.println("Please introduce the id of the patient you want to work with ");
		int ptId=Integer.parseInt(reader.readLine());
		doctorSubMenu(ptId);
	}
	
	
	

	public void doctorAppointmentMenu() throws Exception{

		System.out.println("Introduce your name");
		String doctorName = reader.readLine();
		List<Doctor> docs = dm.searchDoctorByName(doctorName);
		for(int i=0; i<docs.size();i++){
			System.out.println(docs.get(i).getId());
			System.out.println(docs.get(i).getName());
			System.out.println(docs.get(i).geteMail());
		}
		System.out.println("Write to confirm your ID");
		int docID = Integer.parseInt(reader.readLine());
		doctorAppointmentSubMenu(docID);
	}

	private static void doctorSubMenu(Integer patID)throws Exception{
		while (true) {
			System.out.println("Hello doctor , what do you want to do ?");
			System.out.println("1. Read the Medical History of your patient");
			System.out.println("2. Modify the Medical History of your patient");
			System.out.println("3. Create a new treatment for your patient ");
			System.out.println("4.Modify one of the treatments of your patient");
			System.out.println("5.Delete one of the treatments of your patient");
			System.out.println("6.List all of the treatments of your patient");
			System.out.println("7.Read one of the treatments of your patient");
			System.out.println("0. Back");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				ReadMedicalHistory(patID);
				break;
			case 2:
				ModifyMedicalHistory(patID);
				break;
			case 3:
				createTreatment(patID);
			case 4:
				ModifyTreatment(patID);
				break;
			case 5:
				DeleteTreatment(patID);
				break;
			case 6:
				ListTreatments(patID);
				break;
			case 7:
				ReadTreatment(patID);
				break;
			case 0:
				return;
			default:
				break;
			}
 
	
	 }
 }
 
 
 private static void ReadMedicalHistory(Integer patID) {
	 ShowMedicalHistory(patID);
 }
 
 
 
 private static void ModifyMedicalHistory(Integer patID) throws NumberFormatException, IOException {
		ShowMedicalHistory(patID);
		Patient p = pm.getPatient(patID);
		MedicalHistory mhToModify = pm.getMedicalHistory(p);
		System.out.println("Actual name: "+ mhToModify.getName());
		System.out.println("Type the new name of the patient or press enter to leave it as it is :");
		String newName = reader.readLine();
		if(newName.equals("")) {
			newName = mhToModify.getName();
		}
		System.out.println("Actual date of birth:" + mhToModify.getDOB());
		System.out.println("Type the new date of birth of the patient or press enter to leave it as it is :");
		String newDob = reader.readLine();
		Date datenewDob;
		if(newDob.equals("")) {
			datenewDob = mhToModify.getDOB();
		}
		else {
			datenewDob = Date.valueOf(LocalDate.parse(newDob, formatter));
		}
		System.out.println("Actual diseases: "+ mhToModify.getDiseases());
		System.out.println("Type the new disease of the patient or press enter to leave it as it is :");
		String newdisease= reader.readLine();
		if(newdisease.equals("")) {
			newdisease = mhToModify.getDiseases();
		}
		System.out.println("Actual allergies: "+ mhToModify.getAllergies());
		System.out.println("Type the new allergie of the patient or press enter to leave it as it is :");
		String newallergie= reader.readLine();
		if(newallergie.equals("")) {
			newallergie = mhToModify.getAllergies();
		}
		System.out.println("Actual surgeries: "+ mhToModify.getSurgeries());
		System.out.println("Type the new surgerie of the patient or press enter to leave it as it is :");
		String newsurgerie= reader.readLine();
		if(newsurgerie.equals("")) {
			newsurgerie = mhToModify.getAllergies();
		}
		System.out.println("Actual weight: " + mhToModify.getWeightKg());
		System.out.println("Type the new weight of the patient or press enter to leave it as it is :");
		String newWeight = reader.readLine();
		Float floatnewWeight;
		if(newWeight.equals("")) {
			floatnewWeight = mhToModify.getWeightKg();
		}
		else {
			floatnewWeight = Float.parseFloat(newWeight);
		}
		System.out.println("Actual height: " + mhToModify.getHeightCm());
		System.out.println("Type the new height of the patient or press enter to leave it as it is :");
		String newHeight = reader.readLine();
		Integer intnewHeight;
		if(newHeight.equals("")) {
			intnewHeight = mhToModify.getHeightCm();
		}
		else {
			intnewHeight = Integer.parseInt(newHeight);
		}
		
		int id = mhToModify.getID();
		MedicalHistory updatedMedicalHistory = new MedicalHistory(id,newName,datenewDob,newdisease,newallergie,
				newsurgerie,floatnewWeight,intnewHeight);
		dm.modifyMH(updatedMedicalHistory);
		System.out.println("The medical history of your patient has been successfully modified");
		
}
 
 
 
 private static void ShowMedicalHistory(Integer patID){
	 MedicalHistory mh= new MedicalHistory();
	 	Patient p = pm.getPatient(patID);
	 	mh= pm.getMedicalHistory(p);
		System.out.println("This is the actual medical history of your patient:");
			System.out.println(mh);
			
		}
		
private static void createTreatment(Integer patID) throws IOException {
	db= new SQLiteManager();
	db.connect();
	dm = db.getDoctorManager();
	ListTreatments(patID);
	System.out.println("For a new treatment:");
	System.out.println("Type of the new treatment: ");
	String type = reader.readLine();
	System.out.println("Length of the new treatment: ");
	Integer length = Integer.parseInt(reader.readLine());
	Treatment newTreatment = new Treatment(type,length);
	dm.createTreatment(newTreatment);
	System.out.println("You have created a new treatment succesfully!");
}
		

	private static void doctorAppointmentSubMenu(Integer docID) throws Exception {
		while(true){
			System.out.println("Select one of this options");
			System.out.println("1.Read appointments");
			System.out.println("2.Add appointment");
			System.out.println("3.Modify appointment");
			System.out.println("4.Delete appointment");
			System.out.println("0.Return");
			int option = Integer.parseInt(reader.readLine());
			switch(option){
			//NOTA GENERAL: al hacer algo con appointment el medico solo puede trabajar con sus pacientes actuales
			//el paciente será el que añada al medico
			case 1:
				List<Patient>currentPatients = dm.getDoctorsPatients(docID); 
				System.out.println("This are your current patients");
				for(int i=0; i<currentPatients.size();i++){
					Patient patient = currentPatients.get(i);
					System.out.println(patient.toString());
				}
				System.out.println("APPOINTMENTS");
				am.readAppointments(docID);
				break;
			case 2:
				List<Patient>currentPatients2 = dm.getDoctorsPatients(docID);
				System.out.println("This are your current patients");
				for(int i=0; i<currentPatients2.size(); i++){
					Patient patient = currentPatients2.get(i);
					System.out.println(patient.toString());
				}
				System.out.println("---------------------------");
				System.out.println("CURRENT APPOINTMENTS");
				am.readAppointments(docID);
				System.out.println("Choose a patients ID for the new appointment");
				Integer patId = Integer.parseInt(reader.readLine());
				Patient patient = pm.getPatient(patId);
				PhysicalTherapist physicalTherapist = patient.getPhysicalTerapist();
				Integer pTId = physicalTherapist.getId(); //se puede?
				Appointment appointment = introduceDateAndTime();
				am.addAppointment(appointment, patId, pTId, docID);
				break;
			case 3:
				List<Patient>currentPatients3 = dm.getDoctorsPatients(docID);
				System.out.println("CURRENT APPOINTMENTS");
				am.readAppointments(currentPatients3);
				System.out.println("Select the ID of the appointment you want to modify");
				Integer idAp = Integer.parseInt(reader.readLine());

				break;
			case 4:
				List<Patient>currentPatients4 = dm.getDoctorsPatients(docID);
				//TODO
				break;
			case 0:
				return;
			}

		}//FALTAN POR HACER VALIDATE INFO POR SI METE UNA DATE QUE YA ESTA COGIDA O UN ID QUE NO ESTÁ

	}




	private static void ModifyTreatment(Integer patID) throws NumberFormatException, IOException {
	    ListTreatments(patID);
		System.out.println("-------------------------------------------------------");
		System.out.println("Please, input the ID of the treatment you want to modify :");
		int treatID;
        treatID = Integer.parseInt(reader.readLine());
		Treatment treatmentToModify= dm.getTreatment(treatID);
		System.out.println("Actual Type: "+treatmentToModify.getType());
		//If the user does not type anything the type of treatment  won´t change 
		System.out.println("Type the new type of treatment or press enter to leave it as it is :");
		String newType= reader.readLine();
		if(newType.equals("")) {
			newType=treatmentToModify.getType();
		}
		System.out.println("Actual Lenght: "+treatmentToModify.getLenght());
		//If the user does not type anything the type of treatment  won´t change 
		System.out.println("Type the new lenght for the treatment or press enter to leave it as it is :");
		String newLength= reader.readLine();
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
 private static void ListTreatments(Integer patID) {
	 List<Treatment>treatmentList= new ArrayList<Treatment>();
		treatmentList= dm.listTreatments(patID);
		System.out.println("This are the actual treatments of your patient:");
		for (Treatment treatment:treatmentList) {
			System.out.println(treatment);
		}
 }


	private static Appointment introduceDateAndTime() throws Exception{
		System.out.println("Introduce a date: yyyy-mm-dd");
		String dateString = reader.readLine();
		Date appointmentDate = Date.valueOf(LocalDate.parse(dateString, formatter));
		System.out.println("Introduce the time: HH:mm");
		String timeString = reader.readLine();
		Time appointmentTime = java.sql.Time.valueOf(timeString);
		Appointment appointment = new Appointment(appointmentDate, appointmentTime);
		return appointment;
		}
 
 
 
 
private static void DeleteTreatment(Integer patID) throws NumberFormatException, IOException {
	ListTreatments(patID);
	System.out.println("-------------------------------------------------------");
	System.out.println("Please, input the ID of the treatment you want to delete :");
	int treatID;
    treatID = Integer.parseInt(reader.readLine());
	Treatment treatmentToDelete= dm.getTreatment(treatID);
	dm.deleteTreatment(treatmentToDelete);
}
private static void ReadTreatment(Integer patID) throws NumberFormatException, IOException {
	ListTreatments(patID);
	System.out.println("-------------------------------------------------------");
	System.out.println("Please, input the ID of the treatment you want to read :");
	int treatID;
    treatID = Integer.parseInt(reader.readLine());
	Treatment treatmentToRead= dm.getTreatment(treatID);
	dm.readTreatment(treatmentToRead);
}
 
}
 
