package com.star.Ba.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;


/**
 * The persistent class for the TYPE_VOIE database table.
 * 
 */
@Entity
@Table(name="TYPE_VOIE")
@NamedQuery(name="TypeVoie.findAll", query="SELECT t FROM TypeVoie t")
public class TypeVoie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long code;

	private String libelle;

	@Column(name="LIBELLE_AB")
	private String libelleAb;

	// bi-directional many-to-one association to Adherent
	@OneToMany(mappedBy = "typeVoie")
	private List<Client> clients;
	
	public TypeVoie() {
	}

	public long getCode() {
		return this.code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getLibelleAb() {
		return this.libelleAb;
	}

	public void setLibelleAb(String libelleAb) {
		this.libelleAb = libelleAb;
	}

	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (code ^ (code >>> 32));
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
		TypeVoie other = (TypeVoie) obj;
		if (code != other.code)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return libelle;
	}
	
}