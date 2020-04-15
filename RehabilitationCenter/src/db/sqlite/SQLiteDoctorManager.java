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
		MedicalHistory mh = null; //prueba
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
	public Treatment getTreatment( Integer treatID) {
		Treatment treatment =null;
		try
		{
			String sql="SELECT * from treatment  AS t JOIN patient AS p ON t.id=p.id WHERE t.id=?";
			PreparedStatement ps=c.prepareStatement(sql);
			ps.setInt(1, treatID);
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
	
	public List <Patient> SearchByName(String name){
		List <Patient> patientList = new ArrayList<Patient>();
		try {
			String sql="SELECT * FROM patient AS p JOIN PatientDoctor AS pd ON p.ID=pd.PATID "
					+ "WHERE p.Name LIKE ? GROUP BY pd.DOCID";
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id= rs.getInt("ID");
				String patientName = rs.getString("Name");
				String address =rs.getString("Address");
				Date date= rs.getDate("DOB");
				int phone= rs.getInt("Phone");
				String email= rs.getString("Email");
				String sport = rs.getString("SportType");
				String disability= rs.getString("Disability");
				Patient newPat= new Patient(id,patientName,address,date,phone,email,sport,disability);
				patientList.add(newPat);
				
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return patientList;
	}
	}


