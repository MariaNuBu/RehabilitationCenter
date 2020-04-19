package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;

	//TODO check if the atributes of the foreign keys are necesary
	private Integer id;
	private String  name;
	private String  address;
	private Date    dob;
	private Integer phoneNumber;
	private String  eMail;
	private String  sport;
	private String  disability;
	private MedicalHistory medicalHistory;
	private PhysicalTherapist physicalTerapist;
	private List<Doctor> doctors;
	private ArrayList<Appointment> appointments;

	public Patient() {
		super();
	}

	public Patient(Integer id, String name) 
	{
		super();
		this.id = id;
		this.name = name;
	}

	public Patient(Integer id, String name, String address, Date dob, Integer phoneNumber, String eMail, String sport,
			String disability,MedicalHistory mh,PhysicalTherapist pt,List<Doctor> docs)
	{
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.sport = sport;
		this.disability = disability;
		this.medicalHistory=mh;
		this.physicalTerapist=pt;
		this.doctors = docs;
	}

	public Patient(String name, String address, Date dob, Integer phoneNumber, String eMail, String sport,
			String disability) {
		super();
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.sport = sport;
		this.disability = disability;
	}


	public Patient(Integer id, String name, String address, Date dob, Integer phoneNumber, String eMail, String sport,
			String disability) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.sport = sport;
		this.disability = disability;
	}

	public Patient(Integer id, String name, Date dob, String eMail, String sport, String disability) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.eMail = eMail;
		this.sport = sport;
		this.disability = disability;
	}

	public Patient(Integer id, String name, Date dob, String eMail, String sport, String disability,
			PhysicalTherapist physicalTerapist) {
		super();
		this.id = id;
		this.name = name;
		this.dob = dob;
		this.eMail = eMail;
		this.sport = sport;
		this.disability = disability;
		this.physicalTerapist = physicalTerapist;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String adress) {
		this.address = adress;
	}

	public Date getDob() {
		return dob;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Integer getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Integer phoneNumber) {
	this.phoneNumber = phoneNumber;
	}

	public String getSport() {
		return sport;
	}

	public void setSport(String sport) {
		this.sport = sport;
	}

	public String getDisability() {
		return disability;
	}

	public void setDisability(String disability) {
		this.disability = disability;
	}

	public MedicalHistory getMedicalHistory() {
		return medicalHistory;
	}


	public void setMedicalHistory(MedicalHistory medicalHistory) {
		this.medicalHistory = medicalHistory;
	}


	public PhysicalTherapist getPhysicalTerapist() {
		return physicalTerapist;
	}


	public void setPhysicalTerapist(PhysicalTherapist physicalTerapist) {
		this.physicalTerapist = physicalTerapist;
	}


	public List<Doctor> getDoctors() {
		return doctors;
	}


	public void setDoctors(List<Doctor> doctors) {
		this.doctors = doctors;
	}
	

	public String geteMail() {
		return eMail;
	}

	public void seteMail(String eMail) {
		this.eMail = eMail;
	}

	public ArrayList<Appointment> getAppointments() {
		return appointments;
	}

	public void setAppointments(ArrayList<Appointment> appointments) {
		this.appointments = appointments;
	}

	public String toString()
	{
		String s= "ID: "+this.id+"\nName: "+this.name+"\nAddress: "+this.address+"\nDOB: "+this.dob+"\nEmail: "+this.eMail+
				"\nTypeSport: "+this.sport+"\nDisability: "+this.disability;
		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Patient other = (Patient) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
