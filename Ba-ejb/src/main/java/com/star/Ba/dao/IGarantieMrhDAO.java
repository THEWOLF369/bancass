package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.GarantieMrh;

public interface IGarantieMrhDAO {
	
    void create(GarantieMrh garantieMrh);

    void edit(GarantieMrh garantieMrh);

    void remove(GarantieMrh garantieMrh);

    GarantieMrh find(Object id);

    List<GarantieMrh> findAll();

    List<GarantieMrh> findRange(int[] range);

    int count();

}
