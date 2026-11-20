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

import org.apache.commons.lang3.StringUtils;

import com.star.Ba.dto.TarifAgrDTO;
import com.star.Ba.model.TarifGrele;
import com.star.Ba.model.TarifIncendieAgr;

/**
 * 
 * @author star
 */
@Stateless
public class TarifIncendieAgrDAO extends AbstractDAO<TarifIncendieAgr> implements ITarifIncendieAgrDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public TarifIncendieAgrDAO() {
		super(TarifIncendieAgr.class);
	}

	@Override
	public TarifAgrDTO getTarifByIdprodConv(long idProdconv, BigDecimal capital) {
		Query q = em.createQuery(
				"select t from TarifIncendieAgr t where t.idprodconv.idProdconv= :idProdconv and t.min< :capital and t.max>= :capital").setParameter(
				"idProdconv", idProdconv).setParameter("capital", capital);
		List<TarifIncendieAgr> list = q.getResultList();
		System.out.println(capital+"       idprodconv"+idProdconv+"        mmmmmmm       "+list.size());
		BigDecimal taux=BigDecimal.ZERO;
		TarifAgrDTO tarif= new TarifAgrDTO();
		BigDecimal primeMinimal=BigDecimal.ZERO;
		if (list != null && !list.isEmpty()) {
			taux = list.get(0).getTaux();
			tarif.setFranchise(list.get(0).getFranchise().toString());
			tarif.setExtension1(list.get(0).getExtensionVa());
			tarif.setExtension2(list.get(0).getExtensionpp());
			primeMinimal=list.get(0).getPrimeMin();
		}
		
		System.out.println("Taux=" + taux+"    "+capital.multiply(taux));
		
		tarif.setPrime(capital.multiply(taux).doubleValue()+"");
		if(primeMinimal !=null && primeMinimal.doubleValue()>capital.multiply(taux).doubleValue()){
			tarif.setPrime(primeMinimal.doubleValue()+"");
		} 
		System.out.println(primeMinimal+"----------------"+capital.multiply(taux).doubleValue()+"----------------"+tarif.getPrime());
		
//		BigDecimal test; 
//		test =  new BigDecimal(tarif.getPrime());
//		double d = test.doubleValue();
//		
//		 
//		if (d < primeMinimal.doubleValue())
//		{
//		 tarif.setPrime(primeMinimal.toString());
//		}

		
		return tarif;
	}
	
	
	
	@Override
	public TarifAgrDTO getbyTarif661(BigDecimal taux, BigDecimal capital) {
		
		TarifAgrDTO tarif= new TarifAgrDTO();
		
		tarif.setPrime(capital.multiply(taux).doubleValue()+"");
		
	
	BigDecimal test	 =  new BigDecimal(tarif.getPrime());
	double d = test.doubleValue();
	
	 
	

		
		return tarif;
	}
	
	
	
	
	
	
	
	
	
//
}
