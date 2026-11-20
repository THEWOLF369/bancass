/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.model.Agrclasse;
import com.star.Ba.model.PrixHom;
import com.star.Ba.model.Variete;

/**
 *
 * @author star
 */
@Local
public interface IVarieteDAO {

	List<Variete> findvariete(String classe);

	List<Variete> findlegume();

  

}
