package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the GARANTIE_AVJ database table.
 * 
 */
@Embeddable
public class GarantieAvjPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_GARANTIE")
	private long idGarantie;

	@Column(name="N_POLICE")
	private long nPolice;

	public GarantieAvjPK() {
	}
	public long getIdGarantie() {
		return this.idGarantie;
	}
	public void setIdGarantie(long idGarantie) {
		this.idGarantie = idGarantie;
	}
	public long getNPolice() {
		return this.nPolice;
	}
	public void setNPolice(long nPolice) {
		this.nPolice = nPolice;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GarantieAvjPK)) {
			return false;
		}
		GarantieAvjPK castOther = (GarantieAvjPK)other;
		return 
			(this.idGarantie == castOther.idGarantie)
			&& (this.nPolice == castOther.nPolice);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idGarantie ^ (this.idGarantie >>> 32)));
		hash = hash * prime + ((int) (this.nPolice ^ (this.nPolice >>> 32)));
		
		return hash;
	}
}