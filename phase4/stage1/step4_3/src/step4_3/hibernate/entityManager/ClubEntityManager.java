package step4_3.hibernate.entityManager;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import junit.framework.TestCase;

public class ClubEntityManager {
	//
	private EntityManagerFactory entityManagerFactory;
	
	public EntityManagerFactory setUp() throws Exception{
		entityManagerFactory = Persistence.createEntityManagerFactory("step4_3.hibernate.jpa");
		return entityManagerFactory;
	}
	
	public void closeEntityManagerFactory() {
		//
		entityManagerFactory.close();
	}
}
