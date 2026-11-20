package com.star.Ba.dao;

import java.util.Date;
import java.util.List;

import com.star.Ba.model.Produit;
import com.star.Ba.model.Taxe;

public interface ITaxeDAO {

	double gettaxe_by_produit(Produit produit, Date date_creation);

}
