package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the COMGOUV_MRH database table.
 * 
 */
@Entity
@Table(name="COMGOUV_MRH")
@NamedQuery(name="ComgouvMrh.findAll", query="SELECT c FROM ComgouvMrh c")
public class ComgouvMrh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="COMGOUV_MRH_IDCOMGOUV_GENERATOR", sequenceName="COMGOUVMRH")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="COMGOUV_MRH_IDCOMGOUV_GENERATOR")
	@Column(name="ID_COMGOUV")
	private long idComgouv;

	private String gouvernora;

	@Column(name="LIB_ZONE")
	private String libZone;

	private int taux;

	public ComgouvMrh() {
	}

	public long getIdComgouv() {
		return this.idComgouv;
	}

	public void setIdComgouv(long idComgouv) {
		this.idComgouv = idComgouv;
	}

	public String getGouvernora() {
		return this.gouvernora;
	}

	public void setGouvernora(String gouvernora) {
		this.gouvernora = gouvernora;
	}

	public String getLibZone() {
		return this.libZone;
	}

	public void setLibZone(String libZone) {
		this.libZone = libZone;
	}

	public int getTaux() {
		return this.taux;
	}

	public void setTaux(int taux) {
		this.taux = taux;
	}

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((gouvernora == null) ? 0 : gouvernora.hashCode());
		result = prime * result + (int) (idComgouv ^ (idComgouv >>> 32));
		result = prime * result + ((libZone == null) ? 0 : libZone.hashCode());
		result = prime * result + taux;
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
		ComgouvMrh other = (ComgouvMrh) obj;
		if (gouvernora == null) {
			if (other.gouvernora != null)
				return false;
		} else if (!gouvernora.equals(other.gouvernora))
			return false;
		if (idComgouv != other.idComgouv)
			return false;
		if (libZone == null) {
			if (other.libZone != null)
				return false;
		} else if (!libZone.equals(other.libZone))
			return false;
		if (taux != other.taux)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ComgouvMrh [idComgouv=" + idComgouv + ", gouvernora="
				+ gouvernora + ", libZone=" + libZone + ", taux=" + taux + "]";
	}
	
	
}