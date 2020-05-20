package db.interfaces;
import java.util.ArrayList;
import java.util.List;

import pojos.*;

public interface PhysicalTherapistManager {


	public Treatment readTreatment (Treatment t);
	public void insertPhysicalTherapist(PhysicalTherapist pt);
	public List<PhysicalTherapist> showPhysicalTherapists(String sport);
	public PhysicalTherapist getPhysicalTherapist(Integer id);
	public List<PhysicalTherapist> showAllPhysicalTherapists();
	public List<Patient> getAllPatients(Integer ptid);
	public void addPhysicalTherapist (PhysicalTherapist pt);
	public Integer searchPTByEmail (String email);
	public List <PhysicalTherapist> listPhysicalTherapists();
	public void deletePhysicalTherapist(Integer ID);
}
