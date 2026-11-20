package com.star.Ba.dto;

import java.util.List;

import com.star.Ba.model.Convention;
import com.star.Ba.model.Menu;
import com.star.Ba.model.Produit;
import com.star.Ba.model.Utilisateurs;

public class UtilisateursConnexionDTO {

	private Utilisateurs user;
	private List<Menu> menu;
	private List<Convention> conventions;
	private List<String> liensAutorises;
	public UtilisateursConnexionDTO() {
	}
	public Utilisateurs getUser() {
		return user;
	}
	public void setUser(Utilisateurs user) {
		this.user = user;
	}
	public List<Convention> getConventions() {
		return conventions;
	}
	public void setConventions(List<Convention> conventions) {
		this.conventions = conventions;
	}
	public List<String> getLiensAutorises() {
		return liensAutorises;
	}
	public void setLiensAutorises(List<String> liensAutorises) {
		this.liensAutorises = liensAutorises;
	}
	
	public List<Menu> getMenu() {
		return menu;
	}
	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}
	
}
