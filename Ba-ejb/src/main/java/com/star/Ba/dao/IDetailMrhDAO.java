package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.DetailMrh;

public interface IDetailMrhDAO {
	
    void create(DetailMrh detailMrh);

    void edit(DetailMrh detailMrh);

    void remove(DetailMrh detailMrh);

    DetailMrh find(Object id);

    List<DetailMrh> findAll();

    List<DetailMrh> findRange(int[] range);

    int count();
}
