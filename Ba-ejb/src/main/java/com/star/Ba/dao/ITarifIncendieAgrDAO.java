package com.star.Ba.dao;

import java.math.BigDecimal;
import java.util.List;

import com.star.Ba.dto.TarifAgrDTO;
import com.star.Ba.model.TarifIncendieAgr;

public interface ITarifIncendieAgrDAO {
	void create(TarifIncendieAgr tarifIncendieAgr);

    void edit(TarifIncendieAgr tarifIncendieAgr);

    void remove(TarifIncendieAgr tarifIncendieAgr);

    TarifIncendieAgr find(Object id);

    List<TarifIncendieAgr> findAll();

    List<TarifIncendieAgr> findRange(int[] range);

    int count();



	TarifAgrDTO getTarifByIdprodConv(long idProdconv, BigDecimal capital);

	TarifAgrDTO getbyTarif661(BigDecimal taux, BigDecimal capital);

}
