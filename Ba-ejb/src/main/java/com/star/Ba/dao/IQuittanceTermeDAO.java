package com.star.Ba.dao;

import java.math.BigInteger;
import java.util.List;

import com.star.Ba.model.QuittanceTerme;

public interface IQuittanceTermeDAO {

	String FindMaxNumquit();
	void create(QuittanceTerme quittance);
	QuittanceTerme searchquit(int numquit);
	
	QuittanceTerme searchquit(BigInteger numquit);
	
	
	List<QuittanceTerme> searchquitByNAdhesion(String nAdhesion);
	
	
	List<QuittanceTerme> searchquitByNAdhesionNonEncaissed(String nAdhesion);
	
	public QuittanceTerme searchquitByNumquit (BigInteger numquit);
	
	public List<QuittanceTerme> searchquitByManyNumquit (List<BigInteger> numquit);
	
	public List<QuittanceTerme> searchquitByManyNumquitWithNonEncaissed (List<BigInteger> numquit, String nAdhesion);
	
	public QuittanceTerme  Merge(QuittanceTerme qtc);

}
