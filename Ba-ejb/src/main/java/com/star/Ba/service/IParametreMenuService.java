package com.star.Ba.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.star.Ba.dto.ContenuDTO;
import com.star.Ba.model.Agrnature;
import com.star.Ba.model.Contenu;
import com.star.Ba.model.ParametresPack;

public interface IParametreMenuService {

	List<ContenuDTO> getMenuContenu(String page, BigDecimal idProfil);

	List<ContenuDTO> getMenuContenuByPack(String page, BigDecimal idProfil,
			Integer pack);

	Map<String, ParametresPack> findParametresByPack(Integer pack);



}
