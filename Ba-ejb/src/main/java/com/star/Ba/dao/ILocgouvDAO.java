package com.star.Ba.dao;

import com.star.Ba.model.Locgouv;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ILocgouvDAO {
	List<Locgouv> findAll();
	Locgouv find(Object id);
	Locgouv findByPostaCode(String codePosta);
	Locgouv findByPostaCode(String codePosta,String localite);
	List<String> findGouv();
	List<Locgouv> findByGouv(String gouvernora);
	Locgouv findByCode(long code);
	
}
