package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Agrclasse;

public interface IAgrClasseDAO {
	void create(Agrclasse agrclasse);

    void edit(Agrclasse agrclasse);

    void remove(Agrclasse agrclasse);

    Agrclasse find(Object id);

    List<Agrclasse> findAll();

    List<Agrclasse> findRange(int[] range);

    int count();

	Agrclasse getClasseByNom(String nomclasse);

}
