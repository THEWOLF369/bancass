package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Agrnature;

public interface IAgrNatureDAO {
	void create(Agrnature agrnature);

    void edit(Agrnature agrnature);

    void remove(Agrnature agrnature);

    Agrnature find(Object id);

    List<Agrnature> findAll();

    List<Agrnature> findRange(int[] range);

    int count();

}
