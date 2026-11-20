package com.star.Ba.dao;

import javax.ejb.Local;

import com.star.Ba.model.OperationTransaction;


@Local
public interface IOperationTransactionDAO {
	
	void edit(OperationTransaction operationTransaction);
	void create(OperationTransaction operationTransaction);

}
