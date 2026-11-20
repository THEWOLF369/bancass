package com.star.Ba.dao;

import java.util.List;
import java.util.Map;

import com.star.Ba.model.ParametresPack;

public interface IParametresPackDAO {
	void create(ParametresPack parametresPack);

    void edit(ParametresPack parametresPack);

    void remove(ParametresPack parametresPack);

    ParametresPack find(Object id);

    List<ParametresPack> findAll();

    List<ParametresPack> findRange(int[] range);

    int count();

	Map<String, ParametresPack> findParametresByPack(Integer pack);

}
