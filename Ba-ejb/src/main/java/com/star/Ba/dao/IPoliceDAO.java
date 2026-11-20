/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.model.Convention;
import com.star.Ba.model.Police;
import com.star.Ba.model.Produit;

/**
 *
 * @author star
 */
@Local
public interface IPoliceDAO {

    void create(Police police);

    void edit(Police police);

    void remove(Police police);

    Police find(Object id);

    List<Police> findAll();

    List<Police> findRange(int[] range);

    int count();

  

	List<Police> getPolice_by_conv(Convention convention);

	List<Police> getPolice_by_prod(Produit codProdui);

	List<Police> getPolice_by_conv_prod(Convention convention, Produit codProdui);

	List<Police> getPolice_by_conv_libelle(Convention convention, String libelle);

	List<Police> getPolice_by_lib(String libelle);

	String getNumeroContrat(String codeProduit);

	Police enregistrer(Police p);
}
