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
import com.star.Ba.model.TypeAbonnement;

/**
 *
 * @author star
 */
@Stateless
public class TypeAbonnementDAO extends AbstractDAO<TypeAbonnement> implements ITypeAbonnementDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TypeAbonnementDAO() {
        super(TypeAbonnement.class);
    }

   
}
