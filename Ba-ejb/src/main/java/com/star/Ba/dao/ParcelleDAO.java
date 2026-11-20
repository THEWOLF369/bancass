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

import com.star.Ba.model.Parcelle;
import com.star.Ba.model.Police;
import com.star.Ba.model.Quittance;

/**
 *
 * @author star
 */
@Stateless
public class ParcelleDAO extends AbstractDAO<Parcelle> implements IParcelleDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParcelleDAO() {
        super(Parcelle.class);
    }
    
    @Override
    public void creation(Parcelle p){
    	em.merge(p);
    }
    
    
  
    
    
    @Override
    public List<Parcelle> getParbynpolice (String nAdhesion) {
        Query q = em.createQuery("SELECT a FROM Parcelle a WHERE a.nPolice = :nAdhesion");
        q.setParameter("nAdhesion", nAdhesion);
       return  q.getResultList();
     
    }
    
    
    
    
}
