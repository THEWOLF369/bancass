package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Societe;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Stateless
public class ClientDAO extends AbstractDAO<Client> implements IClientDAO {

	@PersistenceContext(unitName = "oracle")
	private EntityManager em;

	public ClientDAO() {
		super(Client.class);
	}

	public ClientDAO(Class<Client> entityClass) {
		super(entityClass);
	}

	@Override
	protected EntityManager getEntityManager() {
		return em;
	}

	public Client saveOrUpdate(Client client) {

		return getEntityManager().merge(client);
	}

	// @Override
	// public Client findByIdentifiant(String identifiant) {
	// TypedQuery<Client> query = em.createNamedQuery(
	// "Client.findByIdentifiant", Client.class);
	// query.setParameter("identifiant", identifiant);
	//
	// try {
	// return query.getSingleResult();
	// } catch (Exception e) {
	// System.out.println(e.getMessage());
	// }
	// return null;
	// }

	@Override
	public Client findByIdentifiant(String identifiant) {
		Query q = em.createQuery("SELECT a FROM Client a WHERE a.identifiant = :identifiant").setParameter("identifiant", identifiant);
		List<Client> clients = q.getResultList();

		if (!clients.isEmpty()) {
			return clients.get(0);
		} else {
			return null;
		}
	}
	
	@Override
	public Client findByIdentifiantAndNcompte(String identifiant, String ncompte) {
		Query q = em.createQuery("SELECT a FROM Client a WHERE a.identifiant = :identifiant "
				+ "and a.ncompte = :ncompte")
				.setParameter("identifiant", identifiant)
				.setParameter("ncompte", ncompte);
		List<Client> clients = q.getResultList();

		if (!clients.isEmpty()) {
			return clients.get(0);
		} else {
			return null;
		}
	}

	@Override
	public Client FindClientByNcompte(String ncompte) {
		Query q = em.createQuery("select a from Client a where a.ncompte = :ncompte ").setParameter("ncompte", ncompte);
		try {
			return (Client) q.getSingleResult();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return null;

	}

	@Override
	public Client creation(Client r) {
		Client c = findByIdentifiant(r.getIdentifiant());
		if (c != null) {
//			em.merge(r);
//			em.flush();
			return c;
		} else {
			em.persist(r);
			em.flush();
			System.out.println("id client " + r.getIdClient());
			return r;
		}

	}
}
