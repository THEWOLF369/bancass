/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.Tariftd;

import java.util.List;

import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface ITariftdDAO {

    void create(Tariftd tariftd);

    void edit(Tariftd tariftd);

    void remove(Tariftd tariftd);

    Tariftd find(Object id);

    List<Tariftd> findAll();

    List<Tariftd> findRange(int[] range);

    int count();

	String getcodetarif_by_police(String nPolice);

	double getTaux_prime(int dur, int age, String code, double capital,	String nPolice);

	int find_min_age(String nPolice);

	int find_max_age(String nPolice);
}
