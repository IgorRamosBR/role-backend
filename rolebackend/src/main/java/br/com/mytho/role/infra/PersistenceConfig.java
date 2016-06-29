package br.com.mytho.role.infra;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceConfig {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("sappg");
	public EntityManager getEntityManager(){
		return factory.createEntityManager();
	}
	
	public void closeEntityManager(EntityManager entity){
		entity.close();
	}
}
