package db.sqlite;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import db.interfaces.PatientManager;
import pojos.*;


public class SQLitePatientManager implements PatientManager {

	private Connection c;
	
	public SQLitePatientManager(Connection c) {
		this.c = c;
	}
	
	@Override
	public Patient searchPatient(Integer ID) {
		Patient p = null;
		try 
		{
			String sql="SELECT * FROM patients WHERE ID LIKE ?";
			PreparedStatement prep=c.prepareStatement(sql);
			prep.setInt(1, ID);
			ResultSet rs=prep.executeQuery();
			int id=rs.getInt("ID");
			String name=rs.getString("Name");
			String adress=rs.getString("Adress");
			Date DOB=rs.getDate("DOB");
			Integer phone = rs.getInt("Phone");
			String email=rs.getString("Email");
			String sportType=rs.getString("SportType");
			String disability=rs.getString("Disability");
			p = new Patient(id,name,adress,DOB,phone,email,sportType,disability);
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return p;
	}

	@Override
	public void modifyAppointment(Appointment a) {
		// TODO Auto-generated method stub

	}

}
