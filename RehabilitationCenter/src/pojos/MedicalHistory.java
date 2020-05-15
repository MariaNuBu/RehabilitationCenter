package pojos;

import java.io.Serializable;
import java.sql.Date;
import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder= {"Diseases","Allergies","Surgeries","WeightKg","HeightCm"})

public class MedicalHistory implements Serializable 
{
	@XmlAttribute
	private Integer ID;
	@XmlTransient
	private String Name;
	@XmlTransient
	private Date DOB;
	@XmlElement
	private String Diseases;
	@XmlElement
	private String Allergies;
	@XmlElement
	private String Surgeries;
	@XmlElement
	private Float WeightKg;
	@XmlElement
	private Integer HeightCm;
	
	
	
	public MedicalHistory(Integer iD, String name, Date dOB, String diseases, String allergies, String surgeries,
			Float weightKg, Integer heightCm) 
	{
		ID = iD;
		Name = name;
		DOB = dOB;
		Diseases = diseases;
		Allergies = allergies;
		Surgeries = surgeries;
		WeightKg = weightKg;
		HeightCm = heightCm;
	}
	
	public MedicalHistory(String name, Date dOB, String diseases, String allergies, String surgeries, Float weightKg,
			Integer heightCm) {
		super();
		Name = name;
		DOB = dOB;
		Diseases = diseases;
		Allergies = allergies;
		Surgeries = surgeries;
		WeightKg = weightKg;
		HeightCm = heightCm;
	}




	public MedicalHistory() {
		super();
	}
	public Integer getID() 	{return ID;	}
	public void setID(Integer iD) {	ID = iD;}
	public String getName() {return Name;	}
	public void setName(String name) {	Name = name;}
	public Date getDOB() {	return DOB;	}
	public void setDOB(Date dOB) {	DOB = dOB;	}
	public String getDiseases() {	return Diseases;}
	public void setDiseases(String diseases) {Diseases = diseases;	}
	public String getAllergies() {return Allergies;	}
	public void setAllergies(String allergies) {Allergies = allergies;	}
	public String getSurgeries() {	return Surgeries;	}
	public void setSurgeries(String surgeries) {Surgeries = surgeries;	}
	public Float getWeightKg() {return WeightKg;	}
	public void setWeightKg(Float weightKg) {	WeightKg = weightKg;	}
	public Integer getHeightCm() {	return HeightCm;	}
	public void setHeightCm(Integer heightCm) {	HeightCm = heightCm;}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
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
		MedicalHistory other = (MedicalHistory) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MedicalHistory [ID=" + ID + ", Name=" + Name + ", DOB=" + DOB + ", Diseases=" + Diseases
				+ ", Allergies=" + Allergies + ", Surgeries=" + Surgeries + ", WeightKg=" + WeightKg + ", HeightCm="
				+ HeightCm + "]";
	}
	
	
	
}
