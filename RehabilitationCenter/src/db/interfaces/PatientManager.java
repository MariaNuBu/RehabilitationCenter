package db.interfaces;

import java.util.List;

import pojos.*;

public interface PatientManager {
	
	public List<Patient> searchPatientName (String patientName);
	public void addPatientandMedicalHistory (Patient p,PhysicalTherapist pt,MedicalHistory mh);
	public List<Treatment> listTreatment(Patient patient);
	public Patient getPatient(Integer id);
	
}
