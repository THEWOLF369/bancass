package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the NATURE_RISQ database table.
 * 
 */
@Entity
@Table(name="NATURE_RISQ")
@NamedQuery(name="NatureRisq.findAll", query="SELECT n FROM NatureRisq n")
public class NatureRisq implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="NATURE_RISQ_IDRISKMRH_GENERATOR", sequenceName="NATURERISQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="NATURE_RISQ_IDRISKMRH_GENERATOR")
	@Column(name="ID_RISK_MRH")
	private long idRiskMrh;

	@Column(name="LIBELLE_NATURE")
	private String libelleNature;

	//bi-directional many-to-one association to DetailMrh
	@OneToMany(mappedBy="natureRisq")
	private List<DetailMrh> detailMrhs;

	public NatureRisq() {
	}

	public long getIdRiskMrh() {
		return this.idRiskMrh;
	}

	public void setIdRiskMrh(long idRiskMrh) {
		this.idRiskMrh = idRiskMrh;
	}

	public String getLibelleNature() {
		return this.libelleNature;
	}

	public void setLibelleNature(String libelleNature) {
		this.libelleNature = libelleNature;
	}

	public List<DetailMrh> getDetailMrhs() {
		return this.detailMrhs;
	}

	public void setDetailMrhs(List<DetailMrh> detailMrhs) {
		this.detailMrhs = detailMrhs;
	}

	public DetailMrh addDetailMrh(DetailMrh detailMrh) {
		getDetailMrhs().add(detailMrh);
		detailMrh.setNatureRisq(this);

		return detailMrh;
	}

	public DetailMrh removeDetailMrh(DetailMrh detailMrh) {
		getDetailMrhs().remove(detailMrh);
		detailMrh.setNatureRisq(null);

		return detailMrh;
	}

 

}