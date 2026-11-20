package com.star.Ba.dao;

import com.star.Ba.model.Locgouv;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Stateless
public class LocgouvDAO extends AbstractDAO<Locgouv> implements ILocgouvDAO{

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public LocgouvDAO(Class<Locgouv> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public LocgouvDAO() {
		super(Locgouv.class);
	}
	
	@Override
	public Locgouv findByPostaCode(String codePosta){
		TypedQuery<Locgouv> query = em.createNamedQuery(
				"Locgouv.findByPostaCode", Locgouv.class);
		query.setParameter("codePosta", codePosta);

		try {
			List<Locgouv> result = query.getResultList(); 
			if(result.size() >  0){
				return result.get(0);
			}

		} catch (Exception e) {
			System.out.println("not found");
			System.out.println(e.getMessage());
		}
		return null;
	}
	@Override
	public List<Locgouv> findAll(){
		TypedQuery<Locgouv> query = em.createNamedQuery(
				"Locgouv.findAll", Locgouv.class);
		
		return query.getResultList();
	}

	@Override
	public Locgouv findByPostaCode(String codePosta, String localite) {
		TypedQuery<Locgouv> query = em.createNamedQuery(
				"Locgouv.findByPostaCodeAndLocalite", Locgouv.class);
		query.setParameter("codePosta", codePosta);
		query.setParameter("localite", localite);

		try {
			List<Locgouv> result = query.getResultList(); 
			if(result.size() >  0){
				return result.get(0);
			}

		} catch (Exception e) {
			System.out.println("not found");
			System.out.println(e.getMessage());
		}
		return null;
		
	
	}
	
	
	@Override
	public List<String> findGouv(){
		TypedQuery<String> query = em.createNamedQuery(
				"Locgouv.findGouv", String.class);
		
		return query.getResultList();
	}
	
	@Override
	public List<Locgouv> findByGouv(String gouvernora){
		TypedQuery<Locgouv> query = em.createNamedQuery(
				"Locgouv.findByGouv", Locgouv.class);
		query.setParameter("gouvernora", gouvernora);
		
		return query.getResultList();
	}
	
	
	@Override
	public Locgouv findByCode(long code){
		TypedQuery<Locgouv> query = em.createNamedQuery(
				"Locgouv.findByCode", Locgouv.class);
		query.setParameter("code", code);

		try {
			List<Locgouv> result = query.getResultList(); 
			if(result.size() >  0){
				return result.get(0);
			}

		} catch (Exception e) {
			System.out.println("not found");
			System.out.println(e.getMessage());
		}
		return null;
	}
	
}
