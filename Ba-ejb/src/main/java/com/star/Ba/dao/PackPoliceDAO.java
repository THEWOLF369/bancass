/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.time.DateUtils;

import com.star.Ba.dto.PackPoliceDTO;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.PackPolice;

/**
 * 
 * @author star
 */
@Stateless
public class PackPoliceDAO extends AbstractDAO<PackPolice> implements
		IPackPoliceDAO {
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public PackPoliceDAO() {
		super(PackPolice.class);
	}

	@Override
	public PackPoliceDTO chercherPackPolice(String numPolice) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Query q = em
				.createQuery(
						"select p from PackPolice p where p.packPolicePK.nPolice = :numPolice and p.etaPolice= 'V' and to_CHAR(datecreation,'dd/mm/yyyy')= to_CHAR(SYSDATE,'dd/mm/yyyy') ")
				.setParameter("numPolice", numPolice);
		List<PackPolice> list = q.getResultList();
		PackPoliceDTO result = new PackPoliceDTO();
		if (list == null || list.isEmpty()) {
			result.setEtat(2);
			result.setMsg("Police inexistante et/ou vous avez depassée 24h ");
			System.out.println("++++++++++++ Police inexistante");

		} else if (list != null && !list.isEmpty()) {
			Query q2 = em
					.createQuery(
							"select p from PackPolice p where p.packPolicePK.idPackPolice = :idPackPolice  and p.etaPolice= 'V'")
					.setParameter("idPackPolice",
							list.get(0).getPackPolicePK().getIdPackPolice());
			result.setList(q2.getResultList());
			result.setEtat(1);
			result.setMsg("Il y a d'autres contrats dans le même pack que celui que vous avez chercher ");
		}
		return result;
	}
	
	@Override
	public PackPolice Findbypolice (String numPolice) {
		Query q = em.createQuery("select p from PackPolice p where p.packPolicePK.nPolice = :numPolice ")
				.setParameter("numPolice", numPolice);
		System.out.println("police**********="+(PackPolice) q.getSingleResult());
		try {
			return (PackPolice) q.getSingleResult();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}
	
	
	@Override
	public List<PackPolice> findPackbypolice (String numPolice) {
		Query q = em.createQuery("select p from PackPolice p where p.packPolicePK.nPolice = :numPolice ")
				.setParameter("numPolice", numPolice);
		List<PackPolice> listPack = q.getResultList();
		if(listPack != null && ! listPack.isEmpty()){
			PackPolice p= listPack.get(0);
			Query q1 = em.createQuery("select p from PackPolice p where p.packPolicePK.idPackPolice = :idPackPolice ")
					.setParameter("idPackPolice", p.getPackPolicePK().getIdPackPolice());
			return q1.getResultList();
		}
		return null;

	}
}
