package com.star.Ba.dao;

import javax.ejb.Local;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.Proposition;

@Local
public interface HayyaDetailsGarantieDAOInter {
	

	 
	 public DetailsGarantieHayya createDetailsGarantieHayya(DetailsGarantieHayya detailsGarantieHayya, Proposition proposition);

}
