/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.model.PrixHom;

/**
 *
 * @author star
 */
@Local
public interface IPrixHomDAO {

    void create(PrixHom prixHom);

    void edit(PrixHom prixHom);

    void remove(PrixHom prixHom);

    PrixHom find(Object id);

    List<PrixHom> findAll();

    List<PrixHom> findRange(int[] range);

    int count();

	List<PrixHom> findPrixHom();

}
