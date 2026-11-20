/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.dao.AbstractDAO;
import com.star.Ba.dao.IDetailMrhDAO;
import com.star.Ba.model.DetailMrh;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author star
 */
@Stateless
public class DetailMrhDAO extends AbstractDAO<DetailMrh> implements IDetailMrhDAO {
    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DetailMrhDAO() {
        super(DetailMrh.class);
    }
    
}
