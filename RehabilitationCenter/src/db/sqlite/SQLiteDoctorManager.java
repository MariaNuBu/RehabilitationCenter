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
import pojos.PhysicalTherapist;
import pojos.Doctor;
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
			String sql = "SELECT * FROM treatment AS t JOIN PatientTreatment AS pt ON t.ID=pt.TREATID JOIN patient AS p ON p.ID=pt.PATID "
					+ "WHERE PATID=?";
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
		//TODO revisar esta query que no esta bien cambiar tambien los numeros
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

	public List <Patient> SearchByName(String name,Integer docId){
		List <Patient> patientList = new ArrayList<Patient>();
		try {
			String sql="SELECT * FROM patient AS p JOIN PatientDoctor AS pd ON p.ID=pd.PATID "
					+ "WHERE p.Name LIKE ? AND pd.DOCID=?";
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ps.setInt(2, docId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				int id= rs.getInt(1);
				String patientName = rs.getString(2);
				String address =rs.getString(3);
				Date date= rs.getDate(4);
				int phone= rs.getInt(5);
				String email= rs.getString(6);
				String sport = rs.getString(7);
				String disability= rs.getString(8);
				Patient newPat= new Patient(id,patientName,address,date,phone,email,sport,disability);
				patientList.add(newPat);

			}


		}catch(Exception e) {
			e.printStackTrace();
		}
		return patientList;
	}

	public List<Doctor>searchDoctorByName(String name){
		List<Doctor>doctorList = new ArrayList<Doctor>();
		try{

			String sql = "SELECT ID,Name,Email FROM doctor WHERE name LIKE ?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setString(1, "%"+name+"%");
			ResultSet rs = prepS.executeQuery();
		while(rs.next()){
			int id = rs.getInt("ID");
			String docName = rs.getString("Name");
			String email = rs.getString("Email");
			Doctor doc = new Doctor(id, docName, email);
			doctorList.add(doc);
		}

		}catch(Exception e){
			e.printStackTrace();
		}
		return doctorList;
	}

	public List<Patient> getDoctorsPatients(Integer docID){ //coge todos los pacientes del médico que esté usando la database
												   //se consigue un ArrayList<Patient>
		List<Patient> doctorsPatients = new ArrayList<Patient>();
		try{

			String sql = "SELECT * FROM doctor AS d JOIN PatientDoctor AS pd ON d.ID = pd.DOCID "
						+"JOIN patient AS p ON pd.PATID = p.ID "
						+"WHERE d.ID = ?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, docID);
			ResultSet rs = prepS.executeQuery();

			while(rs.next()){
				int patID = rs.getInt(11);
				String name = rs.getString(12);
				Date DOB = rs.getDate(14);
				String eMail = rs.getString(16);
				String sport = rs.getString(17);
				String disability = rs.getString(18);
				Patient patient = new Patient(patID, name, DOB, eMail, sport, disability);
				PhysicalTherapist pTherapist = patient.getPhysicalTerapist();
				Patient patient2 = new Patient(patID, name, DOB, eMail, sport, disability, pTherapist);
				doctorsPatients.add(patient2);
			}


		}catch(Exception e){
			e.printStackTrace();
		}

		return doctorsPatients;
	}

}



