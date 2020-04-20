package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;
import db.sqlite.SQLiteManager;
import pojos.*;

public class PhysicalMenu
{
	BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;
	
	public void readTreatment() throws IOException
	{
		db= new SQLiteManager();
		db.connect();
		pm = db.getPatientManager();
		ptm=db.getPhysicalTherapistManager();
		System.out.println("These are your patients");
		//TODO hay que cambiar el 1 por el id del physical
		ArrayList<Patient> patients = new ArrayList<Patient>();
		patients=ptm.getAllPatients(1);
		for (Patient patient : patients) 
		{	
			System.out.println(patient);
		}
		System.out.println("Type the ID of the patient you want to check the treatment");
		Integer patientID = Integer.parseInt(reader.readLine());
		Patient patientChoosen = pm.getPatient(patientID);
		ArrayList<Treatment> treatments = new ArrayList <Treatment>();
		treatments=pm.listTreatment(patientChoosen);
		for (Treatment treatment : treatments) 
		{
			System.out.println(treatment);
		}
	}

}
