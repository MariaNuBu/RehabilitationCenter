package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import db.interfaces.*;
import pojos.*;


public class DoctorMenu {
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static DBManager db;
	private static DoctorManager dm;
	private static PatientManager pm;
	private static AppointmentManager am;

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
			case 1:
				List<Patient>doctorsPatients = dm.getDoctorsPatients(docID);
				am.readAppointments(doctorsPatients);
				break;
			case 2:
				List<Patient>doctorsPatients2 = dm.getDoctorsPatients(docID);
				//TODO
				break;
			case 3:
				List<Patient>doctorsPatients3 = dm.getDoctorsPatients(docID);
				//TODO
				break;
			case 4:
				List<Patient>doctorsPatients4 = dm.getDoctorsPatients(docID);
				//TODO
				break;
			case 0:
				return;
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

