package db.jpa;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
	
	@Override
	public void createStaff()
	{
		Role staff = null;
		if (!isCreated("Human Resources"))
		{
			em.getTransaction().begin();
			//Use it once to create the Human Resources person who is going to register people
			staff = new Role ("Human Resources");
			em.persist(staff);
			em.getTransaction().commit();
			System.out.println("creado rol");
		}
		//SUPUESTAMENTE ESTE BLOQUE NO LO NECESITARÍAMOS PORQUE NOS DEVUELVE DIRECTAMENTE EL ROLE
		
		User boss=null;
		Boolean userStaff=userCreated("boss");
		if(userStaff==false)
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
			System.out.println("creado user");

		}
		else
		{
			//Don't do anything because the boss has been already created
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
	
	public Integer getUser(String username)
	{
		Query q = em.createNativeQuery("SELECT * FROM users WHERE username=?",User.class);
		q.setParameter(1,username);
		User user = (User) q.getSingleResult();
		return user.getId();
	}
	@Override
	public Role getRoleByName(String roleName)
	{
		Query q = em.createNativeQuery("SELECT * FROM roles WHERE role=?",Role.class);
		q.setParameter(1, roleName);
		Role role=(Role) q.getSingleResult();
		return role;
	}
	@Override
	public boolean isCreated (String role) //TODO VERIFICAR ESTE METODO CREO QUE CON EL CATCH ESTÁ ARREGLADO
	{
		Role created=null;
		try
		{	
			Query q = em.createNativeQuery("SELECT * FROM roles WHERE role = ? ",Role.class);
			q.setParameter(1, role);		
			created = (Role) q.getSingleResult();
			return true;
		}
		catch (NoResultException e)
		{
			return false;
		}			
		
	}
	@Override
	public boolean userCreated (String userName)
	{
		Query q = em.createNativeQuery("SELECT * FROM users WHERE username = ? ",User.class);
		q.setParameter(1, userName);
		User created = null;
		try
		{
			created = (User)q.getSingleResult();
			return true;
		}
		catch(NoResultException e)
		{
			return false;
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
	
	@Override
	public User checkPasswordStaff(String username, String password)
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
	
	public void changePassword(String username,String password)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("SHA-512");
			md.update(password.getBytes());
			byte [] hash=md.digest();
			Query q = em.createNativeQuery("SELECT * FROM users WHERE username=?",User.class);
			q.setParameter(1, username);
			User user = (User) q.getSingleResult();
			em.getTransaction().begin();
			user.setPassword(hash);
			em.getTransaction().commit();
			em.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	public void fireWorkers (Integer ID)
	{
		Query q = em.createNativeQuery("SELECT * FROM users WHERE ID=?",User.class);
		q.setParameter(1, ID);
		User workerFired =(User) q.getSingleResult();
		em.getTransaction().begin();
		em.remove(workerFired);
		em.getTransaction().commit();
		em.close();
	}
}
