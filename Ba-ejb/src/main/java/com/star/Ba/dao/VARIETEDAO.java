/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Adherent;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Agrclasse;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.PrixHom;
import com.star.Ba.model.Tauxcomm;
import com.star.Ba.model.Utilisateurs;
import com.star.Ba.model.Variete;

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
public class VARIETEDAO extends AbstractDAO<Variete> implements IVarieteDAO {

    @PersistenceContext(unitName = "oracle")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VARIETEDAO() {
        super(Variete.class);
    }
    
    @Override
    public List<Variete> findvariete(String classe) {
    	Query q = em.createQuery("select t from Variete t where  t.classe = :classe")
               .setParameter("classe", classe);

        List<Variete> list = q.getResultList();
        return list;
    }
    
    @Override
    public List<Variete> findlegume() {
    	Query q = em.createQuery("select t from Variete t where  t.classe = 'classe3' "
    			+ "and t.nomVariete <> 'Oliviers'");
               

        List<Variete> list = q.getResultList();
        return list;
    }

	@Override
	public void create(Variete variete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void edit(Variete variete) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Variete variete) {
		// TODO Auto-generated method stub
		
	}

    
}
