package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Quittance;
import com.star.Ba.model.QuittanceDetails;

public interface IQuittanceDetailsDAO {


	void createQuittanceDetails(QuittanceDetails quittancedetails, Quittance quittance);
	
	void create(QuittanceDetails quittancedetails);
	
	public List<QuittanceDetails> searchByNAdhesion (String nAdhesion, String typequit);
	
	public QuittanceDetails  Merge(QuittanceDetails qtc);
	
	public QuittanceDetails searchDetailQuit (long idQuittance);
	
	public QuittanceDetails searchDetailQuit_not_encaissed (String nAdhesion, String typequit, String etat);


}
