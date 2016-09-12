package ru.r.billing.jpa;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.apache.deltaspike.jpa.api.transaction.Transactional;
import ru.r.billing.ex.NotFoundException;

@Singleton
public class GenericDao {

	@Inject
	@PersistenceContext
	private EntityManager em;


	public <T> T find(Class<T> entityClass, Serializable id) {
		return em.find(entityClass, id);
	}

	public <T> T load(Class<T> entityClass, Serializable id) {
		final T result = em.find(entityClass, id);
		if (result == null) {
			throw new NotFoundException(
					String.format("%s %s", entityClass.getSimpleName(), id));
		}
		return result;
	}

	@Transactional
	public <T> T saveOrUpdate(T entity) {
		return em.merge(entity);
	}

}
