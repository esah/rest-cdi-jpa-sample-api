package ru.r.billing.jpa;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

@Singleton
public class GenericDao {

	@Inject
	@PersistenceContext
	private EntityManager em;


	@Transactional
	public<T> T find(Class<T> entityClass, Serializable id) {
		return em.find(entityClass, id);
	}

}
