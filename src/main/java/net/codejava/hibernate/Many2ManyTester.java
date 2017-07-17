package net.codejava.hibernate;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

public class Many2ManyTester {
	public static void main(String[] args) {
		// loads configuration and mappings
		Configuration configuration = new Configuration().configure();
		ServiceRegistryBuilder registry = new ServiceRegistryBuilder();
		registry.applySettings(configuration.getProperties());
		ServiceRegistry serviceRegistry = registry.buildServiceRegistry();

		// builds a session factory from the service registry
		SessionFactory sessionFactory = configuration
				.buildSessionFactory(serviceRegistry);

		// obtains the session
		Session session = sessionFactory.openSession();
		session.beginTransaction();

//		testSaveAllNew(session);
//		testSaveWithExistingUserAndGroup(session);
		testDeleteUserGroup(session);


		session.getTransaction().commit();
		session.close();
	}

	private static void testSaveAllNew(Session session) {
		User user = new User("tommy", "ymmot", "tommy@gmail.com");
		Group group = new Group("Coders");
		
		UserGroup userGroup = new UserGroup();
		userGroup.setGroup(group);
		userGroup.setUser(user);
		userGroup.setActivated(true);
		userGroup.setRegisteredDate(new Date());
		
		session.save(userGroup);
	}
	
	private static void testSaveWithExistingUserAndGroup(Session session) {
		// this user is obtained from the database with ID 34
		User user = (User) session.get(User.class, new Long(2));

		// this group is obtained from the database with ID 17		
		Group group = (Group) session.get(Group.class, new Long(1));
		
		UserGroup userGroup = new UserGroup();
		userGroup.setGroup(group);
		userGroup.setUser(user);
		userGroup.setActivated(true);
		userGroup.setRegisteredDate(new Date());
				
		session.save(userGroup);		
	}
	
	private static void testDeleteUserGroup(Session session) {
		UserGroup userGroup = new UserGroup();
		userGroup.setId(2);
		session.delete(userGroup);
	}
}
