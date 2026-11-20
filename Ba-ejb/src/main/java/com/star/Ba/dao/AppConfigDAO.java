package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.model.AppConfig;


@Stateless
public class AppConfigDAO extends AbstractDAO<AppConfig> implements IAppConfigDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public AppConfigDAO(){
		super(AppConfig.class);
	}
	
	
	public AppConfigDAO(Class<AppConfig> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}


//	@Override
//	public String getConfigValue(String code) {
//		 Query q = em.createQuery(
//			        "SELECT a FROM AppConfig a WHERE a.code = :code")
//			        .setParameter("code", code);
//
//			    List<AppConfig> list = q.getResultList();
//
//			    if (list == null || list.isEmpty()) {
//			        return null;
//			    } else {
//			        return list.get(0).getValue();
//			    }
//	}
	
	@Override
	public String getConfigValue(String code) {
	    try {
	        return em.createQuery(
	                "SELECT a.value FROM AppConfig a WHERE a.code = :code", String.class)
	                .setParameter("code", code)
	                .getSingleResult();
	    } catch (NoResultException e) {
	        return null;
	    }
	}


	@Override
	public void edit(AppConfig entity) {
		em.merge(entity);
		
	}


	@Override
	public void create(AppConfig entity) {
		em.persist(entity);
		
	}

 
	
	
	
}
