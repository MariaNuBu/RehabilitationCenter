package db.interfaces;
import java.util.List;

import pojos.*;


public interface DoctorManager {

	public void readMH  (Integer ID);
	public void modifyMH(Integer ID);
	public void createTreatment (Treatment t);
	public Treatment getTreatment(Patient p);
	public void modifyTreatment (Treatment t);
	public void deleteTreatment (Treatment t);
	public List<Treatment> listTreatments (Integer IDPat);
	public Treatment readTreatment (Treatment t);
	
}
