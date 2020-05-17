package db.interfaces;

import java.util.List;

import pojos.users.*;


public interface UserManager 
{
	public void connect();
	public void disconnect();
	public void createStaff();
	public void createUser(User user);
	public void createRole(Role role);
	public User checkPassword(String username,String password);
	public User checkPasswordStaff(String username, String password);
	public boolean isCreated (String role);
	public Role getRoleByName(String rolename);
	public boolean userCreated (String username);
	public void changePassword(String username,String password);
	public void fireWorkers (Integer ID);
	public Integer getUser(String username);
}
