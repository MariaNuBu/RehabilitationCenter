package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.AppointmentManager;
import pojos.*;


public class SQLiteAppointmentManager implements AppointmentManager {

	private Connection c;

	public SQLiteAppointmentManager(Connection c){
		this.c = c;
	}


	@Override
	public void addAppointment(Appointment appointment, Integer patId, Integer pTId, Integer docId) {
		try{

			String sql = "INSERT INTO appointment (date, time, PATID, DOCID, PTID)"
						+"VALUES (?,?,?,?,?)";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setDate(1, appointment.getDate());
			prepS.setTime(2, appointment.getTime());
			prepS.setInt(3, patId);
			prepS.setInt(4, docId);
			prepS.setInt(5, pTId);
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
	public void readAppointments(Integer specialistID) {
		try{

			String sql = "SELECT * FROM appointment WHERE DOCID LIKE ?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, specialistID);
			ResultSet rs = prepS.executeQuery();

			while(rs.next()){
				int apID = rs.getInt(1);
				Date apDate = rs.getDate(2);
				Time apTime = rs.getTime(3);
				int patID = rs.getInt(4);
				int docID = rs.getInt(5);
				int ptID = rs.getInt(6);
				Appointment appointmentToRead = new Appointment(apID, apDate, apTime, patID, docID, ptID);
				System.out.println(appointmentToRead.toString());
			}

		}catch(Exception e){
			e.printStackTrace();
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

