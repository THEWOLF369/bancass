/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Produit;
import com.star.Ba.model.Utilisateurs;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface IProduitDAO {

    void create(Produit produit);

    void edit(Produit produit);

    void remove(Produit produit);

    Produit find(Object id);

    List<Produit> findAll();
    
    List<Produit> findAllById(List<Long> ids);

    List<Produit> findRange(int[] range);

    int count();

	Produit getProduit_by_code(String codeProduit);
	
	public List<Produit> findByUser(Utilisateurs user);

}
