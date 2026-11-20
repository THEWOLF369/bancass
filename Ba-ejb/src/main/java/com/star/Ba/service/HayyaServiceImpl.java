package com.star.Ba.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.dao.HayyaDAOInter;
import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.HayyaDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.BenefHayyaDeces;
import com.star.Ba.model.BenefHayyaVie;
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.Proposition;


@Stateless
public class HayyaServiceImpl implements HayyaServiceInter {
	
	
	@EJB HayyaDAOInter hayyaDAOInter;

	
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	
	@Override
	public HayyaDTO createProposition(Proposition proposition, DetailHayya detailHayya, 
			List<DetailsGarantieHayya> detailsGarantieHayyas, 
			List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas,
			BilanFinancierHayya bilanFinancierHayya) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.createProposition(proposition, detailHayya, detailsGarantieHayyas, 
				detailsEvolutionGarantieHayyas, bilanFinancierHayya);
	}

	@Override
	public Adhesion createAdhesion(Proposition proposition, Adhesion adhesion, DetailHayya detailHayya, 
			List<DetailsGarantieHayya> detailsGarantieHayyas, List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas,
			List<Person> personnes, int type_vie,
			List<Person> personnes_deces, int type_deces,
			ModePaiementHayya paiementHayya, BilanFinancierHayya bilanFinancierHayya) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.createAdhesion(proposition, adhesion, detailHayya, detailsGarantieHayyas, detailsEvolutionGarantieHayyas,
				personnes, type_vie,
				personnes_deces, type_deces,
				paiementHayya, bilanFinancierHayya);
	}


	@Override
	public DetailHayya createDetailHayya(DetailHayya detailHayya, Proposition proposition) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.createDetailHayya(detailHayya, proposition);
	}


	@Override
	public List<BenefHayyaVie> createBenefVie(Proposition proposition, Adhesion adhesion, List<Person> personnes, int type) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.createBenefVie(proposition, adhesion, personnes, type);	
		}


	@Override
	public List<BenefHayyaDeces> createBenefDeces(Proposition proposition, Adhesion adhesion, List<Person> personnes,
			int type) {
		return hayyaDAOInter.createBenefDeces(proposition, adhesion, personnes, type);	
	}


	@Override
	public DetailHayya find_detail_by_Adhesion(Adhesion adhesion) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.find_detail_by_Adhesion(adhesion);
	}
	
	@Override
	public DetailHayya find_detail_by_Proposition(Proposition proposition) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.find_detail_by_Proposition(proposition);
	}


	@Override
	public List<DetailsEvolutionGarantieHayya> find_details_ev_gar_by_Adhesion(Adhesion adhesion) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.find_details_ev_gar_by_Adhesion(adhesion);
	}


	@Override
	public List<BenefHayyaVie> find_benef_vie_by_Adhesion(Adhesion adhesion) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.find_benef_vie_by_Adhesion(adhesion);
	}


	@Override
	public List<BenefHayyaDeces> find_benef_deces_by_Adhesion(Adhesion adhesion) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.find_benef_deces_by_Adhesion(adhesion);
	}


	@Override
	public List<DetailsGarantieHayya> find_detail_garatie_by_Adhesion(Adhesion adhesion) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.find_detail_garatie_by_Adhesion(adhesion);
	}


	
	@Override
	public BilanFinancierHayya find_bilan_financier_7ayya_by_Adhesion(Adhesion adhesion) {
		// TODO Auto-generated method stub
		return hayyaDAOInter.find_bilan_financier_7ayya_by_Adhesion(adhesion);
	}


}
