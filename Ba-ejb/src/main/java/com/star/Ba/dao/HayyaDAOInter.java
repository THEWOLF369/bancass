package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

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

@Local
public interface HayyaDAOInter {
	
	public HayyaDTO createProposition(Proposition proposition, DetailHayya detailHayya, 
			List<DetailsGarantieHayya> detailsGarantieHayyas, 
			List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas,
			BilanFinancierHayya bilanFinancierHayya);
	 
	public Adhesion createAdhesion(Proposition proposition, Adhesion adhesion, DetailHayya detailHayya, 
				List<DetailsGarantieHayya> detailsGarantieHayyas, List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas,
				List<Person> personnes, int type_vie,
				List<Person> personnes_deces, int type_deces,
				ModePaiementHayya paiementHayya, BilanFinancierHayya bilanFinancierHayy);
	 
	 public DetailHayya createDetailHayya(DetailHayya detailHayya, Proposition proposition);
	 
	 
	 public List<BenefHayyaVie> createBenefVie(Proposition proposition, Adhesion adhesion, List<Person> personnes, int type);
	 
	 public List<BenefHayyaDeces> createBenefDeces(Proposition proposition, Adhesion adhesion, List<Person> personnes, int type);

	 
	 
	 public DetailHayya find_detail_by_Adhesion(Adhesion adhesion);
	 public DetailHayya find_detail_by_Proposition(Proposition proposition);
	 
	 
	 public List<DetailsGarantieHayya> find_detail_garatie_by_Adhesion(Adhesion adhesion);
	 
	 public List<DetailsEvolutionGarantieHayya> find_details_ev_gar_by_Adhesion(Adhesion adhesion);
	 
	 public List<BenefHayyaVie> find_benef_vie_by_Adhesion(Adhesion adhesion);
	 public List<BenefHayyaDeces> find_benef_deces_by_Adhesion(Adhesion adhesion);
	 
	 
	 public BilanFinancierHayya find_bilan_financier_7ayya_by_Adhesion(Adhesion adhesion);
}
