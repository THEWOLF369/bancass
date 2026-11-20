/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.INiveauScolaireDAO;
import com.star.Ba.model.NiveauScolaire;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author star
 */
@Stateless
public class NiveauScolaireDAO extends AbstractDAO<NiveauScolaire> implements INiveauScolaireDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NiveauScolaireDAO() {
        super(NiveauScolaire.class);
    }
    
}
