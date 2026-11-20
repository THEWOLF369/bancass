package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Parametres;

public interface IParametresDAO {
	void create(Parametres parametres);

    void edit(Parametres parametres);

    void remove(Parametres parametres);

    Parametres find(Object id);

    List<Parametres> findAll();

    List<Parametres> findRange(int[] range);

    int count();

}
