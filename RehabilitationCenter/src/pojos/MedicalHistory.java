package pojos;

import java.io.Serializable;
import java.sql.Date;

public class MedicalHistory implements Serializable 
{
	private Integer ID;
	private String Name;
	private Date DOB;
	private String Diseases;
	private String Allergies;
	private String Surgeries;
	private Float WeightKg;
	private Integer HeightKg;
	
	
	
	public MedicalHistory(Integer iD, String name, Date dOB, String diseases, String allergies, String surgeries,
			Float weightKg, Integer heightKg) 
	{
		ID = iD;
		Name = name;
		DOB = dOB;
		Diseases = diseases;
		Allergies = allergies;
		Surgeries = surgeries;
		WeightKg = weightKg;
		HeightKg = heightKg;
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
	public Integer getHeightKg() {	return HeightKg;	}
	public void setHeightKg(Integer heightKg) {	HeightKg = heightKg;}

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
	
	
}
