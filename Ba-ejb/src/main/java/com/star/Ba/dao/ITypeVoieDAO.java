package com.star.Ba.dao;

import com.star.Ba.model.TypeVoie;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ITypeVoieDAO {
	List<TypeVoie> findAll();
	TypeVoie find(Object id);
}
