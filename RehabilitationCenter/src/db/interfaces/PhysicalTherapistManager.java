package db.interfaces;
import java.util.ArrayList;
import java.util.List;

import pojos.*;

public interface PhysicalTherapistManager {

	
	public Treatment readTreatment (Treatment t);
	public void insertPhysicalTherapist(PhysicalTherapist pt);
	public ArrayList<PhysicalTherapist> showPhisicalTherapists(String sport);
	public PhysicalTherapist getPhysicalTherapist(Integer id);
}
