package db.interfaces;

import pojos.*;

public interface PatientManager {
	
	public Patient searchPatient (Integer ID);
	public void modifyAppointment (Appointment a);
	
}
