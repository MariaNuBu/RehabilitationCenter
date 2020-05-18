package ui;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.DBManager;
import db.interfaces.*;
import db.sqlite.SQLiteManager;
import pojos.*;

public class PhysicalMenu
{
	private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		
	public void readTreatment(String username, PhysicalTherapistManager ptm, PatientManager pm) throws IOException
	{
		Integer ptID = ptm.searchPTByEmail(username);

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
			Integer patientID = DataObtention.readInt("Type the ID of the patient you want to check the treatment");
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
	
	public void checkAppointments(String username, PhysicalTherapistManager ptm, AppointmentManager am, PatientManager pm, DoctorManager dm) throws IOException
	{
		Integer ptID = ptm.searchPTByEmail(username);
				System.out.println("----CURRENT APPOINTMENTS-------");
				am.readPTAppointments(ptID, pm, ptm, dm);
	}

}

