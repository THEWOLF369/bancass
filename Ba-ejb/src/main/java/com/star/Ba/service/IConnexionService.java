/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.service;
import com.star.Ba.dto.UtilisateursConnexionDTO;
import com.star.Ba.model.Utilisateurs;


/**
 *
 * @author star
 */
public interface IConnexionService {
    public UtilisateursConnexionDTO connexion(String login, String password);

	
}
