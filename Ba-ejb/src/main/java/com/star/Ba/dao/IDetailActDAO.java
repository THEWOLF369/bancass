package com.star.Ba.dao;
import java.util.List;

import javax.ejb.Local;
import com.star.Ba.model.DetailAct;

@Local
public interface IDetailActDAO {
    void create(DetailAct detailAct);

	void edit(DetailAct detailAct);

	void remove(DetailAct detailAct);

	DetailAct find(Object id);

	List<DetailAct> findAll();

	List<DetailAct> findRange(int[] range);

	int count();

	  

   List<DetailAct> getDetailAct_by_numact(int numact);

}

