package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.dto.TarifAgrDTO;
import com.star.Ba.model.TarifGrele;

public interface ITarifGreleDAO {
	void create(TarifGrele tarifGrele);

    void edit(TarifGrele tarifGrele);

    void remove(TarifGrele tarifGrele);

    TarifGrele find(Object id);

    List<TarifGrele> findAll();

    List<TarifGrele> findRange(int[] range);

    int count();


	TarifAgrDTO getTarifByClasseZone(String classe, String idzone, BigDecimal capital);


}
