package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.model.DureServiceBourse;
import com.star.Ba.model.Fractionnement;

public interface IFractionnementDAO {

	void create(Fractionnement fractionnement);

    void edit(Fractionnement fractionnement);

    void remove(Fractionnement fractionnement);

    Fractionnement find(Object id);

    List<Fractionnement> findAll();

    List<Fractionnement> findRange(int[] range);

    int count();

	Fractionnement getFractionnement_by_Libelle(String libelle);

}
