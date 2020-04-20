package db.interfaces;
import java.util.List;

import pojos.*;


public interface DoctorManager {

	public void readMH  (Integer ID);
	public void modifyMH(MedicalHistory m);
	public void createTreatment (Treatment t);
	public Treatment getTreatment( Integer treatID);
	public void modifyTreatment (Treatment t);
	public void deleteTreatment (Treatment t);
	public List<Treatment> listTreatments (Integer IDPat);
	public Treatment readTreatment (Treatment t);
	public List <Patient> SearchByName(String name);
	public List <Doctor> searchDoctorByName(String name);
	public List<Patient> getDoctorsPatients(Integer docID); //coge todos los pacientes de un medico

}
