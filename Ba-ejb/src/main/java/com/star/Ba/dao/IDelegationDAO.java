package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Delegation;

public interface IDelegationDAO {
	void create(Delegation delegation);

    void edit(Delegation delegation);

    void remove(Delegation delegation);

    Delegation find(Object id);

    List<Delegation> findAll();

    List<Delegation> findRange(int[] range);

    int count();

}
