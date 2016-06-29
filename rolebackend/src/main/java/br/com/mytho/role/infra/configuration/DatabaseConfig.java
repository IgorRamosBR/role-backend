package br.com.mytho.role.infra.configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DatabaseConfig {
	private static EntityManagerFactory factory = Persistence.createEntityManagerFactory("sappg");
	public EntityManager getEntityManager(){
		return factory.createEntityManager();
	}
	
	public void closeEntityManager(EntityManager entity){
		entity.close();
	}
}
