package com.star.Ba.dao;

import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agence;
import com.star.Ba.model.AgenceProduit;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Societe;
import com.star.Ba.model.Utilisateurs;

@Local
public interface IChartDAO {
	
	List<Adhesion> SimplePie(Police numpolice);
	
	
	List<Object[]> SimplePieObject();
	
	
	 List<Police> listePolice(Produit codeproduit);
	 
	 Produit avoirProduit(long id_produit);
	 
	 Convention avoirConvention(Societe societe);
	 
	 
	 long  countAdhesion(Police numpolice);
	 
	 long  countAdhesionParDate(Police numpolice,String annee1,String annee2,int num_mois);
	 
	 long  countAdhesionParDatePArUtilisateur(Police numpolice,Utilisateurs utilisateur,String annee1,String annee2,int num_mois);
	 
	 List<Police> listePoliceParConvention(Produit codeproduit,Convention convention);
	 
	 Agence avoirAgence(int idAgence);
	 
	  Societe avoiSociete(String codSociet);
	 
	 List<Utilisateurs> listeUtilisateur (Agence agence);
	 
	 
	 long  countAdhesionParUtilisateur(Police numpolice, Utilisateurs utilisateurs);
	 
	 
	 public Map<String, Object> recupererListeAgence(Societe societe, String profil);
	 
	 
	 double sommeMtPrimeParDateParUtilisateur(Police numpolice,Utilisateurs utilisateur,String annee1,String annee2,int num_mois);
	 double sommeMtPrimeTotParDateParUtilisateur(Police numpolice,Utilisateurs utilisateur,String annee1,String annee2,int num_mois);
	 
	 public Map<String, Object> recupererListeSociete();


	double sommeMtPrime(Police numpolice, String annee1, String annee2,int num_mois);


	double sommeMtPrimeTot(Police numpolice, String annee1, String annee2,int num_mois);
	
	 List<Object> exportProduit(int idagence,String annee1, String annee2,List<Integer> listProduit,int num_mois);
	 List<Object> exportProduit(String codsociete,String annee1, String annee2,List<Integer> listProduit,int num_mois);

//// modif pour bna	 
	 
	 List<Object> aficherStat(String codsociete,String annee1, String annee2,List<Integer> listProduit);
	 List<Object> aficherStat(int idagence,String annee1, String annee2,List<Integer> listProduit);
//// fin modif pour bna
	 
	 
	long countAdhesionYear(Police numpolice);


	long countAdhesionParUtilisateurYear(Police numpolice,
			Utilisateurs utilisateurs);


	Map<String, Object> recupererListeSocietebna();


	List<Agence> avoirAgencebysoci();


	long countAdhesionParDatePArUtilisateurbna(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2, int num_mois);


	double sommeMtPrimeParDateParUtilisateurbna(
			Utilisateurs utilisateur, String annee1, String annee2, int num_mois);


	List<Police> listePolicebna(Produit codeproduit);


	List<Utilisateurs> listeUtilisateurbna(Agence agence);


	List<Police> listePolicebnaagent(Produit codeproduit);


	List<Police> listePoliceParConventionbna(Produit codeproduit,
			Convention convention);


	long countAdhesionYeargrele();


	long countAdhesionYearincendie();


	long countAdhesionYeartdbna();


	long countAdhesionYeartdbnaparuser(int x);


	long countAdhesionYearincendieuser(int x);


	long countAdhesionYeargreleuser(int x);


	List<Object> exportData(String codsociete, String annee1, String annee2,String etat);
	List<Object> exportDataupdate(String codsociete, String annee1, String annee2);



	long countAdhesionParDatePAragencebna(Utilisateurs utilisateur,
			String annee1, String annee2, int num_mois);


	long countAdhesionParDatePArUtilisateurbna(Utilisateurs utilisateur,
			String annee1, String annee2, int num_mois);


	List<Police> listePolicebna2(Produit codeproduit);


	double sommeMtPrimeParDateParUtilisateurbna2(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2, int num_mois);




	
	/////////////// pour 7ayyya
	
	public long countAdhesionParUtilisateurYear_VL(Police numpolice,
			Utilisateurs utilisateurs);
	
	public long countAdhesionParUtilisateurYear_ET(Police numpolice,
			Utilisateurs utilisateurs);

	public long countAdhesionYear_VL(Police numpolice);
	
	public long countAdhesionYear_ET(Police numpolice);
	
	public long countAdhesionParDate_VL(Police numpolice,String annee1,String annee2,int num_mois);
	
	public long countAdhesionParDate_ET(Police numpolice,String annee1,String annee2,int num_mois);
	
	public long countAdhesionParDatePArUtilisateur_VL(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois);
	
	public long countAdhesionParDatePArUtilisateur_ET(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois);
	
	
	public double sommeMtPrimeTot_VL(Police numpolice, String annee1, String annee2,int num_mois);
	
	public double sommeMtPrimeTot_ET(Police numpolice, String annee1, String annee2,int num_mois);
	
	public double sommeMtPrimeTotParDateParUtilisateur_VL(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois);
	
	public double sommeMtPrimeTotParDateParUtilisateur_ET(Police numpolice,
			Utilisateurs utilisateur, String annee1, String annee2,int num_mois);
	 
	 
}
