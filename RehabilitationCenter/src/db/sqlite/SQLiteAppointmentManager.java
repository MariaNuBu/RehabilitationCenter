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
	//To add an appointment from a patient obtained by an XML
	@Override
	public void addAppointmentFromXML(Appointment appointment, Integer patID) {
		try{

			String sql = "INSERT INTO appointment (date,time,PATID, DOCID, PTID)"
						+"VALUES (?,?,?,?,?)";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setDate(1, appointment.getDate());
			prepS.setTime(2, appointment.getTime());
			prepS.setInt(3, patID);
			prepS.setInt(4, appointment.getDoc().getId());
			prepS.setInt(5, appointment.getPt().getId());
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

			String sql = "SELECT *FROM appointment WHERE PTID=?";
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

	@Override
	public List<Appointment> getPhysicalTherapistAppointments(Integer ptID,PatientManager pm, PhysicalTherapistManager ptm,DoctorManager dm) {
		List <Appointment> appointments = new ArrayList<Appointment>();
		try
		{
			String sql = "SELECT * FROM appointment WHERE PTID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, ptID);
			ResultSet rs = prepS.executeQuery();
			while(rs.next())
			{
				int apID = rs.getInt(1);
				Date apDate = rs.getDate(2);
				Time apTime = rs.getTime(3);
				int patID = rs.getInt(4);
				int docID = rs.getInt(5);
				int ptid = rs.getInt(6);
				Patient p = new Patient();
				p = pm.getPatient(patID);
				PhysicalTherapist pt = new PhysicalTherapist();
				pt = p.getPhysicalTerapist();
				Doctor d = new Doctor();
				d = dm.getDoctor(docID);
				Appointment appointmentToRead = new Appointment(apID, apDate, apTime,p,d,pt);
				appointments.add(appointmentToRead);
			}
			prepS.close();
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return appointments;
	}

	@Override
	public List<Appointment> getPatientsAppointments(Integer patId, PatientManager pm, PhysicalTherapistManager ptm,DoctorManager dm)
	{
		List <Appointment> appointments = new ArrayList<Appointment>();
		try
		{
			String sql = "SELECT * FROM appointment WHERE PATID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, patId);
			ResultSet rs = prepS.executeQuery();
			while(rs.next())
			{
				int apID = rs.getInt(1);
				Date apDate = rs.getDate(2);
				Time apTime = rs.getTime(3);
				int patID = rs.getInt(4);
				int docID = rs.getInt(5);
				int ptID = rs.getInt(6);Patient p = new Patient();
				p = pm.getPatient(patId);
				PhysicalTherapist pt = new PhysicalTherapist();
				pt = p.getPhysicalTerapist();
				Doctor d = new Doctor();
				d = dm.getDoctor(docID);
				Appointment appointmentToRead = new Appointment(apID, apDate, apTime,p,d,pt);
				appointments.add(appointmentToRead);
			}
			prepS.close();
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return appointments;
	}




	@Override
	public List<Appointment> getDoctorsAppointments(Integer docID,PatientManager pm, PhysicalTherapistManager ptm,DoctorManager dm) {
		List <Appointment> appointments = new ArrayList<Appointment>();
		try
		{
			String sql = "SELECT * FROM appointment WHERE DOCID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, docID);
			ResultSet rs = prepS.executeQuery();
			while(rs.next())
			{
				int apID = rs.getInt(1);
				Date apDate = rs.getDate(2);
				Time apTime = rs.getTime(3);
				int patID = rs.getInt(4);
				int docid = rs.getInt(5);
				int ptID = rs.getInt(6);
				Patient p = new Patient();
				p = pm.getPatient(patID);
				PhysicalTherapist pt = new PhysicalTherapist();
				pt = p.getPhysicalTerapist();
				Doctor d = new Doctor();
				d = dm.getDoctor(docID);
				Appointment appointmentToRead = new Appointment(apID, apDate, apTime,p,d,pt);
				appointments.add(appointmentToRead);
			}
			prepS.close();
			rs.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return appointments;
	}







}

