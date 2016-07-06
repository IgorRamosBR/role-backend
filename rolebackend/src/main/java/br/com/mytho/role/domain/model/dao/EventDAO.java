package br.com.mytho.role.domain.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.mytho.role.domain.model.Event;

@Repository
public class EventDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void create(Event event) {
		entityManager.persist(event);
	}
	
}
