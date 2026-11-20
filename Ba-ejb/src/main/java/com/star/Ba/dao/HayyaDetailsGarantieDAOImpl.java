package com.star.Ba.dao;

import javax.ejb.Local;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.Proposition;


public class HayyaDetailsGarantieDAOImpl extends AbstractDAO<DetailsGarantieHayya> implements HayyaDetailsGarantieDAOInter {

	
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public HayyaDetailsGarantieDAOImpl() {
		super(DetailsGarantieHayya.class);
	}
	
	
	public HayyaDetailsGarantieDAOImpl(Class<DetailsGarantieHayya> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected EntityManager getEntityManager() {
		// TODO Auto-generated method stub
		return em;
	}

	@Override
	public DetailsGarantieHayya createDetailsGarantieHayya(DetailsGarantieHayya detailsGarantieHayya,
			Proposition proposition) {
		// TODO Auto-generated method stub
		return null;
	}


	

	 
}
