/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.PrixHom;
import com.star.Ba.model.Tauxcomm;
import com.star.Ba.model.Utilisateurs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.jdbc.ReturningWork;

/**
 *
 * @author star
 */
@Stateless
public class PrixHomDAO extends AbstractDAO<PrixHom> implements IPrixHomDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrixHomDAO() {
        super(PrixHom.class);
    }
    
    @Override
    public List<PrixHom> findPrixHom() {
    	Query q = em.createQuery("select t from PrixHom t where  :date>=t.dateApplication")
               .setParameter("date", new Date());

        List<PrixHom> list = q.getResultList();
        return list;
    }

    
}
