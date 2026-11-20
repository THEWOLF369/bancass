/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Utilisateurs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author star
 */
@Stateless
public class AdherentDAO extends AbstractDAO<Adherent> implements IAdherentDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AdherentDAO() {
        super(Adherent.class);
    }
    
    @Override
    public Adherent findAdhByIdentifiant(String identifiant) {
        Query q = em.createQuery("SELECT a FROM Adherent a WHERE a.identifiant = :identifiant").setParameter("identifiant", identifiant);
        List<Adherent> adherent=q.getResultList();
        
        if(!adherent.isEmpty()){
			return adherent.get(0);
		}else{
			return null;
		}
    }
    
    @Override
    public List<Adherent> findAdhByIdProposition(int idProposition) {
    	//Query q = em.createNativeQuery("SELECT * FROM ASSURE a WHERE a.id_proposition ="+idProposition);//.setParameter("idProposition", idProposition);
        Query q = em.createQuery("SELECT a FROM Adherent a WHERE a.propositions = :idProposition").setParameter("idProposition", idProposition);
        return q.getResultList();
    }
    
    @Override
    public Adherent enregistrerAdherent(Adherent adherent) {
    	adherent.setProfession(null);
    	adherent.setTypeVoie(null);
    	adherent.setAdhesion(null);
    	em.merge(adherent);
    	em.flush();
        return adherent;
    }
    
    

    
}
