/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.IPackDAO;
import com.star.Ba.model.Pack;
import com.star.Ba.model.Produit;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateless
public class PackDAO extends AbstractDAO<Pack> implements IPackDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PackDAO() {
        super(Pack.class);
    }
    
    @Override
    public List <Pack> getPack_by_codprod( Produit produit) {
        Query q = em.createQuery("select a from Pack a where  a.produit= :produit").setParameter("produit", produit);
        List<Pack> list = q.getResultList();
        return list;
        
       
    }    
    
}
