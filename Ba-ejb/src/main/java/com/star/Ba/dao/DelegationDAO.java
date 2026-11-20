/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.model.Delegation;

/**
 *
 * @author star
 */
@Stateless
public class DelegationDAO extends AbstractDAO<Delegation> implements IDelegationDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DelegationDAO() {
        super(Delegation.class);
    }
    

    
}
