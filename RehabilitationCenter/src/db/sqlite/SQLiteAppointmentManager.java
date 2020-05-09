package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.*;

import db.interfaces.AppointmentManager;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
import db.interfaces.PhysicalTherapistManager;
import pojos.*;


public class SQLiteAppointmentManager implements AppointmentManager {

	private Connection c;

	public SQLiteAppointmentManager(Connection c){
		this.c = c;
	}


	@Override
	public void addAppointment(Appointment appointment, Patient patient, Doctor doctor, PhysicalTherapist physicalTherapist) {
		try{

			String sql = "INSERT INTO appointment (date, time, PATID, DOCID, PTID)"
						+"VALUES (?,?,?,?,?)";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setDate(1, appointment.getDate());
			prepS.setTime(2, appointment.getTime());
			prepS.setInt(3, patient.getId());
			prepS.setInt(4, doctor.getId());
			prepS.setInt(5, physicalTherapist.getId());
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
	//TODO no funciona el delete
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
	public void readAppointments(Integer docId,PatientManager pm,DoctorManager dm) {
		try{

			String sql = "SELECT * FROM appointment WHERE DOCID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, docId);
			ResultSet rs = prepS.executeQuery();

			while(rs.next()){
				int apID = rs.getInt(1);
				Date apDate = rs.getDate(2);
				Time apTime = rs.getTime(3);
				int patID = rs.getInt(4);
				int docID = rs.getInt(5);
				int ptID = rs.getInt(6);
				Patient p = new Patient();
				p = pm.getPatient(patID);
				PhysicalTherapist pt = new PhysicalTherapist();
				pt = p.getPhysicalTerapist();
				Doctor d = new Doctor();
				d = dm.getDoctor(docId);
				Appointment appointmentToRead = new Appointment(apID, apDate, apTime,p,d,pt);
				System.out.println(appointmentToRead.toString()+"PatientsId: "+patID);
			}
			prepS.close();
		}catch(Exception e){
			e.printStackTrace();
		}

	}


	@Override
	public Appointment getAppointment(Integer appointmentID) {
		Appointment appointmentToModify = null;
		try{

			String sql = "SELECT * FROM appointment WHERE ID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, appointmentID);
			ResultSet rs = prepS.executeQuery();
			boolean dateExists = false;
			while(rs.next()){
				if(!dateExists){
					Integer dateID = rs.getInt("ID");
					Date date = rs.getDate("Date");
					Time time = rs.getTime("Time");
					appointmentToModify = new Appointment(dateID, date, time);
				}
			}

			prepS.close();

		}catch(Exception e){
			e.printStackTrace();
		}
		return appointmentToModify;
	}


	@Override
	public LinkedList<ArrayList<Appointment>> checkCurrentAppointments(Integer docID, Integer patID) {
		LinkedList<ArrayList<Appointment>> docAndPatAppointments = new LinkedList<ArrayList<Appointment>>();
		ArrayList<Appointment>docAppointments = new ArrayList<Appointment>();
		ArrayList<Appointment>patAppointments = new ArrayList<Appointment>();
		try{
			String sql = "SELECT * FROM appointment WHERE DOCID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, docID);
			ResultSet rs = prepS.executeQuery();
			while(rs.next()){
				Date apDate = rs.getDate("Date");
				Time apTime = rs.getTime("Time");
				Appointment ap = new Appointment (apDate, apTime);
				docAppointments.add(ap);
			}
			docAndPatAppointments.add(docAppointments);
			String sql2 = "SELECT * FROM appointment WHERE PATID=?";
			PreparedStatement prepS2 = c.prepareStatement(sql2);
			prepS2.setInt(1, patID);
			ResultSet rs2 = prepS2.executeQuery();
			while(rs.next()){
				Date apDate = rs2.getDate("Date");
				Time apTime = rs2.getTime("Time");
				Appointment ap = new Appointment(apDate, apTime);
				patAppointments.add(ap);
			}
			docAndPatAppointments.add(patAppointments);
		}catch(Exception e){
			e.printStackTrace();
		}
		return docAndPatAppointments;
	}

	@Override
	public void deleteAppointmentDoctor(Integer ID)
	{
		try
		{
			String sql = "DELETE FROM appointment WHERE DOCID=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1,ID);
			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void deleteAppointmentPhysicalTherapist(Integer ID)
	{
		try
		{
			String sql = "DELETE FROM appointment WHERE PTID=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1,ID);
			ps.executeUpdate();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void readPTAppointments(Integer ptId, PatientManager pm, PhysicalTherapistManager ptm, DoctorManager dm) {
		try{

			String sql = "SELECT *FROM APPOINTMENT WHERE PTID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, ptId);
			ResultSet rs = prepS.executeQuery();

			while(rs.next()){
				int apID = rs.getInt(1);
				Date apDate = rs.getDate(2);
				Time apTime = rs.getTime(3);
				int patID = rs.getInt(4);
				int docID = rs.getInt(5);
				int ptID = rs.getInt(6);
				Patient p = new Patient();
				p = pm.getPatient(patID);
				PhysicalTherapist pt = new PhysicalTherapist();
				pt = ptm.getPhysicalTherapist(ptId);
				Doctor d = new Doctor();
				d = dm.getDoctor(docID);
				Appointment appointmentToRead = new Appointment(apID, apDate, apTime,p,d,pt);
				System.out.println(appointmentToRead+"  PATIENT-->" + p);

			}
			prepS.close();
		}catch(SQLException e){
			e.printStackTrace();
		}

	}







}

