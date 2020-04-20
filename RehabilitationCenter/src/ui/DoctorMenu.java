package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.ParseConversionEvent;

import db.interfaces.DBManager;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
import pojos.MedicalHistory;
import pojos.Patient;
import pojos.Treatment;
public class DoctorMenu {
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DBManager db;
	private static DoctorManager dm;
	private static PatientManager pm;
	public  void doctorMenu() throws Exception {
		
		System.out.println("Please introduce the name of the patient you want to work with");
		String name = reader.readLine();
		List<Patient> patients = dm.SearchByName(name);
		for (Patient patient : patients) {
			System.out.println(patient);
		}
		System.out.println("Please introduce the id of the patient you want to work with ");
		int ptId=Integer.parseInt(reader.readLine());
		doctorSubMenu(ptId);
	}
	
	
	
	
 private static void doctorSubMenu(Integer patID )throws Exception{
	 while (true) {
			System.out.println("Hello doctor , what do you want to do ?");
			System.out.println("1. Read the Medical History of your patient");
			System.out.println("2. Modify the Medical History of your patient");
			System.out.println("3. Create a new treatment for your patient ");
			System.out.println("4. Modify one of the treatments of your patient");
			System.out.println("5. Delete one of the treatments of your patient");
			System.out.println("6. List all of the treatments of your patient");
			System.out.println("7. Read one of the treatments of your patient");
			System.out.println("0. Back");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				//TODO
				break;
			case 2:
				ModifyMedicalHistory(patID);
				break;
			case 3:
				//TODO
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
		String newType = reader.readLine();
		if(newType.equals("")) {
			newType=treatmentToModify.getType();
		}
		System.out.println("Actual Lenght: "+treatmentToModify.getLenght());
		//If the user does not type anything the type of treatment  won´t change 
		System.out.println("Type the new lenght for the treatment or press enter to leave it as it is :");
		String newLength = reader.readLine();
		int intnewLength;
		if(newLength.equals("")) {
			intnewLength = treatmentToModify.getLenght();
		}else {
			intnewLength = Integer.parseInt(newLength);
		}
		int id = treatmentToModify.getId();
		Treatment updatedTreatment = new Treatment(id,newType,intnewLength);
		dm.modifyTreatment(updatedTreatment);
		System.out.println("The treatment has been successfully modified");
	 }
 
 
 
 
 private static void ListTreatments(Integer patID) {
	 List<Treatment>treatmentList = new ArrayList<Treatment>();
		treatmentList = dm.listTreatments(patID);
		System.out.println("This are the actual treatments of your patient:");
		for (Treatment treatment:treatmentList) {
			System.out.println(treatment);
		}
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
 
