/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Client;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IPropositionDAO {

    void create(Proposition proposition);

    void edit(Proposition proposition);

    void remove(Proposition proposition);

    Proposition find(Object id);

    List<Proposition> findAll();

    List<Proposition> findByUtilisateur(Utilisateurs utilisateur);
    
    List<Proposition> findRange(int[] range);

    int count();

	Proposition saveOrUpdate(Proposition proposotion);

	List<Client> findClientFromPropositionByUtilisateurAndPolice(
			List<Utilisateurs> utilisateur, List<Police> listePolice);

	List<Client> findClientFromPropositionByUtilisateur(
			List<Utilisateurs> utilisateur);

	List<Proposition> findByPolice(Utilisateurs utilisateur, Police police);

	List<Proposition> findByPoliceAndDate(Utilisateurs utilisateur,
			Police police, String annee1, String annee2, String nProposition, String identifiant);

	List<Proposition> findByUtilisateurAndDate(Utilisateurs utilisateur,
			String annee1, String annee2, String nProposition, String identifiant);

	List<Proposition> findPropositionByStarForSTB(List<Police> listPolice, List<Utilisateurs> listStar,
			String annee1, String annee2, String nProposition, String identifiant);

	int traitement_prop(String nProposition);

	boolean nproposition_unique(String nProposition);
}
