package db.interfaces;
import java.util.List;

import pojos.*;


public interface DoctorManager {

	public void readMH  (Integer ID);
	public void modifyMH(MedicalHistory m);
	public void createTreatment (Treatment t);
	public void modifyTreatment (Treatment t);
	public void deleteTreatment (Integer IDPat,Integer IDTreat);
	public List<Treatment> listTreatments (Integer IDPat);
	public void readTreatment (Treatment t);
	
}
