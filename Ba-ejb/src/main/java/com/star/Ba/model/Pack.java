package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the PACK database table.
 * 
 */
@Entity
@NamedQuery(name="Pack.findAll", query="SELECT p FROM Pack p")
public class Pack implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PACK_IDPACK_GENERATOR", sequenceName="PACK")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PACK_IDPACK_GENERATOR")
	@Column(name="ID_PACK")
	private long idPack;

	//bi-directional many-to-one association to Produit_test
	@ManyToOne
	@JoinColumn(name="CODPROD")
	private Produit produit;

	private String libelle;

	//bi-directional many-to-one association to Adhesion_test
	@OneToMany(mappedBy="pack")
	private List<Adhesion> adhesions;
	
	//bi-directional many-to-one association to Proposition_test
		@OneToMany(mappedBy="pack")
		private List<Proposition> propositions;
		
		//bi-directional many-to-one association to Adhesion_test
		@OneToMany(mappedBy="pack")
		private List<TarifAssist> tarifAssists;

	public Pack() {
	}

	public long getIdPack() {
		return this.idPack;
	}

	public void setIdPack(long idPack) {
		this.idPack = idPack;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public String getLibelle() {
		return this.libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public List<Adhesion> getAdhesions() {
		return this.adhesions;
	}

	public void setAdhesions(List<Adhesion> adhesions) {
		this.adhesions = adhesions;
	}

	public Adhesion addAdhesion(Adhesion adhesion) {
		getAdhesions().add(adhesion);
		adhesion.setPack(this);

		return adhesion;
	}

	public Adhesion removeAdhesion(Adhesion adhesion) {
		getAdhesions().remove(adhesion);
		adhesion.setPack(null);

		return adhesion;
	}

	public List<Proposition> getPropositions() {
		return this.propositions;
	}

	public void setPropositions(List<Proposition> propositions) {
		this.propositions = propositions;
	}
	
	
	
	public List<TarifAssist> getTarifAssists() {
		return tarifAssists;
	}

	public void setTarifAssists(List<TarifAssist> tarifAssists) {
		this.tarifAssists = tarifAssists;
	}

//	@Override
//	public int hashCode() {
//		final int prime = 31;
//		int result = 1;
//		result = prime * result
//				+ ((adhesions == null) ? 0 : adhesions.hashCode());
//		result = prime * result + (int) (idPack ^ (idPack >>> 32));
//		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
//		result = prime * result + ((produit == null) ? 0 : produit.hashCode());
//		return result;
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (obj == null)
//			return false;
//		if (getClass() != obj.getClass())
//			return false;
//		Pack other = (Pack) obj;
//		if (adhesions == null) {
//			if (other.adhesions != null)
//				return false;
//		} else if (!adhesions.equals(other.adhesions))
//			return false;
//		if (idPack != other.idPack)
//			return false;
//		if (libelle == null) {
//			if (other.libelle != null)
//				return false;
//		} else if (!libelle.equals(other.libelle))
//			return false;
//		if (produit == null) {
//			if (other.produit != null)
//				return false;
//		} else if (!produit.equals(other.produit))
//			return false;
//		return true;
//	}
//
//	@Override
//	public String toString() {
//		return "Pack [idPack=" + idPack + ", produit=" + produit + ", libelle="
//				+ libelle + ", adhesions=" + adhesions + "]";
//	}

}