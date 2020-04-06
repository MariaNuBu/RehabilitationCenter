package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	

}
