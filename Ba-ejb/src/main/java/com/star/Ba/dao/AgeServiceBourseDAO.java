/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.model.AgeServiceBourse;
import com.star.Ba.model.Convention;
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
public class AgeServiceBourseDAO extends AbstractDAO<AgeServiceBourse> implements IAgeServiceBourseDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public AgeServiceBourseDAO() {
        super(AgeServiceBourse.class);
    }
    @Override
    public AgeServiceBourse getAgeServiceBourse_by_Age( BigDecimal ageServiceBourse) {
        Query q = em.createQuery("select a from AgeServiceBourse a where  a.ageServiceBourse = :ageServiceBourse").setParameter("ageServiceBourse", ageServiceBourse);
        AgeServiceBourse list = (AgeServiceBourse)q.getSingleResult();
        return list;
        
       
    }
    
  
}
