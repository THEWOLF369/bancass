package com.star.Ba.service;

import java.util.List;

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

import javax.ejb.Local;
import javax.persistence.EntityManager;

@Local
public interface AssistantVoiyagetServiceInter {
	Client findByIdentifiant(String identifiant);

	void edit(Client client);

	void create(Client client);

	Client saveOrUpdate(Client client);
	public List<TypeAbonnement> getAllTypeAbonnement();
	public List<Duree> getAllDurees() ;
	public Double calculePrimeAssistantVoiyage(long duree, double typeAbonnement);
	public List<Police> getPolice_by_conv(Convention convention);
	 public List<Police> getPolice_by_prod( String codProdui);
	 public Convention getConventionBySociete(Societe societe);
	 public Proposition createProposition(Proposition proposition);
	 public Adhesion createAdhesion(Adhesion adhesion);
	 public List<Police> getPolice_by_conv_prod(Convention convention,Produit codProdui);
      public List<Produit> getAllProduits();
	 
	 public Produit getProduitByCode(String code);
	 
	 //public List<GarantieAvenirJeunesse> getAllGarantieAvenirJeunesses();
	 public List<Profil> getAllProfiles();
	 public List<Profil> getAllChildrensProfiles(List<String> childrenCodes);
	 public List<Agence> getAllAgencesBySociete(Societe societe);
	 public Agence getPrincipaleAgence(Societe societe);

	 public List<Utilisateurs> getAllUsersChildren(List<Profil> codeProfile,
			Agence agence, Societe societe);

	


	Adhesion createAdhesionTLS(Adhesion adhesion, String numpol);

	String getNumeroQuit();

	TarifAssist calculePrimeAssistantVoiyageTLS(Long duree,
			double typeAbonnement, Police p, double age);

	Proposition UpdateProposition(Proposition proposition);
	//*

	List<Profil> getAllChildrensProfilesbna();


}
