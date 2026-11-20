//package com.star.Ba.model;
//
//import java.io.Serializable;
//import java.util.List;
//import javax.persistence.*;
//
//
///**
// * The persistent class for the GARANTIE_PACK database table.
// * 
// */
//@Entity
//@Table(name="GARANTIE_PACK")
//@NamedQuery(name="GarantiePack.findAll", query="SELECT g FROM GarantiePack g")
//public class GarantiePack implements Serializable {
//	private static final long serialVersionUID = 1L;
//
//	@Id
//	@GeneratedValue(strategy=GenerationType.IDENTITY)
//	@Column(name="ID_GARPACK")
//	private long idGarpack;
//
//	//bi-directional many-to-one association to Adhesion
//	@OneToMany(mappedBy="garantiePack")
////	private List<Adhesion> adhesions;
//
//	//bi-directional many-to-one association to GarantieNonVie
//	@ManyToOne
//	@JoinColumn(name="ID_GARANTIE")
//	private GarantieNonVie garantieNonVie;
//
//	//bi-directional many-to-one association to Pack
//	@ManyToOne
//	@JoinColumn(name="ID_PACK")
//	private Pack pack;
//
//	//bi-directional many-to-one association to Proposition
//	@OneToMany(mappedBy="garantiePack")
//	private List<Proposition> propositions;
//
//	public GarantiePack() {
//	}
//
//	public long getIdGarpack() {
//		return this.idGarpack;
//	}
//
//	public void setIdGarpack(long idGarpack) {
//		this.idGarpack = idGarpack;
//	}
//
//	public List<Adhesion> getAdhesions() {
//		return this.adhesions;
//	}
//
//	public void setAdhesions(List<Adhesion> adhesions) {
//		this.adhesions = adhesions;
//	}
//
//	public Adhesion addAdhesion(Adhesion adhesion) {
//		getAdhesions().add(adhesion);
//		adhesion.setGarantiePack(this);
//
//		return adhesion;
//	}
//
//	public Adhesion removeAdhesion(Adhesion adhesion) {
//		getAdhesions().remove(adhesion);
//		adhesion.setGarantiePack(null);
//
//		return adhesion;
//	}
//
//	public GarantieNonVie getGarantieNonVie() {
//		return this.garantieNonVie;
//	}
//
//	public void setGarantieNonVie(GarantieNonVie garantieNonVie) {
//		this.garantieNonVie = garantieNonVie;
//	}
//
//	public Pack getPack() {
//		return this.pack;
//	}
//
//	public void setPack(Pack pack) {
//		this.pack = pack;
//	}
//
//	public List<Proposition> getPropositions() {
//		return this.propositions;
//	}
//
//	public void setPropositions(List<Proposition> propositions) {
//		this.propositions = propositions;
//	}
//
//	public Proposition addProposition(Proposition proposition) {
//		getPropositions().add(proposition);
//		proposition.setGarantiePack(this);
//
//		return proposition;
//	}
//
//	public Proposition removeProposition(Proposition proposition) {
//		getPropositions().remove(proposition);
//		proposition.setGarantiePack(null);
//
//		return proposition;
//	}
//
//}