package db.interfaces;
import java.util.ArrayList;
import java.util.List;

import pojos.*;

public interface PhysicalTherapistManager {

	
	public void readTreatment (Integer ID);
	public void insert(PhysicalTherapist pt);
	public ArrayList<PhysicalTherapist> showPhisicalTherapists(String sport);
}
