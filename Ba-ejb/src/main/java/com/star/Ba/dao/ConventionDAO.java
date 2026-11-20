/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Agence;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Societe;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Hibernate;

/**
 *
 * @author star
 */
@Stateless
public class ConventionDAO extends AbstractDAO<Convention> implements IConventionDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConventionDAO() {
        super(Convention.class);
    }

    @Override
    public Convention find_conv_by_Societ(Societe societe) {
        Query q = em.createQuery("SELECT a FROM Convention a WHERE a.societe = :societe").setParameter("societe", societe);
        List<Convention> list = q.getResultList();
        return list.get(0);
    }
    
    @Override
    public Convention find_conv_by_Societbna() {
        Query q = em.createQuery("SELECT a FROM Convention a WHERE a.societe = 'B142431996'");
        List<Convention> list = q.getResultList();
        return list.get(0);
    }
    
   

    @Override
    public Convention lazyLoad(Convention convention) {
        convention = find(convention.getIdConvention());

        Hibernate.initialize(convention.getProdconvs());

        return convention;
    }

	@Override
	public Convention createOrUpdate(Convention convention) {
		return em.merge(convention);
		
	}

}
