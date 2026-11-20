package com.star.Ba.dao;

import java.math.BigInteger;
import java.util.List;

import com.star.Ba.model.QuittanceTerme;
import com.star.Ba.model.QuittanceVersementLibre;

public interface IQuittanceVersementLibreDAO {

	String FindMaxNumquit();
	void create(QuittanceVersementLibre quittance);
	QuittanceVersementLibre searchquit(int numquit);
	List<QuittanceVersementLibre> searchquitByNAdhesion(String nAdhesion);
	
	public QuittanceVersementLibre searchquitByNumquit (int numquit);
	
	public List<QuittanceVersementLibre> searchquitByManyNumquit (List<BigInteger> numquit);

}
