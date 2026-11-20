package com.star.Ba.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.model.OperationTransaction;


@Stateless
public class OperationTransactionDAO extends AbstractDAO<OperationTransaction> implements IOperationTransactionDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public OperationTransactionDAO(){
		super(OperationTransaction.class);
	}
	
	
	public OperationTransactionDAO(Class<OperationTransaction> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}

	
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	
	
}
