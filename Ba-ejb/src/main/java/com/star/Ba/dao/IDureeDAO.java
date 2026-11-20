package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Duree;


public interface IDureeDAO {
	
	void create(Duree duree);

    void remove(Duree duree);

    Duree find(Object id);

    List<Duree> findAll();
}
