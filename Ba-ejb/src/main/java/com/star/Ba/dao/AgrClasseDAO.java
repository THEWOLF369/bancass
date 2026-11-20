/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Agrclasse;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
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
public class AgrClasseDAO extends AbstractDAO<Agrclasse> implements IAgrClasseDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public AgrClasseDAO() {
		super(Agrclasse.class);
	}
	
	@Override
	public Agrclasse getClasseByNom(String nomclasse) {
		Query q = em.createQuery("SELECT  c from Agrclasse c WHERE c.nomclasse = :nomclasse").setParameter("nomclasse", nomclasse);
		List<Agrclasse> list = q.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
