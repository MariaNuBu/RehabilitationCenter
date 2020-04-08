package db.interfaces;

import java.util.ArrayList;
import java.util.List;

import pojos.*;

public interface PatientManager {
	
	public ArrayList<Patient> searchPatientName (String patientName);
	public void addPatientandMedicalHistory (Patient p,PhysicalTherapist pt,MedicalHistory mh);
	public ArrayList<Treatment> listTreatment(Patient patient);
	public Patient getPatient(Integer id);
	public int getLastId();
	public MedicalHistory getMedicalHistory(Patient p);
	
}
