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
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class AvenirJeunesseDAOImpl   implements AvenirJeunesseDAOInter{
	
	

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	public List<Fractionnement> allFractionnements() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM Fractionnement p").getResultList();	}

	@Override
	public List<DureServiceBourse> allDureServiceBourses() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM DureServiceBourse p").getResultList();
	}

	@Override
	public List<TauxCroissancePrime> allTauxCroissancePrimes() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM TauxCroissancePrime p").getResultList();	}

	@Override
	public List<AgeServiceBourse> allAgeServiceBourses() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM AgeServiceBourse p").getResultList();	
	}

	@Override
	public List<TauxInteret> allTauxInteret() {
		// TODO Auto-generated method stub
		return em.createQuery( "SELECT p FROM TauxInteret p").getResultList();	
	}
	@Override
	public TauxInteret getTauxInteretByAnnee(long annee) {
		// TODO Auto-generated method stub
		return (TauxInteret) em.createQuery( "SELECT p FROM TauxInteret p WHERE p.annee = :annee").setParameter("annee", annee).getSingleResult();	
	}
 
	
	 @Override
	    public List<DetailsEvolutionGarantieAVJ> find_details_ev_gar_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM DetailsEvolutionGarantieAVJ a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<DetailsEvolutionGarantieAVJ> list = q.getResultList();
	        return list;
	    }
	 
	 
	 @Override
	    public List<DetailsEvolutionGarantieAVJ> find_details_ev_gar_by_Proposition(Proposition proposition) {
	        Query q = em.createQuery("SELECT a FROM DetailsEvolutionGarantieAVJ a WHERE a.proposition = :proposition").setParameter("proposition", proposition);
	        List<DetailsEvolutionGarantieAVJ> list = q.getResultList();
	        return list;
	    }
	 
	 
	 
	 @Override
	    public List<DetailsGarantieAVJ> find_details_gar_by_Proposition(Proposition proposition) {
	        Query q = em.createQuery("SELECT a FROM DetailsGarantieAVJ a WHERE a.proposition = :proposition").setParameter("proposition", proposition);
	        List<DetailsGarantieAVJ> list = q.getResultList();
	        return list;
	    }
	 
	
}
