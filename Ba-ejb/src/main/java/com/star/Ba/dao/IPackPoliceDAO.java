/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.star.Ba.dao;
import java.util.List;

import com.star.Ba.dto.PackPoliceDTO;
import com.star.Ba.model.PackPolice;



/**
 *
 * @author star
 */

public interface IPackPoliceDAO {

    void create(PackPolice packPolice);

    void edit(PackPolice packPolice);

    void remove(PackPolice packPolice);

    PackPolice find(Object id);

    List<PackPolice> findAll();

    List<PackPolice> findRange(int[] range);
    
    int count();

	PackPoliceDTO chercherPackPolice(String numPolice);

	PackPolice Findbypolice(String numPolice);

	List<PackPolice> findPackbypolice(String numPolice);

    
}
