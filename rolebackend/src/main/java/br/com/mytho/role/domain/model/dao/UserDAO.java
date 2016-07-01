package br.com.mytho.role.domain.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.mytho.role.domain.model.User;

@Repository
public class UserDAO {
	
	@PersistenceContext
	private EntityManager em;
	
	public void create(User user) {
		em.persist(user);
	}

	public User findBy(String email) {
		return em.createQuery("select u from User u where u.email = :pEmail", User.class)
				 .setParameter("pEmail", email)
				 .getSingleResult();
	}
	
}
