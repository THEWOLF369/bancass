package com.star.Ba.dao;

import javax.ejb.Local;

import com.star.Ba.model.ApiResponseEntity;


@Local
public interface IApiResponseEntityDAO {
	
	void edit(ApiResponseEntity entity);
	void create(ApiResponseEntity entity);

}
