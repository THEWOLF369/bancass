/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.model.PackPolicePK;

/**
 * 
 * @author star
 */
@Stateless
public class PackPolicePKDAO extends AbstractDAO<PackPolicePK> implements
		IPackPolicePKDAO {
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public PackPolicePKDAO() {
		super(PackPolicePK.class);
	}

	@Override
	public BigInteger getMaxPackPolicePK(){
		Query q = em.createQuery("SELECT max(p.packPolicePK.idPackPolice)+1 from PackPolice p ");
		List<BigInteger> list=q.getResultList();
		if(list!=null && !list.isEmpty()&& list.get(0)!=null){
			System.out.println("idPack:"+list.get(0));
			return list.get(0);
		}else{
			System.out.println("idPack:1");
			return new BigInteger("1");
		}
	}
	@Override
	public PackPolicePK insertPackPolicePK(BigInteger idPackPolice,
			String nPolice) {
		PackPolicePK packPolicePK = new PackPolicePK(idPackPolice, nPolice);
		em.persist(packPolicePK);
		em.flush();
		return packPolicePK;

	}
}
