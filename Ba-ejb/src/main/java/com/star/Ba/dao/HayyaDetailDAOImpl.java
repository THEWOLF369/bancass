package com.star.Ba.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.dto.HayyaDTO;
import com.star.Ba.dto.Person;
import com.star.Ba.model.Adhesion;
import com.star.Ba.model.BenefHayyaDeces;
import com.star.Ba.model.BenefHayyaVie;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Societe;


@Stateless
public class HayyaDetailDAOImpl extends AbstractDAO<DetailHayya> implements HayyaDetailDAOInter {

	
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public HayyaDetailDAOImpl() {
		super(DetailHayya.class);
	}
	
	
	public HayyaDetailDAOImpl(Class<DetailHayya> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	

	
	
	 @Override
	    public DetailHayya find_detail_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM DetailHayya a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        System.out.println("adhesion "+adhesion.toString());
	        List<DetailHayya> list = q.getResultList();
	        return list.get(0);
	    }
	 
	 
	 @Override
	    public List<DetailsEvolutionGarantie> find_details_ev_gar_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM DetailsEvolutionGarantie a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<DetailsEvolutionGarantie> list = q.getResultList();
	        return list;
	    }
	 
	 
	 
	 @Override
	    public List<BenefHayyaVie> find_benef_vie_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM BenefHayyaVie a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<BenefHayyaVie> list = q.getResultList();
	        return list;
	    }
	 
	 
	 @Override
	    public List<BenefHayyaDeces> find_benef_deces_by_Adhesion(Adhesion adhesion) {
	        Query q = em.createQuery("SELECT a FROM BenefHayyaVie a WHERE a.adhesion = :adhesion").setParameter("adhesion", adhesion);
	        List<BenefHayyaDeces> list = q.getResultList();
	        return list;
	    }
	

	

}
