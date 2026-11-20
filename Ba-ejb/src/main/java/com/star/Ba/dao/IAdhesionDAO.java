/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IAdhesionDAO {

    void create(Adhesion adhesion);

    void edit(Adhesion adhesion);

    void remove(Adhesion adhesion);

    Adhesion find(Object id);

    List<Adhesion> findAll();

    List<Adhesion> findByUtilisateur(Utilisateurs utilisateur);

    List<Adhesion> findRange(int[] range);

    int count();

    Connection getConnection();

	List<Adhesion> FindAdhesionByPolice(Police polices);

	Adhesion FindAdhesionByNadhesion(String nadhesion);

	List<Client> findClientFromAdhesionByUtilisateur(
			List<Utilisateurs> utilisateur);

	List<Client> findClientFromAdhesionByUtilisateurAndPolice(
			List<Utilisateurs> utilisateur, List<Police> listePolice);

	List<Adhesion> findByPolice(Utilisateurs utilisateur, Police police);

	List<Adhesion> FindAdhesionByProduit(List<Police> listPolice,
			String annee1, String annee2);

	List<Adherent> FindAssuree(Adhesion adhesion);

	List<Adhesion> findByUtilisateurAndDate(Utilisateurs utilisateur,
			String annee1, String annee2, String nadhesion, String identifiant);

	List<Adhesion> findByPoliceAndDate(Utilisateurs utilisateur, Police police,
			String annee1, String annee2, String nadhesion, String identifiant);

	List<Adhesion> FindAdhesionForAnnulation(Utilisateurs utilisateur,
			Date datcreation);

	

	int AnnulationAdhesion(List<String> listadhesion);

	Adhesion FindMaxNadhesion(Police p);

	String getNadhesion(String numPolice);

	Adhesion getParbynpolice2(String npolice);

	List<Adhesion> replicatenumdec(String num_decision, String ref_credit);

	Adhesion merge(Adhesion adhesion);
	
	List<Adhesion> propexiste (Proposition proposition);
	
	
	List<Adhesion> propexiste_7ayya (Proposition proposition);
	
	
	
	
/************** partie encaissement terme ****************************************/	
	
	List<Adhesion> findByUtilisateurAndDateAndPoliceAndClient(Utilisateurs utilisateur,
			String annee1, String annee2, String nadhesion, String cin, String libelleProduit);
	
	
	/************** partie encaissement libre ****************************************/	
	
	 List<Adhesion> findByUtilisateurAndDateAndPoliceAndClient_for_VL(Utilisateurs utilisateur, String annee1,
			String annee2, String nadhesion, String cin, String libelleProduit);
	
}
