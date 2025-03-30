package dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.ObjectA;
import model.ObjectB;

public class MainClass {
	public static void main(String[] args) {
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("dev");
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		EntityTransaction entityTransaction = entityManager.getTransaction();

		ObjectB b1 = new ObjectB();
		b1.setName("ulli sagar");
		b1.setAge(23);

		ObjectB b2 = new ObjectB();
		b2.setName("suprith");
		b2.setAge(23);

		ObjectA a1 = new ObjectA();
		a1.setName("Shankar Narayan");
		a1.setSubject("Core Java");
		a1.setB(Arrays.asList(b1, b2));
		
		
		
		ObjectB b3 = new ObjectB();
		b3.setName("chetan");
		b3.setAge(23);

		ObjectB b4 = new ObjectB();
		b4.setName("shashank");
		b4.setAge(23);

		ObjectA a2 = new ObjectA();
		a2.setName("Shashank L");
		a2.setSubject("SQL");
		a2.setB(Arrays.asList(b3, b4));

//		------------------ Persist ------------------

		entityTransaction.begin();
		entityManager.persist(a1);
		entityTransaction.commit();

		entityTransaction.begin();
		entityManager.persist(a2);
		entityTransaction.commit();
		
//		------------------ Fetch ------------------

		ObjectA fetchA = entityManager.find(ObjectA.class, "Shankar Narayan");
		System.out.println(fetchA);

		ObjectB fetchB = entityManager.find(ObjectB.class, "ulli sagar");
		System.out.println(fetchB);

//		------------------ Update ------------------

		ObjectA updateA = entityManager.find(ObjectA.class, "Shankar Narayan");
		List<ObjectB> objectBList = new ArrayList<ObjectB>(a1.getB());
		Iterator<ObjectB> itr = objectBList.iterator();

		while (itr.hasNext()) {
			ObjectB objectB = itr.next();
			if (objectB.getName().equals("suprith")) {
				itr.remove();
			}
		}
		updateA.setB(objectBList);
		entityTransaction.begin();
		entityManager.merge(updateA);
		entityTransaction.commit();

		ObjectB updateB = entityManager.find(ObjectB.class, "ulli sagar");
		updateB.setAge(22);
		entityTransaction.begin();
		entityManager.merge(updateB);
		entityTransaction.commit();

//		------------------ Delete ------------------

		ObjectA deleteA = entityManager.find(ObjectA.class, "Shankar Narayan");
		entityTransaction.begin();
		entityManager.remove(deleteA);
		entityTransaction.commit();

//		-------------- Child Remove --------------------
		ObjectA deleteParent = entityManager.find(ObjectA.class, "Shashank L");
		deleteParent.setB(null);
		entityTransaction.begin();
		entityManager.merge(deleteParent);
		entityTransaction.commit();

		ObjectB deleteChild = entityManager.find(ObjectB.class, "chetan");
		entityTransaction.begin();
		entityManager.remove(deleteChild);
		entityTransaction.commit();

	}

}
