package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.model.ProfessionClient;
@Local
public interface IProfessionClientDAO {
	List<ProfessionClient> findAll();
	ProfessionClient find(Object id);

}
