/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.model.Convention;
import com.star.Ba.model.DureServiceBourse;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author star
 */
@Stateful
public class DureeServiceBourseDAO extends AbstractDAO<DureServiceBourse> implements IDureeServiceBourseDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DureeServiceBourseDAO() {
        super(DureServiceBourse.class);
    }
    @Override
    public DureServiceBourse getDureeServiceBourse_by_Duree( BigDecimal dure) {
        Query q = em.createQuery("select a from DureServiceBourse a where  a.dure = :dure").setParameter("dure", dure);
        List<DureServiceBourse> list = q.getResultList();
        return list.get(0);
        
       
    }
    
  
}
