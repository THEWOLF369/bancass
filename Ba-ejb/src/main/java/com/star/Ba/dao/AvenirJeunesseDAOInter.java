package com.star.Ba.dao;



import java.util.List;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.AgeServiceBourse;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.DetailsEvolutionGarantieAVJ;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieAVJ;
import com.star.Ba.model.DureServiceBourse;
import com.star.Ba.model.Duree;
import com.star.Ba.model.Fractionnement;
import com.star.Ba.model.ParamGarantieAvj;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Societe;
import com.star.Ba.model.TauxCroissancePrime;
import com.star.Ba.model.TauxInteret;
import com.star.Ba.model.TypeAbonnement;
import com.star.Ba.model.Utilisateurs;

import javax.ejb.Local;

@Local
public interface AvenirJeunesseDAOInter {
	public  List<Fractionnement> allFractionnements();
	public List<DureServiceBourse> allDureServiceBourses();
	public List<TauxCroissancePrime> allTauxCroissancePrimes();
	public List<AgeServiceBourse> allAgeServiceBourses();
	public List<TauxInteret> allTauxInteret();
	public TauxInteret getTauxInteretByAnnee(long annee);
	
	public List<DetailsEvolutionGarantieAVJ> find_details_ev_gar_by_Adhesion(Adhesion adhesion);
	
	public List<DetailsEvolutionGarantieAVJ> find_details_ev_gar_by_Proposition(Proposition proposition);
	
	
	public List<DetailsGarantieAVJ> find_details_gar_by_Proposition(Proposition proposition);

	 
 
	 
	
}
