/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.model.DureServiceBourse;
import com.star.Ba.model.Fractionnement;

/**
 *
 * @author star
 */
@Stateless
public class FractionnementDAO extends AbstractDAO<Fractionnement> implements IFractionnementDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FractionnementDAO() {
        super(Fractionnement.class);
    }

    @Override
    public Fractionnement getFractionnement_by_Libelle( String libelle) {
        Query q = em.createQuery("select a from Fractionnement a where  substring(a.libelle,0,1) = :libelle").setParameter("libelle", libelle);
        List<Fractionnement> list = q.getResultList();
        return list.get(0);
        
       
    }
}
