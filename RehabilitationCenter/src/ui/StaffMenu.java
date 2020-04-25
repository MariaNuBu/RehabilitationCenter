package ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StaffMenu 
{
	private static BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));

	public static void staffMenu() throws IOException
	{
		System.out.println("Choose the role you want to register: \n1.-Patient\n2.-Doctor\n3.-Physical Therapist");
		Integer role = Integer.parseInt(reader.readLine());
		switch (role)
		{
			case 1:
				PatientMenu patientMenu = new PatientMenu();
				patientMenu.registerPatient();
				break;
			case 2:
				DoctorMenu doctorMenu = new DoctorMenu();
				doctorMenu.registerDoctor();
				break;
			case 3:
				PhysicalMenu physicalMenu = new PhysicalMenu();
				physicalMenu.registerPhysicalTherapist();
				break;
		}
		
	}
	
}
