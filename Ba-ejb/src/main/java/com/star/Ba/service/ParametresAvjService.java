/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.service;


import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.AvenirJeunesseDAOInter;
import com.star.Ba.dao.IConventionDAO;
import com.star.Ba.dao.IParamGarantieAvjDAO;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.ISocieteDAO;
import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.AgeServiceBourse;
import com.star.Ba.model.Beneficiaire;
import com.star.Ba.model.Client;
import com.star.Ba.model.DetailAvj;
import com.star.Ba.model.DetailsEvolutionGarantieAVJ;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieAVJ;
import com.star.Ba.model.DureServiceBourse;
import com.star.Ba.model.Fractionnement;
import com.star.Ba.model.GarantieAvj;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.TauxCroissancePrime;
import com.star.Ba.model.TauxInteret;
import com.star.Ba.model.Utilisateurs;

/**
 *
 * @author star
 */
@Stateless
public class ParametresAvjService extends AbstractDAO<Client> implements IParametresAvjService {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@EJB
	private IParamGarantieAvjDAO iParamGarantieAvjDAO;
	@EJB 
	AvenirJeunesseDAOInter avenirJeunesseDAOIService;
	@EJB
	private IPoliceDAO policeDAO;
	@EJB
	private IConventionDAO conventionDAO;
	@EJB
	private ISocieteDAO societeDAO;

	public ParametresAvjService() {
		super(Client.class);
	}

	public ParametresAvjService(Class<Client> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}



	@Override
	public List<Fractionnement> allFractionnements() {
		// TODO Auto-generated method stub
		return avenirJeunesseDAOIService.allFractionnements();
	}
	@Override
	public List<DureServiceBourse> allDureServiceBourses() {
		// TODO Auto-generated method stub
		return avenirJeunesseDAOIService.allDureServiceBourses();
	}
	@Override
	public List<TauxCroissancePrime> allTauxCroissancePrimes() {
		// TODO Auto-generated method stub
		return avenirJeunesseDAOIService.allTauxCroissancePrimes();
	}
	@Override
	public List<AgeServiceBourse> allAgeServiceBourses() {
		// TODO Auto-generated method stub
		return avenirJeunesseDAOIService.allAgeServiceBourses();
	}
	@Override
	public List<TauxInteret> allTauxInteret() {
		// TODO Auto-generated method stub
		return avenirJeunesseDAOIService.allTauxInteret();
	}
	@Override
	public TauxInteret getTauxInteretByAnnee(long annee) {
		// TODO Auto-generated method stub
		return avenirJeunesseDAOIService.getTauxInteretByAnnee(annee);
	}
	@Override
	public Proposition saveProposition(Beneficiaire beneficiaire,Adherent adherent,Client client, Adhesion adhesion ,GarantieAvj garantieAvj1, GarantieAvj garantieAvj2, GarantieAvj garantieAvj3, GarantieAvj garantieAvj4, GarantieAvj garantieAvj5, 
			Double fraisInscriptionAnnuel, Double renteMonsuelleEnCasDeces,	Utilisateurs utilisateurs,
			List<DetailsEvolutionGarantieAVJ> detailsEvolutionGarantiesAVJ, 
			List<DetailsGarantieAVJ> detailsGarantieAVJs) {
		Proposition proposition = new Proposition();
		proposition.setPolice(adhesion.getPolice());
		proposition.setClient(adhesion.getClient());
		proposition.setPoliceCredit(adhesion.getPoliceCredit());


		proposition.setDatDebut(adhesion.getDatDebut());
		proposition.setDatFin(adhesion.getDatFin());
		proposition.setDuree(adhesion.getDuree());

		proposition.setFraPolice(adhesion.getFraPolice());
		proposition.setDurPaiemt(adhesion.getDurPaiemt());

		proposition.setEtat(adhesion.getEtat());
		proposition.setDatMaj(adhesion.getDatMaj());
		proposition.setTauxPrime(adhesion.getTauxPrime());

		proposition.setMtPrime(adhesion.getMtPrime());

		proposition.setNombrePersonne(adhesion.getNombrePersonne());
		proposition.setDestination(adhesion.getDestination());
		proposition.setTelphone(adhesion.getTelphone());
		proposition.setTypeAbonnement(adhesion.getTypeAbonnement());

		proposition.setTauxInter(adhesion.getTauxInter());
		proposition.setCout(adhesion.getCout());
		proposition.setPrimetot(adhesion.getPrimetot());

		proposition.setDatcreation(new Date());
		if (client.getIdClient() == 0) {
			client.setDatCreation(new Date());
		}

		proposition.setClient(client);
 
		proposition.setIdUtilisateurs(utilisateurs.getIdUtilisateurs());

		proposition.setDetailAvj(adhesion.getDetailAvj());


		return createProposition( beneficiaire,adherent,proposition,fraisInscriptionAnnuel , 
				renteMonsuelleEnCasDeces,  garantieAvj1,  garantieAvj2,  garantieAvj3,  garantieAvj4,  
				garantieAvj5,
				detailsEvolutionGarantiesAVJ, detailsGarantieAVJs);
	}


	public Proposition createProposition(Beneficiaire beneficiaire,Adherent adherent,Proposition proposition,
			double fraisInscri ,double rente, GarantieAvj garantieAvj1, GarantieAvj garantieAvj2, 
			GarantieAvj garantieAvj3, GarantieAvj garantieAvj4, GarantieAvj garantieAvj5,
			List<DetailsEvolutionGarantieAVJ> detailsEvolutionGarantiesAVJ, 
			List<DetailsGarantieAVJ> detailsGarantieAVJs) {

		Client client	=	em.merge(proposition.getClient());
		proposition.setClient(client);

		DetailAvj detailAvj = em.merge(proposition.getDetailAvj());
		proposition.setDetailAvj(detailAvj);

		Proposition newProposition= em.merge(proposition);
		newProposition.setnProposition(newProposition.getNumeroProposition());
		em.merge(newProposition);
		CreateGarantieAVj( newProposition,fraisInscri , rente,  garantieAvj1,  garantieAvj2,  garantieAvj3,  garantieAvj4,  garantieAvj5);
		adherent.setPropositions(newProposition.getIdProposition());
		adherent.setDateCreation(new Date());
		adherent.setClient(client);
		em.merge(adherent);
		beneficiaire.setPropositions(newProposition);
		em.merge(beneficiaire);
		
		 for(DetailsEvolutionGarantieAVJ detail : detailsEvolutionGarantiesAVJ) {
			 detail.setProposition(newProposition);
			 em.merge(detail);
			 
		 }
		 
		 for(DetailsGarantieAVJ detail : detailsGarantieAVJs) {
			 detail.setProposition(newProposition);
			 em.merge(detail);
			 
		 }
		
		
		return newProposition;
	}

	@Override
	public DetailAvj create_detail_avj(BigDecimal MtBourse,BigDecimal AgeServiceBourse,
			BigDecimal DureeBourse,Date DatePremierBourse,BigDecimal IdTauxCroissancePrime,
			BigDecimal tauxCroissanceBourse, BigDecimal surprime, Boolean fromWs,
			BigDecimal dtranche)
	{
		DetailAvj detailAvj =new DetailAvj();
		detailAvj.setMtBourse(MtBourse);
		detailAvj.setAgeServiceBourse(AgeServiceBourse);
		detailAvj.setDureeBourse(DureeBourse);
		detailAvj.setDatePremierBourse(DatePremierBourse);
		detailAvj.setIdTauxCroissancePrime(IdTauxCroissancePrime);
		detailAvj.setTauxCroissanceBourse(tauxCroissanceBourse);
		detailAvj.setSurprime(surprime);
		detailAvj.setClientWs(fromWs);
		detailAvj.setDtranche(dtranche);
		return detailAvj;
	}



	public void CreateGarantieAVj(Proposition proposition,double fraisInscri ,double rente, GarantieAvj garantieAvj1, GarantieAvj garantieAvj2, GarantieAvj garantieAvj3, GarantieAvj garantieAvj4, GarantieAvj garantieAvj5)
	{



		garantieAvj1.setGarantie(iParamGarantieAvjDAO.find(1));
		System.out.println("garantieAvj1="+iParamGarantieAvjDAO.find(1));
		System.out.println("garantieAvj2="+iParamGarantieAvjDAO.find(2));
		garantieAvj2.setGarantie(iParamGarantieAvjDAO.find(2));
		garantieAvj1.setProposition(proposition);
		garantieAvj2.setProposition(proposition);
		System.out.println("garantieAvj1="+garantieAvj1);	
		System.out.println("garantieAvj2="+garantieAvj2);	
		em.merge(garantieAvj1);
		em.merge(garantieAvj2);

		System.out.println("fraisInscri="+fraisInscri);
		System.out.println("rente="+rente);

		if(fraisInscri !=0){

			garantieAvj3.setGarantie(iParamGarantieAvjDAO.find(3));
			garantieAvj4.setGarantie(iParamGarantieAvjDAO.find(4));
			garantieAvj3.setProposition(proposition);
			garantieAvj4.setProposition(proposition);
			System.out.println("garantieAvj3="+garantieAvj3);	
			System.out.println("garantieAvj4="+garantieAvj4);	

			em.merge(garantieAvj3);
			em.merge(garantieAvj4); 

		}

		if(rente !=0){
			garantieAvj5.setGarantie(iParamGarantieAvjDAO.find(5));
			garantieAvj5.setProposition(proposition);
			System.out.println("garantieAvj5="+garantieAvj5);	
			em.merge(garantieAvj5);
		}


	}

	@Override
	public Police getnumplcbylibelle(String cod_societ,String numplc)
	{

		List<Police> list=null;	

		list=policeDAO.getPolice_by_conv_libelle(conventionDAO.find_conv_by_Societ(societeDAO.find(cod_societ)), numplc);

		return list.get(0);

	}


	public void CreateGarantieAVjAdhesion(Adhesion adhesion,double fraisInscri ,double rente, GarantieAvj garantieAvj1, GarantieAvj garantieAvj2, GarantieAvj garantieAvj3, GarantieAvj garantieAvj4, GarantieAvj garantieAvj5)
	{



		garantieAvj1.setGarantie(iParamGarantieAvjDAO.find(1));
		System.out.println("garantieAvj1="+iParamGarantieAvjDAO.find(1));
		System.out.println("garantieAvj2="+iParamGarantieAvjDAO.find(2));
		garantieAvj2.setGarantie(iParamGarantieAvjDAO.find(2));
		garantieAvj1.setAdhesion(adhesion);
		garantieAvj2.setAdhesion(adhesion);
		System.out.println("garantieAvj1="+garantieAvj1);	
		System.out.println("garantieAvj2="+garantieAvj2);	
		em.merge(garantieAvj1);
		em.merge(garantieAvj2);

		System.out.println("fraisInscri="+fraisInscri);
		System.out.println("rente="+rente);

		if(fraisInscri !=0){

			garantieAvj3.setGarantie(iParamGarantieAvjDAO.find(3));
			garantieAvj4.setGarantie(iParamGarantieAvjDAO.find(4));
			garantieAvj3.setAdhesion(adhesion);
			garantieAvj4.setAdhesion(adhesion);
			System.out.println("garantieAvj3="+garantieAvj3);	
			System.out.println("garantieAvj4="+garantieAvj4);	

			em.merge(garantieAvj3);
			em.merge(garantieAvj4); 

		}

		if(rente !=0){
			garantieAvj5.setGarantie(iParamGarantieAvjDAO.find(5));
			garantieAvj5.setAdhesion(adhesion);
			System.out.println("garantieAvj5="+garantieAvj5);	
			em.merge(garantieAvj5);
		}


	}

	@Override
	public Adhesion createAdhesion(Beneficiaire beneficiaire,Adherent adherent, Adhesion adhesion,
			double fraisInscri ,double rente, GarantieAvj garantieAvj1, GarantieAvj garantieAvj2, 
			GarantieAvj garantieAvj3, GarantieAvj garantieAvj4, GarantieAvj garantieAvj5, 
			Utilisateurs utilisateurs,
			List<DetailsEvolutionGarantieAVJ> detailsEvolutionGarantiesAVJ,
			List<DetailsGarantieAVJ> detailsGarantieAVJs,
			ModePaiementHayya paiementHayya) {
		System.out.println("***********************************createAdhesion");
		System.out.println("client="+adhesion.getClient());
		DetailAvj detailAvj = em.merge(adhesion.getDetailAvj());
		adhesion.setDetailAvj(detailAvj);
		Client client=em.merge(adhesion.getClient());
		
		//	    	if(! adhesion.getAdherents().isEmpty()||adhesion.getAdherents()!=null){
		//	    		for (int i = 0; i <  adhesion.getAdherents().size(); i++) {
		//	    			adhesion.getAdherents().get(i).setClient(client);
		//				}
		//	    		System.out.println("adhesion.getAdherents()="+adhesion.getAdherents());
		//	    		 		
		//	    	}

		adhesion.setIdUtilisateurs(utilisateurs.getIdUtilisateurs());
		adhesion.setClient(client);
		adhesion.setDatcreation(new Date());
		adhesion.setDetailAvj(detailAvj);
		if(adhesion.getProposition()!=null){
			Proposition proposition=em.merge(adhesion.getProposition());
			adhesion.setProposition(proposition);
		}
		Adhesion newAdhesion=em.merge(adhesion);
		System.out.println("newAdhesion="+newAdhesion);
		newAdhesion.setNadhesion(newAdhesion.getNumeroAdhesion());
		em.merge(newAdhesion);
		CreateGarantieAVjAdhesion( newAdhesion,fraisInscri , rente,  garantieAvj1,  garantieAvj2,  garantieAvj3,  garantieAvj4,  garantieAvj5);
		adherent.setAdhesion(newAdhesion);
		adherent.setDateCreation(new Date());
		adherent.setClient(client);
		em.merge(adherent);
		beneficiaire.setAdhesion(newAdhesion);
		em.merge(beneficiaire);

		 for(DetailsEvolutionGarantieAVJ detail : detailsEvolutionGarantiesAVJ) {
			 detail.setAdhesion(newAdhesion);
			 em.merge(detail);
			 
		 }
		 
		 for(DetailsGarantieAVJ detail : detailsGarantieAVJs) {
			 detail.setAdhesion(newAdhesion);
			 em.merge(detail);
			 
		 }
		 
		 //mode de paiement
		 paiementHayya.setPropositions(newAdhesion.getProposition());
		 paiementHayya.setAdhesion(newAdhesion);
		 em.merge(paiementHayya);
		 
		return  newAdhesion;
	}	


}
