package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.NatureRisq;

public interface INatureRisqDAO {

	 
	   void create(NatureRisq natureRisq);

	    void edit(NatureRisq natureRisq);

	    void remove(NatureRisq natureRisq);

	    NatureRisq find(Object id);

	    List<NatureRisq> findAll();

	    List<NatureRisq> findRange(int[] range);

	    int count();

		List<NatureRisq> listeNaturRisq();
		
		public NatureRisq  createNewNatureRisq(NatureRisq natureRisq);
		
        

}
