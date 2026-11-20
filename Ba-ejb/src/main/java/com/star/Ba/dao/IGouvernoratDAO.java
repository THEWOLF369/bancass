package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Gouvernorat;

public interface IGouvernoratDAO {
	void create(Gouvernorat gouvernorat);

    void edit(Gouvernorat gouvernorat);

    void remove(Gouvernorat gouvernorat);

    Gouvernorat find(Object id);

    List<Gouvernorat> findAll();

    List<Gouvernorat> findRange(int[] range);

    int count();

}
