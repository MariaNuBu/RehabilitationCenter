package pojos;

import java.io.Serializable;
import java.sql.Date;

public class Patient implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String  name;
	private String  address;
	private Date    dob;
	private Integer phoneNumber;
	private String  eMail;
	private String  sport;
	private String  disability;
	
	public Patient() {
		super();
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
	
	public String getEmail() {
		return eMail;
	}
	
	public void setEmail(String eMail) {
		this.eMail = eMail;
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
