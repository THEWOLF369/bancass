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

import com.star.Ba.model.GarantieAvj;
import com.star.Ba.model.ParamGarantieAvj;

/**
 *
 * @author star
 */
@Stateless
public class GarantieAvjDAO extends AbstractDAO<GarantieAvj> implements IGarantieAvjDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public GarantieAvjDAO() {
        super(GarantieAvj.class);
    }
    
    @Override
    public GarantieAvj getGarantieAvj_by_Garantie( ParamGarantieAvj Garantie) {
        Query q = em.createQuery("select a from GarantieAvj a where  a.Garantie = :Garantie").setParameter("Garantie", Garantie);
        List<GarantieAvj> list = q.getResultList();
        try {
            return list.get(0);
            
    		} catch (Exception e) {
    			System.out.println(e.getMessage());
    		}
    		return null;
        
       
    }
}
