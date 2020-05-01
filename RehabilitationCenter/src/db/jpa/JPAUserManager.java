package db.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;

import db.interfaces.UserManager;
import pojos.users.Role;
import pojos.users.User;

public class JPAUserManager implements UserManager {

	private EntityManager em;
	@Override
	public void connect()
	{
		em = Persistence.createEntityManagerFactory("rehabilitationCenter-provider").createEntityManager();
		em.getTransaction().begin();
		em.createNativeQuery("PRAGMA foreign_keys=ON").executeUpdate();
		em.getTransaction().commit();
		createStaff();	
	}

	public void createStaff()
	{
		Role staff=null;
		User boss=null;
		if(isCreated("Human Resources")==false)
		{
			em.getTransaction().begin();
			//Use it once to create the Human Resorces person who is going to register people
			staff = new Role (1,"Human Resources");
			em.persist(staff);
			em.getTransaction().commit();
		}
		else
		{
			staff = getRoleByName("Human Resources");
		}
		if(userCreated("boss")==false)
		{
			//Use this to crate the username and password of this person
			String username = "boss";
			String password = "fuckingboss";
			MessageDigest md=null;
			try 
			{
				md = MessageDigest.getInstance("SHA-512");
			} 
			catch (NoSuchAlgorithmException e) 
			{			
				e.printStackTrace();
			}
			md.update(password.getBytes());
			byte [] hash = md.digest();
			boss = new User (username,hash,staff);
			em.getTransaction().begin();
			em.persist(boss);
			em.getTransaction().commit();
		}
		else
		{
			//Don't do anything because the boss has been alredy created}
		}
		
		
	}
	@Override
	public void disconnect() 
	{
		em.close();
	}

	@Override
	public void createUser(User user) 
	{
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	@Override
	public void createRole(Role role) 
	{
		em.getTransaction().begin();
		em.persist(role);
		em.getTransaction().commit();
	}

	@Override
	public Role getRole(int id)
	{
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE ID=?",Role.class);
		q.setParameter(1, id);
		Role role=(Role) q.getSingleResult();
		return role;
	}
	
	public Role getRoleByName(String roleName)
	{
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE role=?",Role.class);
		q.setParameter(1, roleName);
		Role role=(Role) q.getSingleResult();
		return role;
	}
	
	public boolean isCreated (String role) //TODO VERIFICAR ESTE METODO CREO QUE CON EL CATCH ESTÁ ARREGLADO
	{
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE role = ? ",Role.class);
		Role created=null;
		try
		{
			q.setParameter(1, role);
			created = (Role)q.getSingleResult();
		}
		catch (javax.persistence.NoResultException e)
		{
			e.printStackTrace();
		}
		if(created==null)
		{
			System.out.println("false");

			return false;
		}
		else
		{
			System.out.println("true");

			return true;
		}
	}
	
	public boolean userCreated (String userName)
	{
		Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? ",User.class);
		q.setParameter(1, userName);
		User created = (User)q.getSingleResult();
		if(created==null)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	@Override
	public List<Role> getRoles() 
	{
		Query q = em.createNativeQuery("SELECT * FROM roles",Role.class);
		List<Role> roles = (List<Role>)q.getResultList();
		return roles;
	}

	@Override
	public User checkPassword(String username, String password)
	{
		User user=null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			byte [] hash=md.digest();
			Query q = em.createNativeQuery("SELECT * FROM users WHERE username=? AND password=?", User.class);
			q.setParameter(1, username);
			q.setParameter(2, hash);
			user = (User) q.getSingleResult();
		}
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return user;
	}
	public User checkPasswordStaff(String username, String password)
	{
		User user=null;
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			byte [] hash=md.digest();
			Query q = em.createNativeQuery("SELECT * FROM users WHERE username=? AND password=? AND ID=?", User.class);
			q.setParameter(1, username);
			q.setParameter(2, hash);
			q.setParameter(3, 1);
			user = (User) q.getSingleResult();
		}
		catch (NoSuchAlgorithmException e) 
		{
			e.printStackTrace();
		}
		return user;
	}

}
