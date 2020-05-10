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
	public void changePhysicalTherapist(Patient patient,Integer newptID);
	public Integer searchPatientByEmail(String email);
	public boolean checkDoctor(Integer patID, Integer docID);
	public void addDoctorToPatient(Patient pat, Doctor doc);

}
