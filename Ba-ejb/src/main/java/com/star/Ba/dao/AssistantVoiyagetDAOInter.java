package com.star.Ba.dao;



import java.util.List;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Duree;
import com.star.Ba.model.ParamGarantieAvj;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Quittance;
import com.star.Ba.model.Societe;
import com.star.Ba.model.TarifAssist;
import com.star.Ba.model.TypeAbonnement;
import com.star.Ba.model.Utilisateurs;

import javax.ejb.Local;

@Local
public interface AssistantVoiyagetDAOInter {
	Client findByIdentifiant(String identifiant);

	void edit(Client client);

	void create(Client client);

	Client saveOrUpdate(Client client);
	public List<TypeAbonnement> getAllTypeAbonnement();
	public List<Duree> getAllDurees();
	
	Double calculePrimeAssistantVoiyage(Long duree, double typeAbonnement);
	public List<Police> getPolice_by_conv(Convention convention);
	public List<Police> getPolice_by_conv_prod(Convention convention, Produit codProdui);
	 public List<Police> getPolice_by_prod( String codProdui);
	 
	 
	 public Convention getConventionBySociete(Societe societe);
	 public Proposition createProposition(Proposition proposition);
	 public Adhesion createAdhesion(Adhesion adhesion);
     public List<Produit> getAllProduits();
	 
	 public Produit getProduitByCode(String code);
	 
	 public List<ParamGarantieAvj> getAllGarantieAvenirJeunesses();
	 
	 public List<Profil> getAllProfiles();
	 public List<Profil> getAllChildrensProfiles(List<String> childrenCodes);
	 
	 public List<Utilisateurs> getAllUser();
	 public List<Utilisateurs> getAllUsersChildren(List<Profil> codeProfile,Agence agence,Societe societe);
	 
	 public List<Agence> getAllAgencesBySociete(Societe societe);
	 
	 public Agence getPrincipaleAgence(Societe societe);

	Adhesion createAdhesionTLS(Adhesion adhesion,String numpol);

	void createQuittance(Quittance quittance);
//*
	public Proposition UpdateProposition(Proposition proposition);

	List<Profil> getAllChildrensProfilesbna();
	 
	 
 
	 
	
}
