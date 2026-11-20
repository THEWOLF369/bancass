package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Beneficiaire;

public interface IBeneficiaireDAO {
	void create(Beneficiaire beneficiaire);

    void edit(Beneficiaire beneficiaire);

    void remove(Beneficiaire beneficiaire);

    Beneficiaire find(Object id);

    List<Beneficiaire> findAll();

    List<Beneficiaire> findRange(int[] range);

    int count();
}
