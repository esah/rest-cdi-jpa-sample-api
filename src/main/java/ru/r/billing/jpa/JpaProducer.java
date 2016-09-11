package ru.r.billing.jpa;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Any;
import javax.enterprise.inject.Default;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;

@Singleton
public class JpaProducer {
	@PersistenceUnit
	private EntityManagerFactory factory;
	@PersistenceUnit
	private EntityManager entityManager;

	@PostConstruct
	public void init() {
		if (factory == null) {
			factory = Persistence.createEntityManagerFactory("MainStore");
		}
	}

	@Produces
	@Default
	@RequestScoped
	public EntityManager entityManager() {
		return factory.createEntityManager();
	}

	public void close(@Disposes @Any EntityManager entityManager) {
		if (entityManager.isOpen()) {
			entityManager.close();
		}
	}
}
