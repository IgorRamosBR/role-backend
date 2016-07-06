package br.com.mytho.role.domain.model.dao;

import java.util.List;

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

	public List<Event> list() {
		return entityManager.createQuery("from Event", Event.class).getResultList();
	}
	
}
