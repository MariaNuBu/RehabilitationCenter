package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.AppointmentManager;
import pojos.Appointment;
import pojos.Doctor;
import pojos.Patient;
import pojos.PhysicalTherapist;

public class SQLiteAppointmentManager implements AppointmentManager {

	private Connection c;

	public SQLiteAppointmentManager(Connection c){
		this.c = c;
	}


	@Override
	public void addAppointment(Appointment appointment, Patient patient, Doctor doc, PhysicalTherapist pT) {
		try{

			String sql = "INSERT INTO appointment (ID, date, time, PATID, DOCID, PTID)"
						+"VALUES (?,?,?,?,?,?)";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, appointment.getId());
			prepS.setDate(2, appointment.getDate());
			prepS.setTime(3, appointment.getTime());
			prepS.setInt(4, patient.getId());
			prepS.setInt(5, doc.getId());
			prepS.setInt(6, pT.getId());
			prepS.executeUpdate();
			prepS.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void modifyAppointment(Appointment appointment) {
		try{

			String sql = "UPDATE appointment SET date=?, time=? WHERE ID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setDate(1, appointment.getDate());
			prepS.setTime(2, appointment.getTime());
			prepS.setInt(3, appointment.getId());
			prepS.executeUpdate();
			prepS.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void deleteAppointment(Appointment appointment) {
		try{

			String sql = "DELETE FROM appointment WHERE ID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, appointment.getId());
			prepS.executeUpdate();
			prepS.close();

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	@Override
	public void readAppointments(List<Patient>patients) {
		
		for(int i=0; i<patients.size(); i++){
			Integer id  = patients.get(i).getId();
			String name = patients.get(i).getName();
			String sport = patients.get(i).getSport();
			String disability = patients.get(i).getDisability();
			List<Appointment> appointmentsToRead = patients.get(i).getAppointments();
			System.out.println(id+" "+name+" "+sport+" "+disability+"APPOINTMENTS-->"+appointmentsToRead);
			
		}

	}

	@Override
	public List<Appointment> searchDate(Date date) {
		List<Appointment>dates = new ArrayList<Appointment>();
		try{

			String sql = "SELECT * FROM appointment WHERE date LIKE ?";
			//que muestre solo los appointments del medico o pt en concreto
			//constructor de appointment con todo+DocID y pTID??
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setDate(1, date);
			ResultSet rs = prepS.executeQuery();

			while(rs.next()){
				int id = rs.getInt("ID");
				Date apDate = rs.getDate("date");
				Time time   = rs.getTime("time");
				Appointment appointment = new Appointment(id,apDate,time);
				dates.add(appointment);
			}

		}catch(Exception e){
			e.printStackTrace();
		}

		return dates;
	}

	@Override
	public List<Patient> searchAppointmentByPatient(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
