package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Adhesion;
import com.star.Ba.model.Client;
import com.star.Ba.model.Police;

import javax.ejb.Local;

@Local
public interface IClientDAO {
	Client findByIdentifiant(String identifiant);
	
	Client findByIdentifiantAndNcompte(String identifiant, String ncompte);

	void edit(Client client);

	void create(Client client);

	Client saveOrUpdate(Client client);
	List<Client> findAll();

	Client FindClientByNcompte(String ncompte);

	Client creation(Client r);

}
