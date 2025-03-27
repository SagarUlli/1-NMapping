package dao;

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

		entityTransaction.commit();

		ObjectB b1 = new ObjectB();
		b1.setName("ulli sagar");
		b1.setAge(23);

		ObjectB b2 = new ObjectB();
		b2.setName("suprith");
		b2.setAge(23);

		ObjectA a = new ObjectA();
		a.setName("Shankar Narayan");
		a.setSubject("Core Java");
		a.setB(Arrays.asList(b1, b2));

//		------------------ Persist ------------------

		entityTransaction.begin();
		entityManager.persist(a);
		entityTransaction.commit();

//		------------------ Fetch ------------------

		ObjectA fetchA = entityManager.find(ObjectA.class, "Shankar Narayan");
		System.out.println(fetchA);

		ObjectB fetchB = entityManager.find(ObjectB.class, "ulli sagar");
		System.out.println(fetchB);

//		------------------ Update ------------------

		ObjectA updateA = entityManager.find(ObjectA.class, "Shankar Narayan");
		List<ObjectB> objectBList = a.getB();
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
		updateB.setName("suprith");
		entityTransaction.begin();
		entityManager.merge(updateB);
		entityTransaction.commit();

//		------------------ Delete ------------------

		ObjectA deleteA = entityManager.find(ObjectA.class, "Shankar Narayan");
		entityTransaction.begin();
		entityManager.merge(deleteA);
		entityTransaction.commit();

//		-------------- Child Remove --------------------
		ObjectA deleteParent = entityManager.find(ObjectA.class, "Shankar Narayan");
		deleteParent.setB(null);
		entityTransaction.begin();
		entityManager.merge(entityManager);
		entityTransaction.commit();

		ObjectB deleteChild = entityManager.find(ObjectB.class, "ulli sagar");
		entityTransaction.begin();
		entityManager.remove(deleteChild);
		entityTransaction.commit();

	}

}
