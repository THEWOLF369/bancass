package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.star.Ba.dto.DetailsEvolutionGarantie;
import com.star.Ba.model.DetailHayya;
import com.star.Ba.model.PasswordHistory;
import com.star.Ba.model.Utilisateurs;


@Stateless
public class PasswordHistoryDAO extends AbstractDAO<PasswordHistory> implements IPasswordHistoryDAO {
	
	
	
	@PersistenceContext(unitName = "oracle")
	private EntityManager em;
	
	public PasswordHistoryDAO() {
		super(PasswordHistory.class);
	}
	
	
	public PasswordHistoryDAO(Class<PasswordHistory> entityClass) {
		super(entityClass);
		// TODO Auto-generated constructor stub
	}
	
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	
	@Override
	public List<PasswordHistory> findUserOrderByChangedAtDesc(Utilisateurs utilisateurs) {

		String jpql = "SELECT p FROM PasswordHistory p WHERE p.utilisateurs = :utilisateurs ORDER BY p.changedAt DESC";
		
        Query q = em.createQuery(jpql).setParameter("utilisateurs", utilisateurs);
        List<PasswordHistory> list = q.getResultList();
        return list;

	}

	@Override
	public List<PasswordHistory> findTop10ByUserOrderByChangedAtDesc(Utilisateurs utilisateurs) {

		String jpql = "SELECT p FROM PasswordHistory p WHERE p.utilisateurs = :utilisateurs ORDER BY p.changedAt DESC";
		
        Query q = em.createQuery(jpql).setParameter("utilisateurs", utilisateurs);
        q.setMaxResults(10);
        List<PasswordHistory> list = q.getResultList();
        return list;

	}

	@Override
	public List<PasswordHistory> findTop3ByUserOrderByChangedAtDesc(Utilisateurs utilisateurs) {

		String jpql = "SELECT p FROM PasswordHistory p WHERE p.utilisateurs = :utilisateurs ORDER BY p.changedAt DESC";
		
        Query q = em.createQuery(jpql).setParameter("utilisateurs", utilisateurs);
        q.setMaxResults(3);
        List<PasswordHistory> list = q.getResultList();
        return list;
	}

}
