package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.TauxCroissancePrime;

public interface ITauxCroissancePrimeDAO {

	  void create(TauxCroissancePrime tauxCroissancePrime);

	    void edit(TauxCroissancePrime tauxCroissancePrime);

	    void remove(TauxCroissancePrime tauxCroissancePrime);

	    TauxCroissancePrime find(Object id);

	    List<TauxCroissancePrime> findAll();

	    List<TauxCroissancePrime> findRange(int[] range);

	    int count();
}
