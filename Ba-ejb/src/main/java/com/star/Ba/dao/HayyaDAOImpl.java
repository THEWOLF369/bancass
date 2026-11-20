package com.star.Ba.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.HayyaDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.BenefHayyaDeces;
import com.star.Ba.model.BenefHayyaVie;
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Societe;


@Stateless
public class HayyaDAOImpl extends AbstractDAO<Client> implements HayyaDAOInter {

	
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public HayyaDAOImpl() {
		super(Client.class);
	}
	
	
	public HayyaDAOImpl(Class<Client> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	

	@Override
	public HayyaDTO createProposition(Proposition proposition, DetailHayya detailHayya, 
			List<DetailsGarantieHayya> detailsGarantieHayyas,
			List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas,
			BilanFinancierHayya bilanFinancierHayya) {
		
		HayyaDTO hayyaDTO = new HayyaDTO();
		
		DetailHayya newDetailHayya = new DetailHayya();
		
		List<DetailsGarantieHayya> newDetailsGarantieHayyas = new ArrayList<DetailsGarantieHayya>();
		
		List<DetailsEvolutionGarantieHayya> newDetailsEvolutionGarantieHayyas = new ArrayList<DetailsEvolutionGarantieHayya>();

		BilanFinancierHayya newBilanFinancierHayya = new BilanFinancierHayya();
		
		Client newClient	=	proposition.getClient();
		
		if (newClient.getDatCreation() == null) {
			newClient.setDatCreation(new Date());
		}
		
		Client client	=	em.merge(newClient);
        
    	
	      proposition.setClient(client);
	    	Proposition newProposition= em.merge(proposition);
	    	newProposition.setnProposition(newProposition.getNumeroProposition());
	    	newProposition.setDatFin(proposition.getDatFin());

	    	 em.merge(newProposition);
	    	 
	    	 detailHayya.setProposition(newProposition);
	    	 
	    	 newDetailHayya = em.merge(detailHayya);
	    	 
	    	 
	    	 bilanFinancierHayya.setProposition(newProposition);
	    	 newBilanFinancierHayya = em.merge(bilanFinancierHayya);
	    	 
	    	 
	    	 for(DetailsGarantieHayya detail : detailsGarantieHayyas) {
	    		 detail.setProposition(newProposition);
	    		 newDetailsGarantieHayyas.add(em.merge(detail));
	    	 }
	    	 
	    	 for(DetailsEvolutionGarantieHayya detail : detailsEvolutionGarantieHayyas) {
	    		 detail.setProposition(newProposition);
	    		 newDetailsEvolutionGarantieHayyas.add(em.merge(detail));
	    		 
	    	 }
	    	 
	    	 
	    	em.flush();
	    	
	    	
	    	hayyaDTO.setProposition(newProposition);
	    	hayyaDTO.setDetailHayya(newDetailHayya);
	    	hayyaDTO.setDetailsGarantieHayyas(newDetailsGarantieHayyas);
	    	hayyaDTO.setDetailsEvolutionGarantieHayyas(newDetailsEvolutionGarantieHayyas);
	    	hayyaDTO.setBilanFinancierHayya(newBilanFinancierHayya);
	    	
	    	
	   return hayyaDTO;
	}

	@Override
	public Adhesion createAdhesion(Proposition proposition, Adhesion adhesion, DetailHayya detailHayya, 
			List<DetailsGarantieHayya> detailsGarantieHayyas, List<DetailsEvolutionGarantieHayya> detailsEvolutionGarantieHayyas,
			List<Person> personnes, int type_vie,
			List<Person> personnes_deces, int type_deces,
			ModePaiementHayya paiementHayya, BilanFinancierHayya bilanFinancierHayy) {


		Client client	=	proposition.getClient();
		
		adhesion.setClient(client);
		adhesion.setProposition(proposition);
		
		Adhesion newAdhesion = em.merge(adhesion);
		newAdhesion.setNadhesion(newAdhesion.getNumeroAdhesion());
    	 

    	 em.merge(newAdhesion);
		
		detailHayya.setAdhesion(newAdhesion);
   	    em.merge(detailHayya);
   	    
   	    
   	 bilanFinancierHayy.setAdhesion(newAdhesion);
   	 em.merge(bilanFinancierHayy);
   	    
   	    
   	 for(DetailsGarantieHayya detail : detailsGarantieHayyas) {
		 detail.setAdhesion(newAdhesion);
		 em.merge(detail);
	 }
	 
	 for(DetailsEvolutionGarantieHayya detail : detailsEvolutionGarantieHayyas) {
		 detail.setAdhesion(newAdhesion);
		 em.merge(detail);
		 
	 }
	 
	 //benef cas vie 
	 
	 List<BenefHayyaVie> benefHayyaVies = new ArrayList<>();
		
	 for(Person personne : personnes) {
		 
		 BenefHayyaVie benefHayyaVie = new BenefHayyaVie();
		 benefHayyaVie.setAdhesion(newAdhesion);
		 benefHayyaVie.setPropositions(proposition);
		 benefHayyaVie.setNom(personne.getNom());
		 benefHayyaVie.setPrenom(personne.getPrenom());
		 benefHayyaVie.setCin(personne.getCin());
		 benefHayyaVie.setLien(personne.getLien());
		 benefHayyaVie.setQuote(personne.getQuote());
		 benefHayyaVie.setType(type_vie);
		 

		 benefHayyaVies.add(em.merge(benefHayyaVie));
	 }
	 
	 // benef cas deces
	 
	 List<BenefHayyaDeces> benefHayyaDecess = new ArrayList<>();
		
	 for(Person personne : personnes_deces) {
		 
		 BenefHayyaDeces benefHayyaDeces = new BenefHayyaDeces();
		 benefHayyaDeces.setAdhesion(newAdhesion);
		 benefHayyaDeces.setPropositions(proposition);
		 benefHayyaDeces.setNom(personne.getNom());
		 benefHayyaDeces.setPrenom(personne.getPrenom());
		 benefHayyaDeces.setCin(personne.getCin());
		 benefHayyaDeces.setLien(personne.getLien());
		 benefHayyaDeces.setQuote(personne.getQuote());
		 benefHayyaDeces.setType(type_deces);
		 

		 benefHayyaDecess.add(em.merge(benefHayyaDeces));
	 }
	 
	 
	 //mode de paiement
	 paiementHayya.setPropositions(proposition);
	 paiementHayya.setAdhesion(newAdhesion);
	 em.merge(paiementHayya);
	 
	 
	em.flush();
		
		return newAdhesion;
	}


	@Override
	public DetailHayya createDetailHayya(DetailHayya detailHayya, Proposition proposition) {

		DetailHayya newDetailHayya = detailHayya;
		newDetailHayya.setProposition(proposition);
		
		em.merge(newDetailHayya);
    	em.flush();
    	return newDetailHayya;
	}


	@Override
	public List<BenefHayyaVie> createBenefVie(Proposition proposition, Adhesion adhesion, List<Person> personnes, int type) {

		List<BenefHayyaVie> benefHayyaVies = new ArrayList<>();
		
		 for(Person personne : personnes) {
			 
			 BenefHayyaVie benefHayyaVie = new BenefHayyaVie();
			 benefHayyaVie.setAdhesion(adhesion);
			 benefHayyaVie.setPropositions(proposition);
			 benefHayyaVie.setNom(personne.getNom());
			 benefHayyaVie.setPrenom(personne.getPrenom());
			 benefHayyaVie.setCin(personne.getCin());
			 benefHayyaVie.setLien(personne.getLien());
			 benefHayyaVie.setQuote(personne.getQuote());
			 benefHayyaVie.setType(type);
			 

			 benefHayyaVies.add(em.merge(benefHayyaVie));
    	 }
		 
		 em.flush();
		 
		return benefHayyaVies;
	}
	
	
	@Override
	public List<BenefHayyaDeces> createBenefDeces(Proposition proposition, Adhesion adhesion, List<Person> personnes, int type) {

		List<BenefHayyaDeces> benefHayyaDecess = new ArrayList<>();
		
		 for(Person personne : personnes) {
			 
			 BenefHayyaDeces benefHayyaDeces = new BenefHayyaDeces();
			 benefHayyaDeces.setAdhesion(adhesion);
			 benefHayyaDeces.setPropositions(proposition);
			 benefHayyaDeces.setNom(personne.getNom());
			 benefHayyaDeces.setPrenom(personne.getPrenom());
			 benefHayyaDeces.setCin(personne.getCin());
			 benefHayyaDeces.setLien(personne.getLien());
			 benefHayyaDeces.setQuote(personne.getQuote());
			 benefHayyaDeces.setType(type);
			 

			 benefHayyaDecess.add(em.merge(benefHayyaDeces));
    	 }
		 
		 em.flush();
		 
		return benefHayyaDecess;
	}


	
	
	 @Override
	    public DetailHayya find_detail_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM DetailHayya a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<DetailHayya> list = q.getResultList();
	        return list.get(0);
	    }
	 
	 
	 @Override
	    public DetailHayya find_detail_by_Proposition(Proposition proposition) {
	        Query q = em.createQuery("SELECT a FROM DetailHayya a WHERE a.proposition = :proposition").setParameter("proposition", proposition);
	        List<DetailHayya> list = q.getResultList();
	        return list.get(0);
	    }
	 
	 
	 @Override
	    public List<DetailsEvolutionGarantieHayya> find_details_ev_gar_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM DetailsEvolutionGarantieHayya a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<DetailsEvolutionGarantieHayya> list = q.getResultList();
	        return list;
	    }
	 
	 
	 
	 @Override
	    public List<BenefHayyaVie> find_benef_vie_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM BenefHayyaVie a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<BenefHayyaVie> list = q.getResultList();
	        return list;
	    }
	 
	 
	 @Override
	    public List<BenefHayyaDeces> find_benef_deces_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM BenefHayyaDeces a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<BenefHayyaDeces> list = q.getResultList();
	        return list;
	    }


	@Override
	public List<DetailsGarantieHayya> find_detail_garatie_by_Adhesion(Adhesion adhesion) {
		 Query q = em.createQuery("SELECT a FROM DetailsGarantieHayya a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<DetailsGarantieHayya> list = q.getResultList();
	        return list;
	}
	
	
	
	 @Override
	    public BilanFinancierHayya find_bilan_financier_7ayya_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM BilanFinancierHayya a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<BilanFinancierHayya> list = q.getResultList();
	        return list.get(0);
	    }

	

}
