package db.interfaces;
import java.util.ArrayList;
import java.util.List;

import pojos.*;


public interface DoctorManager {

	public void readMH  (Integer ID);
	public void modifyMH(MedicalHistory m);
	public void createTreatment (Treatment t,Integer patientID,Integer DOCID,PatientManager pm);
	public Treatment getTreatment( Integer treatID);
	public void modifyTreatment (Treatment t);
	public void deleteTreatment (Treatment t,Integer patID);
	public List<Treatment> listTreatments (Integer IDPat);
	public Treatment readTreatment (Treatment t);
	public ArrayList <Patient> SearchByName(String name,Integer docID);
	public List <Doctor> searchDoctorByName(String name);
	public List<Patient> getDoctorsPatients(Integer docID); //coge todos los pacientes de un medico
	public Doctor getDoctor(int docId);
	public int getLastId();
	public void createDoctor (Doctor doc);
	public Integer searchDoctorByEmail(String email);
}
