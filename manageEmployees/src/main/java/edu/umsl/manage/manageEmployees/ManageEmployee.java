package edu.umsl.manage.manageEmployees;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import edu.umsl.manage.dto.Employee;

public class ManageEmployee {
	
	private static SessionFactory factory;
	
	public static void main(String[] args){
		Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
		
		StandardServiceRegistryBuilder serviceRegistryBuilder = new StandardServiceRegistryBuilder();
		serviceRegistryBuilder.applySettings(configuration.getProperties());
		
		ServiceRegistry serviceRegistry = serviceRegistryBuilder.build();
		
		factory = new Configuration().configure().buildSessionFactory(serviceRegistry);
		
		ManageEmployee ME = new ManageEmployee();
		
		//Add a few employees
		Integer empID1 = ME.addEmployee("Zara", "Ali", 1000);
	      Integer empID2 = ME.addEmployee("Daisy", "Das", 5000);
	}
	
	public Integer addEmployee(String fname, String lname, int salary){
	      Session session = factory.openSession();
	      Transaction tx = null;
	      Integer employeeID = null;
	      try{
	         tx = session.beginTransaction();
	         Employee employee = new Employee(fname, lname, salary);
	         employeeID = (Integer) session.save(employee); 
	         tx.commit();
	      }catch (HibernateException e) {
	         if (tx!=null) tx.rollback();
	         e.printStackTrace(); 
	      }finally {
	         session.close(); 
	      }
	      return employeeID;
	   }

}
