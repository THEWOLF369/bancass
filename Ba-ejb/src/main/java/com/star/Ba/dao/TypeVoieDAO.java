package com.star.Ba.dao;

import com.star.Ba.model.TypeVoie;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class TypeVoieDAO extends AbstractDAO<TypeVoie> implements ITypeVoieDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	public TypeVoieDAO() {
		super(TypeVoie.class);
	}
	
	public TypeVoieDAO(Class<TypeVoie> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

}
