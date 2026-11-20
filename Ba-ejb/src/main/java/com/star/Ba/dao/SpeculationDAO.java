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
import com.star.Ba.model.Produit;
import com.star.Ba.model.Speculation;
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
public class SpeculationDAO extends AbstractDAO<Speculation> implements ISpeculationDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public SpeculationDAO() {
		super(Speculation.class);
	}
	
	@Override
	public Speculation getspeculation(int code_spec) {
		Query q = em.createQuery("SELECT  c from Speculation c WHERE c.code_spec = :code_spec").setParameter("code_spec", code_spec);
		List<Speculation> list = q.getResultList();
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	

}
