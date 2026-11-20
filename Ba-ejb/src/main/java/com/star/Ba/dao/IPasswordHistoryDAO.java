package com.star.Ba.dao;

import java.util.List;

import javax.ejb.Local;

import com.star.Ba.model.PasswordHistory;
import com.star.Ba.model.Utilisateurs;

@Local
public interface IPasswordHistoryDAO {
	
	
	public List<PasswordHistory> findUserOrderByChangedAtDesc(Utilisateurs utilisateurs);
	
	public List<PasswordHistory> findTop10ByUserOrderByChangedAtDesc(Utilisateurs utilisateurs);
	
	
	public List<PasswordHistory> findTop3ByUserOrderByChangedAtDesc(Utilisateurs utilisateurs);

}
