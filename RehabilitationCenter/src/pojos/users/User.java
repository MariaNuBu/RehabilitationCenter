package pojos.users;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Entity
@Table(name="users")
public class User implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3881471776148143121L;
	@Id
	@GeneratedValue(generator="users")
	@TableGenerator(name="users", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="users")
	private Integer ID;
	@Column(unique = true)
	private String username;
	@Lob
	private byte[]  password;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="roleID")
	private Role role;	
	
	public User() 
	{
		super();
	}

	public User(String userName, byte[] password, Role role) 
	{
		super();
		this.username = userName;
		this.password = password;
		this.role = role;
	}
	

	public Integer getId() 
	{
		return ID;
	}
	public void setId(Integer id)
	{
		this.ID = id;
	}


	@Override
	public String toString() 
	{
		return "User [id=" + ID + ", userName=" + username + ", password=" + Arrays.toString(password) + ", role="
				+ role + "]";
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}


	public String getUserName() {
		return username;
	}


	public void setUserName(String userName) {
		this.username = userName;
	}


	public byte[] getPassword() {
		return password;
	}


	public void setPassword(byte[] password) {
		this.password = password;
	}


	public Role getRole() {
		return role;
	}


	public void setRole(Role role) {
		this.role = role;
	}
	

}
