package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the GARANTIE_VIE database table.
 * 
 */
@Entity
@Table(name="GARANTIE_VIE")
@NamedQuery(name="GarantieVie.findAll", query="SELECT g FROM GarantieVie g")
public class GarantieVie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="ID_GARANTIE")
	private long idGarantie;

	@Column(name="GAR_BASE")
	private String garBase;

	@Column(name="BASE_EXCLUSION")
	private String garExclusion;
	
	@Column(name="GAR_OPT")
	private String garOpt;
	
	@Column(name="OPT_EXCLUSION")
	private String optExclusion;

	//bi-directional many-to-one association to Police
	@ManyToOne
	@JoinColumn(name="N_POLICE")
	private Police police;

	public GarantieVie() {
	}

	public long getIdGarantie() {
		return this.idGarantie;
	}

	public void setIdGarantie(long idGarantie) {
		this.idGarantie = idGarantie;
	}

	public String getGarBase() {
		return this.garBase;
	}

	public void setGarBase(String garBase) {
		this.garBase = garBase;
	}

	public String getGarExclusion() {
		return this.garExclusion;
	}

	public void setGarExclusion(String garExclusion) {
		this.garExclusion = garExclusion;
	}

	public Police getPolice() {
		return this.police;
	}

	public void setPolice(Police police) {
		this.police = police;
	}

	public String getGarOpt() {
		return garOpt;
	}

	public void setGarOpt(String garOpt) {
		this.garOpt = garOpt;
	}

	public String getOptExclusion() {
		return optExclusion;
	}

	public void setOptExclusion(String optExclusion) {
		this.optExclusion = optExclusion;
	}
}