package com.star.Ba.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.model.ApiResponseEntity;
import com.star.Ba.model.OperationTransaction;


@Stateless
public class ApiResponseEntityDAO extends AbstractDAO<ApiResponseEntity> implements IApiResponseEntityDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public ApiResponseEntityDAO(){
		super(ApiResponseEntity.class);
	}
	
	
	public ApiResponseEntityDAO(Class<ApiResponseEntity> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

 
	
	
	
}
