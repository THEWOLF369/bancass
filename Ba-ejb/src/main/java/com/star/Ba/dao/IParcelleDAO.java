package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Parcelle;

public interface IParcelleDAO {
	void create(Parcelle parcelle);

    void edit(Parcelle parcelle);

    void remove(Parcelle parcelle);

    Parcelle find(Object id);

    List<Parcelle> findAll();

    List<Parcelle> findRange(int[] range);

    int count();

	void creation(Parcelle p);

	List<Parcelle> getParbynpolice(String nAdhesion);



}
