package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.TypeAbonnement;


public interface ITypeAbonnementDAO {
	void create(TypeAbonnement typeAbonnement);

    void edit(TypeAbonnement typeAbonnement);

    void remove(TypeAbonnement typeAbonnement);

    TypeAbonnement find(Object id);

    List<TypeAbonnement> findAll();

    List<TypeAbonnement> findRange(int[] range);

    int count();
}
