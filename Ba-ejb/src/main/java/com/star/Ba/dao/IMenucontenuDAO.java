package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.model.Menucontenu;

public interface IMenucontenuDAO {
	 void create(Menucontenu menuContenu);

	    void edit(Menucontenu menuContenu);

	    void remove(Menucontenu menuContenu);

	    Menucontenu find(Object id);

	    List<Menucontenu> findAll();

	    List<Menucontenu> findRange(int[] range);

	    int count();

		List<Menucontenu> getMenuContenu(String page, BigDecimal idProfil);

		List<Menucontenu> getMenuContenuByPack(String page,
				BigDecimal idProfil, Integer pack);
}
