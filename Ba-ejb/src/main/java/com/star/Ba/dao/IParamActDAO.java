package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.model.ParamAct;
@Local
      public interface IParamActDAO {
	  void create(ParamAct paramAct);

	    void edit(ParamAct paramAct);

	    void remove(ParamAct paramAct);

	    ParamAct find(Object id);

	    List<ParamAct> findAll();

	    List<ParamAct> findRange(int[] range);

	  //  int count();
	

	   int getParamAct_by_numact(int age, double capital);


}

