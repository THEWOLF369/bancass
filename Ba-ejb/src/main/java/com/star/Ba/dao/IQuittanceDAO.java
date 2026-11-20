package com.star.Ba.dao;

import java.util.Date;
import java.util.List;

import com.star.Ba.dto.QuittanceVLDTO;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.Utilisateurs;

public interface IQuittanceDAO {

	String FindMaxNumquit();
	void create(Quittance quittance);
	Quittance searchquit(int numquit);
	Quittance searchquitByNAdhesion(String nAdhesion);
	
	// cas 7ayya 
	Quittance searchQuitContratByNAdhesion(String nAdhesion, int numquit);
	 public String FindMinNumquit(String nAdhesion);
	 public List<Quittance> searchQuitByManyIdQuittance (List<Long> idQuittance);
	 
	 public List<Object[]> searchVLByAdhesions(List<Integer> idsUtilisateurs,
	            String annee1,
	            String annee2,
	            String nadhesion,
	            String cin,
	            String libelleProduit,
	            String dateEncDebut,
	            String dateEncFin,
	            String numQuit);

}
