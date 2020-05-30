package db.sqlite;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import db.interfaces.DoctorManager;
import db.interfaces.PatientManager;
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
	public void createDoctor(Doctor doc) 
	{
		try
		{
			String sqldoctor="INSERT INTO doctor (Name, Address, DOB, Phone, Email, Specialty,Salary)"
					+  "VALUES (?,?,?,?,?,?,?)";
			PreparedStatement prep=c.prepareStatement(sqldoctor);
			prep.setString(1,doc.getName());
			prep.setString(2,doc.getAddress());
			prep.setDate(3,doc.getDob());
			prep.setInt(4, doc.getPhoneNumber());
			prep.setString(5, doc.geteMail());
			prep.setString(6, doc.getSpeciality());
			prep.setDouble(7, doc.getSalary());		
			prep.executeUpdate();
			prep.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
	}
	
	@Override
	public Integer searchDoctorByEmail(String email)
	{
		Integer docID= null;
		try
		{
			String sql = "SELECT ID FROM doctor WHERE Email=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1,email);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				docID=rs.getInt("ID");
			}
			ps.close();
			rs.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return docID;
	}
	
	@Override
	public void readMH(Integer ID) 
	{
		MedicalHistory mh = null; 
		try 
		{
			String sql = "SELECT * FROM medicalhistory WHERE ID=?";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setInt(1,ID);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer id = rs.getInt("iD");
				String name = rs.getString("name");
				Date DOB = rs.getDate("DOB");
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
	public void modifyMH(MedicalHistory MH) 
	{
		try 
		{
			String sql = "UPDATE medicalHistory SET ID=?, Name=?, DOB=?, Diseases=?, Allergies=?, Surgeries=?,"
					+ "WeightKg=?, HeightCm=? WHERE ID=?";
			PreparedStatement s = c.prepareStatement(sql);
			s.setInt(1, MH.getID());
			s.setString(2, MH.getName());
			s.setDate(3, MH.getDOB());
			s.setString(4, MH.getDiseases());
			s.setString(5, MH.getAllergies());
			s.setString(6,MH.getSurgeries());
			s.setFloat(7, MH.getWeightKg());
			s.setInt(8, MH.getHeightCm());
			s.setInt(9, MH.getID());
			s.executeUpdate();
			s.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public int getLastId() 
	{
		int id = 0;
		try 
		{
			String query = "SELECT last_insert_rowid() AS lastId";
			PreparedStatement p = c.prepareStatement(query);
			ResultSet rs = p.executeQuery();
			id = rs.getInt("lastId");
			p.close();

		}
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return id;
	}

	@Override
	public void createTreatment(Treatment t,Integer patientID,Integer DOCID,PatientManager pm) 
	{
		try 
		{
			String sql = "INSERT INTO treatment (type, length,DOCID) "
					+ "VALUES (?,?,?);";
			PreparedStatement ps = c.prepareStatement(sql);
			ps.setString(1, t.getType());
			ps.setInt(2, t.getLenght());
			ps.setInt(3,DOCID);
			ps.executeUpdate();
			ps.close();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		Integer treatmentID=getLastId();
		try
		{
			String sql2="INSERT INTO PatientTreatment (PATID,TREATID) VALUES (?,?)";
			PreparedStatement ps2 = c.prepareStatement(sql2);
			ps2.setInt(1, patientID);
			ps2.setInt(2, treatmentID);
			ps2.executeUpdate();
			ps2.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void modifyTreatment(Treatment t) 
	{
			try 
			{
				// Changes the Type and Length of a particular treatment
				String sql = "UPDATE treatment SET Type	=?, Length=? WHERE ID=?";
				PreparedStatement s = c.prepareStatement(sql);
				s.setString(1, t.getType());
				s.setInt(2, t.getLenght());
				s.setInt(3, t.getId());
				s.executeUpdate();
				s.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
	}

	@Override
	public void deleteTreatment(Treatment t,Integer patID) 
	{	
		try 
		{
			String sql2 = "DELETE FROM PatientTreatment WHERE PATID=? AND TREATID=?";
			PreparedStatement ps2= c.prepareStatement(sql2);			
			ps2.setInt(1, patID);
			ps2.setInt(2, t.getId());
			ps2.executeUpdate();
			ps2.close();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public List<Treatment> listTreatments(Integer IDPat)
	{
		List<Treatment> treatmentList = new ArrayList<Treatment>();
		try 
		{
			String sql = "SELECT * FROM treatment AS t JOIN PatientTreatment AS pt ON t.ID=pt.TREATID JOIN patient AS p ON p.ID=pt.PATID "
					+ "WHERE PATID=?";
			PreparedStatement prep = c.prepareStatement(sql);
			prep.setInt(1, IDPat);
			ResultSet rs = prep.executeQuery();
			while (rs.next()) 
			{
				int id = rs.getInt(1);
				String type= rs.getString(2);
				int length= rs.getInt(3);
				Treatment newTreat = new Treatment(id,type,length);
				// Add it to the list
				treatmentList.add(newTreat);
			}
			prep.close();
		} 
		catch (Exception e) 
		{
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
	public Treatment getTreatment( Integer treatID) 
	{
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

	@Override
	public List <Patient> SearchByName(String name,Integer docId)
	{
		List <Patient> patientList = new ArrayList<Patient>();
		try
		{
			String sql="SELECT * FROM patient AS p JOIN PatientDoctor AS pd ON p.ID=pd.PATID "
					+ "WHERE p.Name LIKE ? AND pd.DOCID=?";
			PreparedStatement ps= c.prepareStatement(sql);
			ps.setString(1, "%"+name+"%");
			ps.setInt(2, docId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) 
			{
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
			
			ps.close();
		}
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		return patientList;
	}

	@Override
	public List<Doctor>searchDoctorByName(String name)
	{
		List<Doctor>doctorList = new ArrayList<Doctor>();
		try
		{
			String sql = "SELECT ID,Name,Email FROM doctor WHERE name LIKE ?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setString(1, "%"+name+"%");
			ResultSet rs = prepS.executeQuery();
			while(rs.next())
			{
				int id = rs.getInt("ID");
				String docName = rs.getString("Name");
				String email = rs.getString("Email");
				Doctor doc = new Doctor(id, docName, email);
				doctorList.add(doc);
			}
			prepS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return doctorList;
	}
	
	@Override
	public List<Patient> getDoctorsPatients(Integer docID)//Get all the patients from a doctor
	{ 
		List<Patient> doctorsPatients = new ArrayList<Patient>();
		try
		{
			String sql = "SELECT * FROM patient AS p JOIN PatientDoctor AS pd ON p.ID = pd.PATID "
						+"JOIN doctor AS d ON pd.DOCID = d.ID "
						+"WHERE d.ID = ?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, docID);
			ResultSet rs = prepS.executeQuery();
			while(rs.next())
			{
				int patID = rs.getInt(1);
				String name = rs.getString(2);
				Date DOB = rs.getDate(4);
				String eMail = rs.getString(6);
				String sport = rs.getString(7);
				String disability = rs.getString(8); 
				Patient patient = new Patient(patID, name, DOB, eMail, sport, disability);
				PhysicalTherapist pTherapist = patient.getPhysicalTerapist();
				Patient patient2 = new Patient(patID, name, DOB, eMail, sport, disability, pTherapist);
				doctorsPatients.add(patient2);
			}
			prepS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return doctorsPatients;
	}

	@Override
	public Doctor getDoctor(int docId) 
	{
		Doctor doc = null;
		try
		{
			String sql = "SELECT ID,Name,Phone,Email,Specialty FROM doctor WHERE ID=?";
			PreparedStatement prepS = c.prepareStatement(sql);
			prepS.setInt(1, docId);
			ResultSet rs = prepS.executeQuery();
			boolean docExists = false;
			while(rs.next())
			{
				if(!docExists)
				{
					Integer docID = rs.getInt("ID");
					String docName = rs.getString("Name");
					Integer docPhoneNumber = rs.getInt("Phone");
					String docEmail = rs.getString("Email");
					String specialty = rs.getString("Specialty");
					doc = new Doctor(docID, docName, docPhoneNumber, docEmail, specialty);
				}
			}
			prepS.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return doc;
	}
	
	@Override
	public List <Doctor> listDoctors()
	{
		List <Doctor> doctors = new ArrayList <Doctor>();
		try
		{
			String sql = "SELECT * FROM doctor";
			PreparedStatement ps = c.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while(rs.next())
			{
				Integer docID = rs.getInt("ID");
				Date DOB = rs.getDate("DOB");				
				String name = rs.getString("Name");
				String address = rs.getString("Address");
				Integer phone = rs.getInt("Phone");
				String email = rs.getString("Email");
				String specialty = rs.getString("Specialty");
				Double salary = rs.getDouble("Salary");
				Doctor doctor = new Doctor(docID, name,address, DOB, phone, email, specialty,salary);
				doctors.add(doctor);
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return doctors;
	}
	
	@Override
	public void deleteDoctor(Integer ID)
	{
		try
		{
			String sql = "DELETE FROM doctor WHERE ID = ?";
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


