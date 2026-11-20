package com.star.Ba.dao;

import com.star.Ba.model.Client;
import com.star.Ba.model.ProfessionClient;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class ProfessionClientDAO extends AbstractDAO<ProfessionClient> implements IProfessionClientDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	public ProfessionClientDAO() {
		super(ProfessionClient.class);
	}
	
	public ProfessionClientDAO(Class<ProfessionClient> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	
	
	
	
}
