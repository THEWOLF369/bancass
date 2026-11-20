package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.model.Delegation;
import com.star.Ba.model.TarifGreleDelegation;

public interface ITarifGreleDelegationDAO {
	void create(TarifGreleDelegation tarifGreleDelegation );

    void edit(TarifGreleDelegation tarifGreleDelegation);

    void remove(TarifGreleDelegation tarifGreleDelegation);

    TarifGreleDelegation find(Object id);

    List<TarifGreleDelegation> findAll();

    List<TarifGreleDelegation> findRange(int[] range);

    int count();

	List<Delegation> getDelegationByTarif(BigDecimal idTarif);

}
