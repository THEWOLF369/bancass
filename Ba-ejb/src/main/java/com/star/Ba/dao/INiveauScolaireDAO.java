/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import com.star.Ba.model.NiveauScolaire;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author star
 */
@Local
public interface INiveauScolaireDAO {

    void create(NiveauScolaire niveauScolaire);

    void edit(NiveauScolaire niveauScolaire);

    void remove(NiveauScolaire niveauScolaire);

    NiveauScolaire find(Object id);

    List<NiveauScolaire> findAll();

    List<NiveauScolaire> findRange(int[] range);

    int count();
    
}
