/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.star.Ba.model.Parametres;

/**
 *
 * @author star
 */
@Stateless
public class ParametresDAO extends AbstractDAO<Parametres> implements IParametresDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ParametresDAO() {
        super(Parametres.class);
    }
    

    
}
