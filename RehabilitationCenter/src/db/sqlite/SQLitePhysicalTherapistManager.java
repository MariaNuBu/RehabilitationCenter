package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public void readTreatment(Integer ID) 
	{
		Treatment t=null;
		try
		{
			String sql="SELECT * FROM treatment WHERE ID=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1,ID);
			ResultSet rs=ps.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String type=rs.getString(2);
				Integer lenght=rs.getInt(3);
				t = new Treatment(id,type,lenght);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

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

	@Override
	public ArrayList<PhysicalTherapist> showPhisicalTherapists(String sport) 
	{
		ArrayList<PhysicalTherapist> physicalTherapists=new ArrayList<PhysicalTherapist>();
		PhysicalTherapist pt=null;
		try
		{
			String sql="SELECT * FROM physicalTherapist WHERE SportType=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setString(1, sport);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				String address=rs.getString(3);
				//Date dob=rs.getDate(4);
				Integer phone=rs.getInt(5);
				String email=rs.getString(6);
				String sportType=rs.getString(7);
				Double salary=rs.getDouble(8);
				//pt=new PhysicalTherapist(id,name,address,dob,phone,email,sportType,salary);
				pt=new PhysicalTherapist(id,name,address,phone,email,sportType,salary);
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
				Date dob=rs.getDate(4);
				Integer phone=rs.getInt(5);
				String email=rs.getString(6);
				String sport=rs.getString(7);
				Double salary=rs.getDouble(8);
				pt= new PhysicalTherapist(ID,name,address,dob,phone,email,sport,salary);
			}
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return pt;
	}
	
	

	

}
