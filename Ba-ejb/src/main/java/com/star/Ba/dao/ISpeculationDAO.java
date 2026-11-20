package com.star.Ba.dao;

import java.util.Date;
import java.util.List;

import com.star.Ba.model.Produit;
import com.star.Ba.model.Speculation;
import com.star.Ba.model.Taxe;

public interface ISpeculationDAO {

	Speculation getspeculation(int code_spec);

}
