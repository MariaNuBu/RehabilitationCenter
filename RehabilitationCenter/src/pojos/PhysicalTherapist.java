package pojos;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class PhysicalTherapist implements Serializable {

	private static final long serialVersionUID = 1L;
	

	private Integer id;
	private String name;
	private String address;
	private Date dob;
	private Integer phoneNumber;
	private String eMail;
	private String typeSport;
	private Double salary;
	private List <Patient> patients;
	
	
	
	public PhysicalTherapist(Integer id,String name, String address, Integer phoneNumber, String eMail, String typeSport,
			Double salary) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.typeSport = typeSport;
		this.salary = salary;
	}


	public PhysicalTherapist(Integer id, String name, String address, Date dob, Integer phoneNumber, String eMail,
			String typeSport, Double salary) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.typeSport = typeSport;
		this.salary = salary;
	}
	
	
	public PhysicalTherapist(Integer id, String name, String address, Date dob, Integer phoneNumber, String eMail,
			String typeSport, Double salary, List<Patient> patients) {
		super();
		this.id = id;
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.typeSport = typeSport;
		this.salary = salary;
		this.patients = patients;
	}


	public PhysicalTherapist(String name, String address, Date dob, Integer phoneNumber, String eMail, String typeSport,
			Double salary) {
		super();
		this.name = name;
		this.address = address;
		this.dob = dob;
		this.phoneNumber = phoneNumber;
		this.eMail = eMail;
		this.typeSport = typeSport;
		this.salary = salary;
	}



	public PhysicalTherapist() {
		super();
	}

	public String toString()
	{
		String s= "ID: "+this.id+"\nName: "+this.name+"\nAddress: "+this.address+
				//"\nDOB: "+this.dob+
				"\nEmail: "+this.eMail+
				"\nTypeSport: "+this.typeSport+"\nSalary: "+this.salary+"\nPhone: "+this.phoneNumber;
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
		PhysicalTherapist other = (PhysicalTherapist) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
	public void setAddress(String address) {
		this.address = address;
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
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getTypeSport() {
		return typeSport;
	}
	public void setTypeSport(String typeSport) {
		this.typeSport = typeSport;
	}
	public Double getSalary() {
		return salary;
	}
	public void setSalary(Double salary) {
		this.salary = salary;
	}
	
	
	
}
