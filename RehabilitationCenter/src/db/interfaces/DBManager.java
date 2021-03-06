package db.interfaces;

public interface DBManager {
	
	public void connect();
	public void disconnect();
	public void createTables();
	public PatientManager getPatientManager(); 
	public DoctorManager getDoctorManager();
	public PhysicalTherapistManager getPhysicalTherapistManager();
	public AppointmentManager getAppointmentManager();
}
