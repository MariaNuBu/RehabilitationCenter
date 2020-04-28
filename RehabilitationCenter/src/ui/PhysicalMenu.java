package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import db.interfaces.DBManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;
import db.sqlite.SQLiteManager;
import pojos.*;

public class PhysicalMenu
{
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	private static DBManager db;
	private static PatientManager pm;
	private static PhysicalTherapistManager ptm;
	
	public void readTreatment(String userName, PhysicalTherapistManager ptm, PatientManager pm) throws IOException
	{
		Integer ptID = ptm.searchPTByEmail(userName);
		System.out.println("These are your patients");
		ArrayList<Patient> patients = new ArrayList<Patient>();
		patients=ptm.getAllPatients(ptID);
		if(patients==null)
		{
			System.out.println("You don't have patients alredy");
		}
		else
		{
			for (Patient patient : patients) 
			{	
				System.out.println(patient);
			}
			System.out.println("Type the ID of the patient you want to check the treatment");
			Integer patientID = Integer.parseInt(reader.readLine());
			Patient patientChoosen = pm.getPatient(patientID);
			ArrayList<Treatment> treatments = new ArrayList <Treatment>();
			treatments=pm.listTreatment(patientChoosen);
			if (treatments==null)
			{
				System.out.println("This patient doesn't have any treatments");
			}
			else
			{
				for (Treatment treatment : treatments) 
				{
					System.out.println(treatment);
				}
			}
		}
		
	}
	public void checkAppointments(String userName, PhysicalTherapistManager ptm) throws IOException
	{
		
	}

}
