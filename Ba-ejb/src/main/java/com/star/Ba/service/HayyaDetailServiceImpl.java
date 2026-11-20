package com.star.Ba.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.dao.HayyaDAOInter;
import com.star.Ba.dao.HayyaDetailDAOInter;
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


@Stateless
public class HayyaDetailServiceImpl implements HayyaDetailServiceInter {
	
	
	@EJB HayyaDetailDAOInter hayyaDAOInter;

	
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	
	 
//	@Override
//	public DetailHayya find_detail_by_Adhesion(Adhesion adhesion) {
//		// TODO Auto-generated method stub
//		return hayyaDAOInter.find_detail_by_Adhesion(adhesion);
//	}
//
//
//	@Override
//	public List<DetailsEvolutionGarantie> find_details_ev_gar_by_Adhesion(Adhesion adhesion) {
//		// TODO Auto-generated method stub
//		return hayyaDAOInter.find_details_ev_gar_by_Adhesion(adhesion);
//	}
//
//
//	@Override
//	public List<BenefHayyaVie> find_benef_vie_by_Adhesion(Adhesion adhesion) {
//		// TODO Auto-generated method stub
//		return hayyaDAOInter.find_benef_vie_by_Adhesion(adhesion);
//	}
//
//
//	@Override
//	public List<BenefHayyaDeces> find_benef_deces_by_Adhesion(Adhesion adhesion) {
//		// TODO Auto-generated method stub
//		return hayyaDAOInter.find_benef_deces_by_Adhesion(adhesion);
//	}

}
