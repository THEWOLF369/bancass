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
import com.star.Ba.model.BilanFinancierHayya;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.DetailsEvolutionGarantieHayya;
import com.star.Ba.model.DetailsGarantieHayya;
import com.star.Ba.model.ModePaiementHayya;
import com.star.Ba.model.Proposition;
import com.star.Ba.model.Societe;


@Stateless
public class BilanFinancierHayyaImpl extends AbstractDAO<BilanFinancierHayya> implements BilanFinancierHayyaInter {

	
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public BilanFinancierHayyaImpl() {
		super(BilanFinancierHayya.class);
	}
	
	
	public BilanFinancierHayyaImpl(Class<BilanFinancierHayya> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	


	

}
