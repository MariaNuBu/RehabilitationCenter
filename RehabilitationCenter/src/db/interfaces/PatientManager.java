package db.interfaces;

import java.util.List;

import pojos.*;

public interface PatientManager {
	
	public List<Patient> searchPatientName (String patientName);
	public void modifyAppointment (Appointment a);
	
}
