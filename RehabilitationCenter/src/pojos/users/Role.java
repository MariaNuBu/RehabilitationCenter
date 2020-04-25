package pojos.users;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="roles")
public class Role implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 382040897504230764L;
	@Id
	@GeneratedValue(generator="roles")
	@TableGenerator(name="roles", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="roles")	
	private Integer ID;
	private String role;
	@OneToMany(mappedBy="role")
	private List<User> users;
	
	public Role() 
	{
		super();
		this.users= new ArrayList<User>();
	}
	
	public Role(Integer iD, String role) {
		super();
		ID = iD;
		this.role = role;
		this.users= new ArrayList<User>();
	}

	public Integer getID() 
	{
		return ID;
	}
	public void setID(Integer iD) 
	{
		ID = iD;
	}
	public String getRole() 
	{
		return role;
	}
	public void setRole(String role)
	{
		this.role = role;
	}
	public List<User> getUsers() 
	{
		return users;
	}
	public void setUsers(List<User> users) 
	{
		this.users = users;
	}
	@Override
	public int hashCode() 
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) 
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Role other = (Role) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Role [ID=" + ID + ", role=" + role + "]";
	}
}
