package db.interfaces;

import pojos.*;
import java.util.*;



public interface AppointmentManager {

	public void addAppointment(Appointment appointment, Patient patient, Doctor doctor, PhysicalTherapist physicalTherapist);
	public void addAppointmentFromXML(Appointment appointment, Integer patId);
	public void modifyAppointment(Appointment appointment);
	public void deleteAppointment(Appointment appointment);
	public void readAppointments(Integer docId,PatientManager pm,DoctorManager dm); //para que un medico o pT vea todas sus consultas
	public Appointment getAppointment(Integer appointmentID);
	public LinkedList<ArrayList<Appointment>> checkCurrentAppointments(Integer docID, Integer patID);
	//public List<Appointment> searchDate (Date date);//para buscar appointments por fecha
	public void readPTAppointments(Integer ptID, PatientManager pm, PhysicalTherapistManager ptm, DoctorManager dm);
	public List<Appointment> getPatientsAppointments(Integer patID,PatientManager pm, PhysicalTherapistManager ptm, DoctorManager dm);
	public List<Appointment> getPhysicalTherapistAppointments(Integer ptID,PatientManager pm, PhysicalTherapistManager ptm,DoctorManager dm);
	public List<Appointment> getDoctorsAppointments(Integer docID,PatientManager pm, PhysicalTherapistManager ptm,DoctorManager dm);

}
