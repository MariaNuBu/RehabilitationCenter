package db.interfaces;

import pojos.*;
import java.util.*;



public interface AppointmentManager {

	public void addAppointment(Appointment appointment, Patient patient, Doctor doctot, PhysicalTherapist physicalTherapist);
	public void modifyAppointment(Appointment appointment);
	public void deleteAppointment(Appointment appointment);
	public void readAppointments(Integer docId,PatientManager pm,DoctorManager dm); //para que un medico o pT vea todas sus consultas
	public Appointment getAppointment(Integer appointmentID);
	public LinkedList<ArrayList<Appointment>> checkCurrentAppointments(Integer docID, Integer pacID);
	//public List<Appointment> searchDate (Date date);//para buscar appointments por fecha
	public void deleteAppointmentDoctor(Integer ID);
	public void deleteAppointmentPhysicalTherapist(Integer ID);
}
