package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.DBManager;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
import pojos.Patient;
import pojos.Treatment;
public class DoctorMenu {
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	private static DoctorManager dm;
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
			System.out.println("4.Modify one of the treatments of your patient");
			System.out.println("5.Delete one of the treatments of your patient");
			System.out.println("6.List all of the treatments of your patient");
			System.out.println("7.Read one of the treatments of your patient");
			System.out.println("0. Back");
			int choice = Integer.parseInt(reader.readLine());
			switch (choice) {
			case 1:
				//TODO 
				break;
			case 2:
				//TODO
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
 private static void ModifyTreatment(Integer patID) throws NumberFormatException, IOException {
	    ListTreatments(patID);
		System.out.println("-------------------------------------------------------");
		System.out.println("Please, input the ID of the treatment you want to modify :");
		int treatID;
        treatID = Integer.parseInt(reader.readLine());
		Treatment treatmentToModify= dm.getTreatment(treatID);
		System.out.println("Actual Type: "+treatmentToModify.getType());
		//If the user does not type anything the type of treatment  won�t change 
		System.out.println("Type the new type of treatment or press enter to leave it as it is :");
		String newType= reader.readLine();
		if(newType.equals("")) {
			newType=treatmentToModify.getType();
		}
		System.out.println("Actual Lenght: "+treatmentToModify.getLenght());
		//If the user does not type anything the type of treatment  won�t change 
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
 
