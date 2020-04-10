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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ArrayList<PhysicalTherapist> physicalTherapists=new ArrayList<PhysicalTherapist>();
		PhysicalTherapist pt=null;
		try
		{
			//TODO conseguir que saque bien la fecha porque se introduce pero no la lee al salir
			String sql="SELECT ID,Name,sportType FROM physicalTherapist WHERE SportType=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setString(1, sport);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				//String address=rs.getString(3);
				//Date dob=rs.getDate(4);
				//Integer phone=rs.getInt(5);
				//String email=rs.getString(6);
				String sportType=rs.getString(3);
				//Double salary=rs.getDouble(8);
				//pt=new PhysicalTherapist(id,name,address,dob,phone,email,sportType,salary);
				//pt=new PhysicalTherapist(id,name,address,phone,email,sportType,salary);
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
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		ArrayList<PhysicalTherapist> physicalTherapists=new ArrayList<PhysicalTherapist>();
		PhysicalTherapist pt=null;
		try
		{
			//TODO conseguir que saque bien la fecha porque se introduce pero no la lee al salir
			String sql="SELECT ID,Name,sportType FROM physicalTherapist";
			PreparedStatement ps=c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer id=rs.getInt(1);
				String name=rs.getString(2);
				//String address=rs.getString(3);
				//Date dob=rs.getDate(4);
				//Integer phone=rs.getInt(5);
				//String email=rs.getString(6);
				String sportType=rs.getString(3);
				//Double salary=rs.getDouble(8);
				//pt=new PhysicalTherapist(id,name,address,dob,phone,email,sportType,salary);
				//pt=new PhysicalTherapist(id,name,address,phone,email,sportType,salary);
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
				String dob=rs.getString(4);
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				Date DOB = Date.valueOf(LocalDate.parse(dob, formatter));
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
	
	

	

}
