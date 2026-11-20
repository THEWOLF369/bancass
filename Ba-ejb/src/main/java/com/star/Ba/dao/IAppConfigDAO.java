package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.model.ApiResponseEntity;
import com.star.Ba.model.AppConfig;


@Local
public interface IAppConfigDAO {
	
	
	public String getConfigValue(String code);
	
	void edit(AppConfig  entity);
	void create(AppConfig  entity);

	public List<AppConfig> findAll();

}
