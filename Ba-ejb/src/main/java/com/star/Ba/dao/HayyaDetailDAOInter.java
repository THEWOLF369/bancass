package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.HayyaDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.BenefHayyaDeces;
import com.star.Ba.model.BenefHayyaVie;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.Proposition;

@Local
public interface HayyaDetailDAOInter {
	

	 
	 public DetailHayya find_detail_by_Adhesion(Adhesion adhesion);
	 
	 public List<DetailsEvolutionGarantie> find_details_ev_gar_by_Adhesion(Adhesion adhesion);
	 
	 public List<BenefHayyaVie> find_benef_vie_by_Adhesion(Adhesion adhesion);
	 public List<BenefHayyaDeces> find_benef_deces_by_Adhesion(Adhesion adhesion);
}
