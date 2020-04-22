package db.interfaces;

import java.sql.Date;
import java.util.List;
import pojos.*;



public interface AppointmentManager {

	public void addAppointment(Appointment appointment, Integer patId, Integer pTId,Integer docId);
	public void modifyAppointment(Appointment appointment);
	public void deleteAppointment(Appointment appointment);
	public void readAppointments(Integer specialistID); //para que un medico o pT vea todas sus consultas
	public List<Appointment> searchDate (Date date);//para buscar appointments por fecha
	public List<Patient> searchAppointmentByPatient(String name); //buscar appointments por paciente, no es seguro que se necesite esto

}
