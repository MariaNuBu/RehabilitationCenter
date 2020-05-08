package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import db.interfaces.PhysicalTherapistManager;
import pojos.*;

public class SQLitePhysicalTherapistManager implements PhysicalTherapistManager {

private Connection c;
	
	public SQLitePhysicalTherapistManager(Connection c) 
	{
		this.c = c;
	}
	
	@Override
	public void addPhysicalTherapist (PhysicalTherapist pt)
	{
		try
		{
			String sqlphysicalTherapist="INSERT INTO physicalTherapist (Name, Address, DOB, Phone, Email, SportType,Salary)"
					+  "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement prep=c.prepareStatement(sqlphysicalTherapist);
			prep.setString(1,pt.getName());
			prep.setString(2,pt.getAddress());
			prep.setDate(3,pt.getDob());
			prep.setInt(4, pt.getPhoneNumber());
			prep.setString(5, pt.geteMail());
			prep.setString(6, pt.getTypeSport());
			prep.setDouble(7, pt.getSalary());		
			prep.executeUpdate();
			prep.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	@Override
	public Integer searchPTByEmail (String email)
	{
		Integer ptID=null;
		try
		{
			String sql="SELECT ID FROM physicalTherapist WHERE Email=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, email);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				ptID = rs.getInt(1);
			}
			ps.close();
			rs.close();					
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return ptID;
	}
	
	@Override
	public Treatment readTreatment(Treatment t) 
	{
		Treatment treatment=null;
		try
		{
			String sql="SELECT * FROM treatment WHERE ID=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1,t.getId());
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String type=rs.getString(2);
				Integer lenght=rs.getInt(3);
				treatment = new Treatment(id,type,lenght);
			}
			ps.close();

		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
     return treatment;
	}
	
	
	
	@Override
	public void insertPhysicalTherapist(PhysicalTherapist pt)
	{
		try 
		{
			String sql="INSERT INTO physicalTherapist (Name,Address,DOB,Phone,Email,SportType,Salary)"
					+ "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, pt.getName());
			ps.setString(2, pt.getAddress());
			ps.setDate(3,pt.getDob());
			ps.setInt(4, pt.getPhoneNumber());
			ps.setString(5, pt.geteMail());
			ps.setString(6,pt.getTypeSport());
			ps.setDouble(7, pt.getSalary());
			ps.executeUpdate();
			ps.close();
		} 
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	
	public ArrayList<PhysicalTherapist> showPhysicalTherapists(String sport) 
	{
		ArrayList<PhysicalTherapist> physicalTherapists=new ArrayList<PhysicalTherapist>();
		PhysicalTherapist pt=null;
		try
		{
			String sql="SELECT ID,Name,sportType FROM physicalTherapist WHERE SportType=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setString(1, sport);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				String sportType=rs.getString(3);
				pt=new PhysicalTherapist(id,name,sportType);
				physicalTherapists.add(pt);
			}
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return physicalTherapists;
	}
	
	public ArrayList<PhysicalTherapist> showAllPhysicalTherapists() 
	{
		ArrayList<PhysicalTherapist> physicalTherapists=new ArrayList<PhysicalTherapist>();
		PhysicalTherapist pt=null;
		try
		{
			String sql="SELECT ID,Name,sportType FROM physicalTherapist";
			PreparedStatement ps=c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				String sportType=rs.getString(3);
				pt=new PhysicalTherapist(id,name,sportType);
				physicalTherapists.add(pt);
			}
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return physicalTherapists;
	}
	
	public PhysicalTherapist getPhysicalTherapist(Integer id)
	{
		PhysicalTherapist pt=null;
		try
		{
			String sql="SELECT * FROM physicalTherapist WHERE ID=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, id);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				int ID=rs.getInt(1);
				String name=rs.getString(2);
				String address=rs.getString(3);
				Date DOB=rs.getDate(4);
				Integer phone=rs.getInt(5);
				String email=rs.getString(6);
				String sport=rs.getString(7);
				Double salary=rs.getDouble(8);
				pt= new PhysicalTherapist(ID,name,address,DOB,phone,email,sport,salary);
			}
			ps.close();

		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return pt;
	}

	@Override
	public ArrayList<Patient> getAllPatients(Integer ptid) 
	{
		ArrayList <Patient> patients = new ArrayList<Patient>();
		try
		{
			String sql="SELECT ID,Name FROM patient WHERE PTID=?";
			PreparedStatement pt= c.prepareStatement(sql);
			pt.setInt(1, ptid);
			ResultSet rs=pt.executeQuery();
			Patient p=null;
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				p=new Patient(id,name);
				patients.add(p);
			}
			pt.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
			
		return patients;
	}
	
	public List <PhysicalTherapist> listPhysicalTherapists()
	{
		List <PhysicalTherapist> physicalTherapists = new ArrayList<PhysicalTherapist>();
		try
		{
			String sql = "SELECT * FROM physicalTherapist";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer ID = rs.getInt("ID");
				String name = rs.getString("Name");
				String address = rs.getString("Address");
				Date DOB = rs.getDate("DOB");				
				Integer phone = rs.getInt("Phone");
				String email = rs.getString("Email");
				String sport = rs.getString("SportType");
				Double salary = rs.getDouble("Salary");
				PhysicalTherapist pt = new PhysicalTherapist(ID, name,address, DOB, phone, email, sport,salary);
				//System.out.println(pt);
				physicalTherapists.add(pt);
			}
			ps.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return physicalTherapists;
	}
	
	@Override
	public void deletePhysicalTherapist(Integer ID)
	{
		try
		{
			String sql = "DELETE FROM physicalTherapist WHERE ID = ?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1,ID);
			ps.executeUpdate();
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	

}
