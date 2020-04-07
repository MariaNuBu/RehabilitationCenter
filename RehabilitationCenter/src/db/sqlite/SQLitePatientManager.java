package db.sqlite;
import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.*;
import pojos.*;


public class SQLitePatientManager implements PatientManager {

	private Connection c;
	
	public SQLitePatientManager(Connection c) 
	{
		this.c = c;
	}
	
	@Override
	public ArrayList<Patient> searchPatientName(String patientName) 
	{
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
				String address=rs.getString("Address");
				Date DOB=rs.getDate("DOB");
				Integer phone = rs.getInt("Phone");
				String email=rs.getString("Email");
				String sportType=rs.getString("SportType");
				String disability=rs.getString("Disability");
				
				Patient pat = new Patient(id,name,address,DOB,phone,email,sportType,disability);
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
	public void addPatient (Patient p,PhysicalTherapist pt,MedicalHistory mh)
	{
		try
		{
			String sql="INSERT INTO patient (Name, Address, DOB, Phone, Email, SportType,Disability) "
					+  "VALUES (?,?,?,?,?,?,?";
			PreparedStatement prep=c.prepareStatement(sql);
			//TODO see how to do the autoincrement
			//prep.setInt(1, p.getId());
			prep.setString(1,p.getName());
			prep.setString(2,p.getAddress());
			prep.setDate(3,p.getDob());
			prep.setInt(4, p.getPhoneNumber());
			prep.setString(5, p.getEmail());
			prep.setString(6, p.getSport());
			prep.setString(7, p.getDisability());
			//prep.setInt(8, mh.getID());
			//prep.setInt(9,pt.getId());		
			prep.executeUpdate();
			prep.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void createMH(MedicalHistory mh)
	{
		try
		{
			String sql="INSERT INTO medicalHistory (ID,Name,DOB,Diseases,Allergies,Surgeries,WeightKg,HeightKg)"
					+  "VALUES (?,?,?,?,?,?,?,?)";
			PreparedStatement prep=c.prepareStatement(sql);
			//TODO see how to do the autoincrement
			prep.setInt(1, 0);
			prep.setString(2,mh.getName());
			prep.setDate(3,mh.getDOB());
			prep.setString(4,mh.getDiseases());			
			prep.setString(5,mh.getAllergies());
			prep.setString(6, mh.getSurgeries());
			prep.setFloat(7,mh.getWeightKg());
			prep.setInt(8, mh.getHeightKg());
			prep.executeUpdate();
			prep.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public List<Treatment> listTreatment(Patient patient)
	{
		ArrayList<Treatment> treatments=new ArrayList <Treatment>();
		try 
		{
			String sql="SELECT * FROM treatment AS t JOIN PatientTreatment AS pt ON t.ID=pt.TREATID"
					+ "WHERE PATID=?";
			PreparedStatement p=c.prepareStatement(sql);
			p.setInt(1, patient.getId());
			ResultSet rs=p.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String type=rs.getString(2);
				Integer length=rs.getInt(3);
				treatments.add(new Treatment(id,type,length));
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return treatments;
		
		
	}
	
	@Override
	public Patient getPatient(Integer id)
	{
		Patient patient =null;
		try
		{
			String sql="SELECT * from patient WHERE ID=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			boolean patientCreated=false;
			while (rs.next())
			{
				if(!patientCreated)
				{
					Integer patID=rs.getInt("ID");
					String name=rs.getString("Name");
					String address=rs.getString("Address");
					Date DOB=rs.getDate("DOB");
					Integer phone=rs.getInt("Phone");
					String email=rs.getString("Email");
					String sportType=rs.getString("SportType");
					String disability=rs.getString("Disability");
					patient = new Patient(patID, name, address,DOB,phone,email,sportType,disability);
					patientCreated=true;
				}
			}
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return patient;
		
	}

}


