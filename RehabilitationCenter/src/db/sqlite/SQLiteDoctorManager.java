package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.interfaces.DoctorManager;
import pojos.Patient;
import pojos.Treatment;

public class SQLiteDoctorManager implements DoctorManager {

	Connection c;
	
	public SQLiteDoctorManager(Connection c) 
	{
		this.c = c;
	}
	@Override
	public void readMH(Integer ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void modifyMH(Integer ID) {
		// TODO Auto-generated method stub

	}

	@Override
	public void createTreatment(Treatment t) {
		// TODO Auto-generated method stub

	}
   
	@Override
	public void modifyTreatment(Treatment t) {
			try {
				// Changes the Type and Length of a particular treatment
				String sql = "UPDATE treatment SET Type	=?, Length=?, WHERE ID=?";
				PreparedStatement s = c.prepareStatement(sql);
				s.setString(1, t.getType());
				s.setInt(2, t.getLenght());
				s.executeUpdate();
				s.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	

	@Override
	public void deleteTreatment(Treatment t ) {
		try {
			String sql = "DELETE FROM treatment WHERE ID=?";
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setInt(1, t.getId());
			ps.executeQuery(sql);	
			ps.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

	@Override
	public List<Treatment> listTreatments(Integer IDPat) {
		
		List<Treatment> treatmentList = new ArrayList<Treatment>();
		
		try {
			String sql = "SELECT * FROM treatment AS t JOIN patient AS p ON t.id=p.id WHERE p.id=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, IDPat);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String type= rs.getString(2);
				int length= rs.getInt(3);
				Treatment newTreat = new Treatment(id,type,length);
				// Add it to the list
				treatmentList.add(newTreat);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// Return the list
		return treatmentList;
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
			rs.close();
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
     return treatment;
	}
	@Override
	public Treatment getTreatment(Patient p) {
		Treatment treatment =null;
		try
		{
			String sql="SELECT * from treatment  AS t JOIN patient AS p ON t.id=p.id WHERE p.id=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, p.getId());
			ResultSet rs=ps.executeQuery();
			boolean treatmentCreated=false;
			while (rs.next())
			{
				if(!treatmentCreated)
				{
					Integer id=rs.getInt("ID");
					String  type=rs.getString("Type");
					Integer length=rs.getInt("Length");
					treatment = new Treatment(id,type,length);
					treatmentCreated=true;
				}
			}
			rs.close();
			ps.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return treatment;		
	}	
	}


