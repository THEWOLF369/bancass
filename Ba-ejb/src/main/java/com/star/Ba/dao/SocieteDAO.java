/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;




import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.model.Societe;


/**
 *
 * @author star
 */
@Stateless
public class SocieteDAO extends AbstractDAO<Societe> implements ISocieteDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SocieteDAO() {
        super(Societe.class);
    }
    
    @Override
    public List<Societe> listeSociete() {
   		Query q = em.createQuery("SELECT a FROM  Societe a where a.codSociet <> 'star' ");
   		
   		return q.getResultList();
   	}

    @Override
    public List<Societe> listeSocietebna() {
   		Query q = em.createQuery("SELECT a FROM  Societe a where a.codSociet = 'B142431996' ");
   		
   		return q.getResultList();
   	}

    
	@Override
	public Societe createNewSociete(Societe societe) {
		return em.merge(societe);
	}
    
}
