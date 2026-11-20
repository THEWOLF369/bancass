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

import com.star.Ba.model.Delegation;
import com.star.Ba.model.TarifGreleDelegation;

/**
 * 
 * @author star
 */
@Stateless
public class TarifGreleDelegationDAO extends AbstractDAO<TarifGreleDelegation> implements ITarifGreleDelegationDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public TarifGreleDelegationDAO() {
		super(TarifGreleDelegation.class);
	}

	@Override
	public List<Delegation> getDelegationByTarif(BigDecimal idTarif) {
		Query q = em.createQuery("SELECT d.iddelegation FROM TarifGreleDelegation d where d.idtarif.idtarif = :idTarif").setParameter("idTarif", idTarif);
		return q.getResultList();
	}

}
