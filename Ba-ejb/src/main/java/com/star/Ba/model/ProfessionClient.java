package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the PARAM_PROFESSION database table.
 * 
 */
@Entity
@Table(name="PARAM_PROFESSION")
@NamedQuery(name="ProfessionClient.findAll", query="SELECT p FROM ProfessionClient p")
public class ProfessionClient implements Serializable {
	private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_PROFESSION")
	private Long idProfession;

	@Column(name="LIBELLE_PROFESSION")
	private String libelleProfession;

	@OneToMany(mappedBy = "profession")
	private List<Client> clients;
	
	@OneToMany(mappedBy = "profession")
	private List<Adherent> Adherents;
	
	public List<Adherent> getAdherents() {
		return Adherents;
	}

	public void setAdherents(List<Adherent> adherents) {
		Adherents = adherents;
	}

	public ProfessionClient() {
	}

	public Long getIdProfession() {
		return this.idProfession;
	}

	public void setIdProfession(Long idProfession) {
		this.idProfession = idProfession;
	}

	public String getLibelleProfession() {
		return this.libelleProfession;
	}

	public void setLibelleProfession(String libelleProfession) {
		this.libelleProfession = libelleProfession;
	}
	
	
	
	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	@Override
	public String toString() {
		return libelleProfession;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProfession ^ (idProfession >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProfessionClient other = (ProfessionClient) obj;
		if (idProfession != other.idProfession)
			return false;
		return true;
	}
	
	
	
}