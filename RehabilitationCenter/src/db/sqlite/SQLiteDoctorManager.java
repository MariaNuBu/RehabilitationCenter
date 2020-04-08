package db.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

import db.interfaces.DoctorManager;
import pojos.MedicalHistory;
import pojos.Treatment;

public class SQLiteDoctorManager implements DoctorManager {

	Connection c;
	
	public SQLiteDoctorManager(Connection c) 
	{
		this.c = c;
	}
	@Override
	public void readMH(Integer ID) {
		MedicalHistory mh = null;
		try {
			String sql = "SELECT * FROM medicalhistory WHERE ID=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1,ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer id = rs.getInt("iD");
				String name = rs.getString("name");
				Date DOB = rs.getDate("dOB");
				String desease = rs.getString("diseases");
				String allergies = rs.getString("allergies");
				String surgeries = rs.getString("surgeries");
				Float weightKg = rs.getFloat("weightKg");
				Integer heightCm = rs.getInt("heightCm");
						
				mh = new MedicalHistory(id,name,DOB,desease,allergies,surgeries,weightKg,heightCm);
			}
			rs.close();
			ps.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
			
		}


	@Override
	public void modifyMH(MedicalHistory MH) {
		try {
		String sql = "UPDATE medicalHistory SET id=?, name=?, DOB=?, disease=?, allergies=?, surgeries=?,"
				+ "weightKg=?, heightCm WHERE id=?";
		PreparedStatement s = c.prepareStatement(sql);
		s.setInt(1, MH.getID());
		s.setString(2, MH.getName());
		s.setDate(3, MH.getDOB());
		s.setString(4, MH.getDiseases());
		s.setString(5, MH.getAllergies());
		s.setFloat(6, MH.getWeightKg());
		s.setInt(7, MH.getHeightCm());
		s.executeUpdate();
		s.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void createTreatment(Treatment t) {
		try {
			String sql = "INSERT INTO treatments (id, type, length) "
					+ "VALUES (?,?,?,?,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1, t.getId()); 
			ps.setString(2, t.getType());
			ps.setInt(3, t.getLenght());
			ps.executeUpdate();
			ps.close();
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public void modifyTreatment(Treatment t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteTreatment(Integer IDPat, Integer IDTreat) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Treatment> listTreatments(Integer IDPat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readTreatment(Treatment t) {
		// TODO Auto-generated method stub

	}

}
