package db.interfaces;

public interface DBManager {
	
	public void connect(String connection);
	public void disconnect();
	public void createTables();
	public PatientManager getPatient(); 
	public DoctorManager getDoctor();
	public PhysicalTherapistManager getPhysicalTherapist();
	
}
