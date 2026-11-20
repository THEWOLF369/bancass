/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.service;

import com.star.Ba.dao.IUtilisateursDAO;
import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.service.IConnexionService;

import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author star
 */
@Stateless
public class ConnexionService implements IConnexionService {

    @EJB
    private IUtilisateursDAO iUtilisateursDAO;

    @Override
    public UtilisateursConnexionDTO connexion(String login, String password) {

    	return iUtilisateursDAO.connexion(login,  password);
           
    }

	


    
   
}
