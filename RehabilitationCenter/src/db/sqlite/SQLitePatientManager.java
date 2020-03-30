package db.sqlite;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import db.interfaces.PatientManager;
import pojos.*;


public class SQLitePatientManager implements PatientManager {

	private Connection c;
	
	public SQLitePatientManager(Connection c) {
		this.c = c;
	}
	
	@Override
	public ArrayList<Patient> searchPatientName(String patientName) {
		ArrayList <Patient> p = new ArrayList<Patient>();
		try 
		{
			String sql="SELECT * FROM patients WHERE name LIKE ?";
			PreparedStatement prep=c.prepareStatement(sql);
			prep.setString(1,  "%" + patientName + "%");
			ResultSet rs=prep.executeQuery();
			while(rs.next())
			{
				int id=rs.getInt("ID");
				String name=rs.getString("Name");
				String adress=rs.getString("Adress");
				Date DOB=rs.getDate("DOB");
				Integer phone = rs.getInt("Phone");
				String email=rs.getString("Email");
				String sportType=rs.getString("SportType");
				String disability=rs.getString("Disability");
				Patient pat = new Patient(id,name,adress,DOB,phone,email,sportType,disability);
				p.add(pat);
			}
			
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
