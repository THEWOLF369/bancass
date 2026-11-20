package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Adherent;
import com.star.Ba.model.Proposition;

public interface IAdherentDAO {
	void create(Adherent adherent);

    void edit(Adherent adherent);

    void remove(Adherent adherent);

    Adherent find(Object id);

    List<Adherent> findAll();

    List<Adherent> findRange(int[] range);

    int count();

	Adherent findAdhByIdentifiant(String identifiant);
	public List<Adherent> findAdhByIdProposition(int idProposition) ;
	public Adherent enregistrerAdherent(Adherent adherent) ;
}
