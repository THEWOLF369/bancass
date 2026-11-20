/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.model.Agence;
import com.star.Ba.model.Profil;
import com.star.Ba.model.Utilisateurs;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IUtilisateursDAO {

    void create(Utilisateurs utilisateurs);

    void edit(Utilisateurs utilisateurs);

    void remove(Utilisateurs utilisateurs);

    Utilisateurs find(Object id);

    List<Utilisateurs> findAll();

    List<Utilisateurs> findRange(int[] range);

    int count();

    public UtilisateursConnexionDTO connexion(java.lang.String login, java.lang.String password);
    public com.star.Ba.model.Utilisateurs searchLogin(java.lang.String login);
    
    
    public com.star.Ba.model.Utilisateurs searchLoginMdp(String login, String password);
    
    

	List<Utilisateurs> listeUtilisateur(Agence agence);
	 public  Utilisateurs createUser(Utilisateurs utilisateurs);

	List<Utilisateurs> find_utilisateurs_by_profil(Profil profil);

	

    List<Utilisateurs> find_utilisateurs_bna();
    
    
    List<Utilisateurs> find_utilisateurs_all_bna();

	List<Utilisateurs> find_admin_bna();

	Utilisateurs searchmail(String mail);







	
}
