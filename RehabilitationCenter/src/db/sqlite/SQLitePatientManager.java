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
			String sql="SELECT * FROM patient WHERE name LIKE ?";
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
			prep.close();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return p;
	}
	
	@Override
	public int getLastId() 
	{
		int id = 0;
		try {
			String query = "SELECT last_insert_rowid() AS lastId";
			PreparedStatement p = c.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			id = rs.getInt("lastId");
			p.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id;
	}
	
	@Override
	public void addPatientandMedicalHistory (Patient p,PhysicalTherapist pt,MedicalHistory mh)
	{
		try
		{
			String sqlMedicalHistory="INSERT INTO medicalHistory (Name,DOB,Diseases,Allergies,Surgeries,WeightKg,HeightCm)"
					+  "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps=c.prepareStatement(sqlMedicalHistory);
			ps.setString(1,mh.getName());
			ps.setDate(2,mh.getDOB());
			ps.setString(3,mh.getDiseases());			
			ps.setString(4,mh.getAllergies());
			ps.setString(5, mh.getSurgeries());
			ps.setFloat(6,mh.getWeightKg());
			ps.setInt(7,mh.getHeightCm());
			ps.executeUpdate();
			ps.close();

			int mhid=getLastId();
			String sqlpatient="INSERT INTO patient (Name, Address, DOB, Phone, Email, SportType,Disability,MHID,PTID)"
					+  "VALUES (?,?,?,?,?,?,?,?,?)";
			PreparedStatement prep=c.prepareStatement(sqlpatient);
			prep.setString(1,p.getName());
			prep.setString(2,p.getAddress());
			prep.setDate(3,p.getDob());
			prep.setInt(4, p.getPhoneNumber());
			prep.setString(5, p.geteMail());
			prep.setString(6, p.getSport());
			prep.setString(7, p.getDisability());
			prep.setInt(8, mhid);
			prep.setInt(9,pt.getId());		
			prep.executeUpdate();
			prep.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Treatment> listTreatment(Patient patient)
	{
		ArrayList<Treatment> treatments=new ArrayList <Treatment>();
		try 
		{
			//TODO como hacer bien este join porque explota
			String sql="SELECT * FROM treatment AS t JOIN PatientTreatment AS pt ON t.ID=pt.TREATID JOIN patient AS p ON p.ID=pt.PATID"
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
			p.close();
			
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
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return patient;		
	}

	@Override
	public MedicalHistory getMedicalHistory(Patient p)
	{
		MedicalHistory mh=null;
		try
		{
			String sql="SELECT * FROM medicalHistory AS mh JOIN patient AS p ON mh.ID=p.MHID WHERE p.id=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ResultSet rs=ps.executeQuery();
			

			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				Date dob=rs.getDate(3);
				String diseases=rs.getString(4);
				String allergies=rs.getString(5);
				String surgeries=rs.getString(6);
				Float weight=rs.getFloat(7);
				Integer height=rs.getInt(8);
				mh=new MedicalHistory(id,name,dob,diseases,allergies,surgeries,weight,height);
			}	
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return mh;
	}
	
	
	

}


