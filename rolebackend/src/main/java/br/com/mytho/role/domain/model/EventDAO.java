package br.com.mytho.role.domain.model;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class EventDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
}
