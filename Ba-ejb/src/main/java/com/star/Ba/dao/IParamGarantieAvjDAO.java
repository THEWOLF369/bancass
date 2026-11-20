package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.ParamGarantieAvj;

public interface IParamGarantieAvjDAO {
	
	
	  void create(ParamGarantieAvj paramGarantieAvj);

	    void edit(ParamGarantieAvj paramGarantieAvj);

	    void remove(ParamGarantieAvj paramGarantieAvj);

	    ParamGarantieAvj find(Object id);

	    List<ParamGarantieAvj> findAll();

	    List<ParamGarantieAvj> findRange(int[] range);

	    int count();

}
