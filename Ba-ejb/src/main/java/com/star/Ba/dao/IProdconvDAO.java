package com.star.Ba.dao;

import java.util.List;

import com.star.Ba.model.Convention;
import com.star.Ba.model.Prodconv;

public interface IProdconvDAO {
	void create(Prodconv prodconv);

	void edit(Prodconv prodconv);

	void remove(Prodconv prodconv);

	Prodconv find(Object id);

	List<Prodconv> findAll();

	List<Prodconv> findRange(int[] range);

	int count();

//	List<Prodconv> getlstProduit_by_prodconv(Prodconv prodconv);
	List<Prodconv> getlstProduit_by_convention(Convention convention);
	
	
	
	/*********p artie 7ayya quittance *****************/
	public List<Prodconv> getlstProduit_by_convention_for_qtc( Convention convention);
	
	public List<Prodconv> getlstProduit_by_convention_for_qtc_vlibre( Convention convention);

}
