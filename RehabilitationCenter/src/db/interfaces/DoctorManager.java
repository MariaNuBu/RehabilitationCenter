package db.interfaces;
import java.util.ArrayList;
import java.util.List;

import pojos.*;


public interface DoctorManager 
{
	public void createDoctor (Doctor doc);
	public Integer searchDoctorByEmail(String email);
	public void readMH  (Integer ID);
	public void modifyMH(MedicalHistory m);
	public int getLastId();
	public void createTreatment (Treatment t,Integer patientID,Integer DOCID,PatientManager pm);
	public void modifyTreatment (Treatment t);
	public void deleteTreatment(Treatment t,Integer patID); 
	public List<Treatment> listTreatments (Integer IDPat);
	public Treatment readTreatment (Treatment t);
	public Treatment getTreatment( Integer treatID);
	public List <Patient> SearchByName(String name,Integer docID);
	public List <Doctor> searchDoctorByName(String name);
	public List<Patient> getDoctorsPatients(Integer docID); //get all the patients of a doctor
	public Doctor getDoctor(int docId);	
	public List<Doctor> listDoctors();
	public void deleteDoctor(Integer ID);
}
