package com.star.Ba.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

/**
 * The persistent class for the PRODCONV database table.
 * 
 */
@Entity
@NamedQuery(name = "Prodconv.findAll", query = "SELECT p FROM Prodconv p")
public class Prodconv implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_PRODCONV")
	@SequenceGenerator(name = "ID_PRODCONV", sequenceName = "ID_PRODCONV", allocationSize = 1)
	@Column(name = "ID_PRODCONV")
	private long idProdconv;

	// bi-directional many-to-one association to Convention
	@ManyToOne
	@JoinColumn(name = "ID_CONVENTION")
	private Convention convention;

	// bi-directional many-to-one association to Produit
	@ManyToOne
	@JoinColumn(name = "ID_PRODUIT")
	private Produit produit;
	@Column(name = "CODEPRODUIT")
	private String codeProduit;

	@OneToMany(mappedBy = "idprodconv", fetch = FetchType.LAZY)
	private List<TarifGrele> tarifGreleList;
	@OneToMany(mappedBy = "idprodconv", fetch = FetchType.LAZY)
	private List<Contenu> contenuList;
	@OneToMany(mappedBy = "idprodconv", fetch = FetchType.EAGER)
	private List<TarifIncendieAgr> tarifIncendieAgrList;

	@OneToMany(mappedBy = "idProdconv")
    private List<Tauxcomm> tauxcommList;
    @OneToMany(mappedBy = "idProdconv")
    private List<Tauxreduction> tauxreductionList;

	public Prodconv() {
	}

	
	public List<Tauxreduction> getTauxreductionList() {
		return tauxreductionList;
	}
	public void setTauxreductionList(List<Tauxreduction> tauxreductionList) {
		this.tauxreductionList = tauxreductionList;
	}
	public List<Tauxcomm> getTauxcommList() {
		return tauxcommList;
	}
	public void setTauxcommList(List<Tauxcomm> tauxcommList) {
		this.tauxcommList = tauxcommList;
	}

	public Prodconv(Convention convention, Produit produit) {
		this.convention = convention;
		this.produit = produit;
	}

	public String getCodeProduit() {
		return codeProduit;
	}

	public void setCodeProduit(String codeProduit) {
		this.codeProduit = codeProduit;
	}

	public List<TarifIncendieAgr> getTarifIncendieAgrList() {
		return tarifIncendieAgrList;
	}

	public void setTarifIncendieAgrList(List<TarifIncendieAgr> tarifIncendieAgrList) {
		this.tarifIncendieAgrList = tarifIncendieAgrList;
	}

	public List<Contenu> getContenuList() {
		return contenuList;
	}

	public void setContenuList(List<Contenu> contenuList) {
		this.contenuList = contenuList;
	}

	public long getIdProdconv() {
		return this.idProdconv;
	}

	public void setIdProdconv(long idProdconv) {
		this.idProdconv = idProdconv;
	}

	public Convention getConvention() {
		return this.convention;
	}

	public void setConvention(Convention convention) {
		this.convention = convention;
	}

	public Produit getProduit() {
		return this.produit;
	}

	public void setProduit(Produit produit) {
		this.produit = produit;
	}

	public List<TarifGrele> getTarifGreleList() {
		return tarifGreleList;
	}

	public void setTarifGreleList(List<TarifGrele> tarifGreleList) {
		this.tarifGreleList = tarifGreleList;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (idProdconv ^ (idProdconv >>> 32));
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
		Prodconv other = (Prodconv) obj;
		if (idProdconv != other.idProdconv)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Prodconv [idProdconv=" + idProdconv + ", codeProduit=" + codeProduit + "]";
	}

}
