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

import com.star.Ba.model.DetailAct;


/**
 *
 * @author star
 */
@Stateless
public  class DetailActDAO extends AbstractDAO<DetailAct> implements IDetailActDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public DetailActDAO() {
        super(DetailAct.class);
    }
    
	
	@Override
    public List<DetailAct> getDetailAct_by_numact(int numAct) {
        Query q = em.createQuery("select a from DetailAct a where a.numAct = :numAct").setParameter("numAct", numAct);
        List<DetailAct>list= q.getResultList();
                if (list != null && !list.isEmpty()) {
        	return list ;
        	}
        return null;
       
    }

	
}

