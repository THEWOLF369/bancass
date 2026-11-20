/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.star.Ba.dto.ContratDTO;
import com.star.Ba.dto.TarifAgrDTO;
import com.star.Ba.model.TarifGrele;

/**
 * 
 * @author star
 */
@Stateless
public class TarifGreleDAO extends AbstractDAO<TarifGrele> implements ITarifGreleDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public TarifGreleDAO() {
		super(TarifGrele.class);
	}

	@Override
	public TarifAgrDTO getTarifByClasseZone(String classe, String idzone, BigDecimal capital) {
		Query q = em.createQuery("select t." + classe + " from TarifGrele t where t.idtarif= :idzone").setParameter("idzone", new BigDecimal(idzone));
		List<String> list=q.getResultList();
		
		String taux = "0";
		if(list!=null &&  ! list.isEmpty()){
			taux=list.get(0);
		}
		BigDecimal extension = null;
		String franchise="0";
		q = em.createQuery("select t.extension,t.franchise from TarifGrele t where t.idtarif= :idzone").setParameter("idzone", new BigDecimal(idzone));
//		List<BigDecimal> listex=q.getResultList();
		List<Object[]> listex = q.getResultList();
		if(listex!=null &&  ! listex.isEmpty()){
			Object[] s =listex.get(0);
			extension= new BigDecimal(s[0].toString());
			franchise=s[1].toString();
		}
		
		System.out.println("Taux="+taux);
		taux=StringUtils.replace(taux, ",",".");
		BigDecimal prime=capital.multiply(new BigDecimal(taux));
		prime=prime.divide(new BigDecimal(100));
		TarifAgrDTO c=new TarifAgrDTO();
		c.setPrime(prime  + "");
		c.setExtension1(extension);
		c.setFranchise(franchise);
		c.setTaux(taux);
		
		BigDecimal test; 
		test =  new BigDecimal(c.getPrime());
		double d = test.doubleValue();
		BigDecimal test2 = new BigDecimal("15");
		
		if (d < 15)
		{
		 c.setPrime(test2.toString());
		}
		
		return c;
	}

}
