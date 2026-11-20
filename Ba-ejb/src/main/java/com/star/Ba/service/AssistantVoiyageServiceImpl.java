package com.star.Ba.service;



import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.star.Ba.dao.AssistantVoiyagetDAOInter;
import com.star.Ba.dao.IPoliceDAO;
import com.star.Ba.dao.IQuittanceDAO;
import com.star.Ba.dao.QuittanceDAO;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Duree;
//import com.star.Ba.model.GarantieAvenirJeunesse;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.Societe;
import com.star.Ba.model.TarifAssist;
import com.star.Ba.model.TypeAbonnement;
import com.star.Ba.model.Utilisateurs;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class AssistantVoiyageServiceImpl  implements AssistantVoiyagetServiceInter {

	@EJB AssistantVoiyagetDAOInter assistantVoiyageService;
	@EJB IQuittanceDAO iQuittanceDAO;

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public List<TypeAbonnement> getAllTypeAbonnement(){
		
		return assistantVoiyageService.getAllTypeAbonnement();
	}
	
	
	
	@Override
	public Client findByIdentifiant(String identifiant) {
		
		return null;
	}

	
	@Override
	public void edit(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Client client) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Client saveOrUpdate(Client client) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Duree> getAllDurees() {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getAllDurees();
	}



	@Override
	public Double calculePrimeAssistantVoiyage(long duree, double typeAbonnement) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.calculePrimeAssistantVoiyage(duree, typeAbonnement);
	}

	@Override
	public List<Police> getPolice_by_conv(Convention convention) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getPolice_by_conv( convention);
	}

	

	@Override
	public List<Police> getPolice_by_prod(String codProdui) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getPolice_by_prod(codProdui);
	}

	@Override
	public Convention getConventionBySociete(Societe societe) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getConventionBySociete(societe);
	}

	@Override
	public Proposition createProposition(Proposition proposition) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.createProposition(proposition);
	}

	@Override
	public Adhesion createAdhesion(Adhesion adhesion) {
		// TODO Auto-generated method stub
		
		return assistantVoiyageService.createAdhesion(adhesion);
	}
	
	@Override
	public Adhesion createAdhesionTLS(Adhesion adhesion,String numpol) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.createAdhesionTLS(adhesion,numpol);
	}
	
	

	@Override
	public List<Police> getPolice_by_conv_prod(Convention convention,
			Produit codProdui) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getPolice_by_conv_prod(convention, codProdui);
				}



	@Override
	public List<Produit> getAllProduits() {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getAllProduits();
	}



	@Override
	public Produit getProduitByCode(String code) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getProduitByCode(code);
	}



//	@Override
//	public List<GarantieAvenirJeunesse> getAllGarantieAvenirJeunesses() {
//		// TODO Auto-generated method stub
//		return assistantVoiyageService.getAllGarantieAvenirJeunesses();
//	}



	@Override
	public List<Profil> getAllProfiles() {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getAllProfiles();
	}



	@Override
	public List<Profil> getAllChildrensProfiles(List<String> childrenCodes) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getAllChildrensProfiles(childrenCodes);
	}

	@Override
	public List<Profil> getAllChildrensProfilesbna() {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getAllChildrensProfilesbna();
	}


	@Override
	public List<Utilisateurs> getAllUsersChildren(List<Profil> codeProfile,Agence agence, Societe societe) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getAllUsersChildren(codeProfile, agence,societe);
	}



	@Override
	public List<Agence> getAllAgencesBySociete(Societe societe) {
		// TODO Auto-generated method stub
		return assistantVoiyageService.getAllAgencesBySociete(societe);
	}



	@Override
	public Agence getPrincipaleAgence(Societe societe) {
		
		return assistantVoiyageService.getPrincipaleAgence(societe);
	}


	
	@Override
	public TarifAssist calculePrimeAssistantVoiyageTLS(Long duree, double typeAbonnement,Police p,double age) {
		
		String pol=p.getNPolice();
		BigDecimal dure = new BigDecimal(duree);
		
		BigDecimal type=new BigDecimal(typeAbonnement);
		
		
		
		Query query = em.createQuery("SELECT a FROM TarifAssist a WHERE a.idDuree = :dure  and a.idTypeAbonnement = :type and a.police = :pol and a.agemin < :age AND a.agemax >= :age").setParameter("dure", dure).setParameter("type", type).setParameter("pol", pol).setParameter("age", age);
		
		 
		 List<TarifAssist> list = query.getResultList();
	        if (list == null || list.isEmpty()) {

	            return null;
	        } else {

	            return list.get(0);
	        }
		
	   
	}

@Override
	public String getNumeroQuit() {
		Integer currentNomber ;
		Calendar c = Calendar.getInstance();
		String currentyear=String.valueOf(c.getInstance().get(c.YEAR));
		System.out.println("current year="+currentyear);
		String structure="00000";
		String quit=iQuittanceDAO.FindMaxNumquit();
		
		if(quit.equals(0))
		{
			currentNomber=1;
		}
		else{
			String year=String.valueOf(quit).substring(0, 4);
			System.out.println("year="+(String.valueOf(quit).substring(0, 4)));
			if(year.equals(currentyear) )
			{
			currentNomber=Integer.parseInt((String.valueOf(quit).substring(4, 9)))+1;
			System.out.println("currentNomber="+currentNomber);
			}
			else{
				currentNomber=1;
			}
		}
		String number = currentNomber.toString();
		String newNomber =currentyear+ structure.substring(0, structure.length() - number.length())	+ number;
		return newNomber;
	}

@Override
public Proposition UpdateProposition(Proposition proposition) {
	//*
	// TODO Auto-generated method stub
	return assistantVoiyageService.UpdateProposition(proposition);
}

//@Override
//public String getNumeroQuit() {
//	Quittance quit=new Quittance();
//	Integer currentNomber ;
//	Calendar c = Calendar.getInstance();
//	String currentyear=String.valueOf(c.getInstance().get(c.YEAR));
//	System.out.println("current year="+currentyear);
//	String structure="00000";
//	quit=iQuittanceDAO.FindMaxNumquit();
//	
//	if(quit == null)
//	{
//		currentNomber=1;
//	}
//	else{
//		String year=String.valueOf(quit.getNumquit()).substring(0, 4);
//		System.out.println("year="+(String.valueOf(quit.getNumquit()).substring(0, 4)));
//		if(year.equals(currentyear) )
//		{
//		currentNomber=Integer.parseInt((String.valueOf(quit.getNumquit()).substring(4, 9)))+1;
//		System.out.println("currentNomber="+currentNomber);
//		}
//		else{
//			currentNomber=1;
//		}
//	}
//	String number = currentNomber.toString();
//	String newNomber =currentyear+ structure.substring(0, structure.length() - number.length())	+ number;
//	return newNomber;
//}
//	
//	

	
	
    
	

	
}
