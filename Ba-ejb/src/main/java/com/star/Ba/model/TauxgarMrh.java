package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.math.BigDecimal;


/**
 * The persistent class for the TAUXGAR_MRH database table.
 * 
 */
@Entity
@Table(name="TAUXGAR_MRH")
@NamedQuery(name="TauxgarMrh.findAll", query="SELECT t FROM TauxgarMrh t")
public class TauxgarMrh implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TAUXGAR_MRH_CODEGAR_GENERATOR", sequenceName="TAUXGARMRH")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TAUXGAR_MRH_CODEGAR_GENERATOR")
	@Column(name="CODE_GAR")
	private String codeGar;

	@Column(name="LIB_GAR")
	private String libGar;

	private String pack1;

	private String pack2;

	private String pack3;

	private BigDecimal tauxgarpac1;

	private BigDecimal tauxgarpac2;

	private BigDecimal tauxgarpac3;
	
	private String pack4;

	private String pack5;
	
	private BigDecimal tauxgarpac4;

	private BigDecimal tauxgarpac5;


	@Column(name="TYPE_GAR")
	private String typeGar;

	public TauxgarMrh() {
	}

	public String getCodeGar() {
		return this.codeGar;
	}

	public void setCodeGar(String codeGar) {
		this.codeGar = codeGar;
	}

	public String getLibGar() {
		return this.libGar;
	}

	public void setLibGar(String libGar) {
		this.libGar = libGar;
	}

	public String getPack1() {
		return this.pack1;
	}

	public void setPack1(String pack1) {
		this.pack1 = pack1;
	}

	public String getPack2() {
		return this.pack2;
	}

	public void setPack2(String pack2) {
		this.pack2 = pack2;
	}

	public String getPack3() {
		return this.pack3;
	}

	public void setPack3(String pack3) {
		this.pack3 = pack3;
	}

	public BigDecimal getTauxgarpac1() {
		return this.tauxgarpac1;
	}

	public void setTauxgarpac1(BigDecimal tauxgarpac1) {
		this.tauxgarpac1 = tauxgarpac1;
	}

	public BigDecimal getTauxgarpac2() {
		return this.tauxgarpac2;
	}

	public void setTauxgarpac2(BigDecimal tauxgarpac2) {
		this.tauxgarpac2 = tauxgarpac2;
	}

	public BigDecimal getTauxgarpac3() {
		return this.tauxgarpac3;
	}

	public void setTauxgarpac3(BigDecimal tauxgarpac3) {
		this.tauxgarpac3 = tauxgarpac3;
	}

	public String getTypeGar() {
		return this.typeGar;
	}

	public void setTypeGar(String typeGar) {
		this.typeGar = typeGar;
	}

	
	public String getPack4() {
		return pack4;
	}

	public void setPack4(String pack4) {
		this.pack4 = pack4;
	}

	public String getPack5() {
		return pack5;
	}

	public void setPack5(String pack5) {
		this.pack5 = pack5;
	}

	public BigDecimal getTauxgarpac4() {
		return tauxgarpac4;
	}

	public void setTauxgarpac4(BigDecimal tauxgarpac4) {
		this.tauxgarpac4 = tauxgarpac4;
	}

	public BigDecimal getTauxgarpac5() {
		return tauxgarpac5;
	}

	public void setTauxgarpac5(BigDecimal tauxgarpac5) {
		this.tauxgarpac5 = tauxgarpac5;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codeGar == null) ? 0 : codeGar.hashCode());
		result = prime * result + ((libGar == null) ? 0 : libGar.hashCode());
		result = prime * result + ((pack1 == null) ? 0 : pack1.hashCode());
		result = prime * result + ((pack2 == null) ? 0 : pack2.hashCode());
		result = prime * result + ((pack3 == null) ? 0 : pack3.hashCode());
		result = prime * result + ((pack4 == null) ? 0 : pack4.hashCode());
		result = prime * result + ((pack5 == null) ? 0 : pack5.hashCode());
		result = prime * result
				+ ((tauxgarpac1 == null) ? 0 : tauxgarpac1.hashCode());
		result = prime * result
				+ ((tauxgarpac2 == null) ? 0 : tauxgarpac2.hashCode());
		result = prime * result
				+ ((tauxgarpac3 == null) ? 0 : tauxgarpac3.hashCode());
		result = prime * result
				+ ((tauxgarpac4 == null) ? 0 : tauxgarpac4.hashCode());
		result = prime * result
				+ ((tauxgarpac5 == null) ? 0 : tauxgarpac5.hashCode());
		result = prime * result + ((typeGar == null) ? 0 : typeGar.hashCode());
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
		TauxgarMrh other = (TauxgarMrh) obj;
		if (codeGar == null) {
			if (other.codeGar != null)
				return false;
		} else if (!codeGar.equals(other.codeGar))
			return false;
		if (libGar == null) {
			if (other.libGar != null)
				return false;
		} else if (!libGar.equals(other.libGar))
			return false;
		if (pack1 == null) {
			if (other.pack1 != null)
				return false;
		} else if (!pack1.equals(other.pack1))
			return false;
		if (pack2 == null) {
			if (other.pack2 != null)
				return false;
		} else if (!pack2.equals(other.pack2))
			return false;
		if (pack3 == null) {
			if (other.pack3 != null)
				return false;
		} else if (!pack3.equals(other.pack3))
			return false;
		if (pack4 == null) {
			if (other.pack4 != null)
				return false;
		} else if (!pack4.equals(other.pack4))
			return false;
		if (pack5 == null) {
			if (other.pack5 != null)
				return false;
		} else if (!pack5.equals(other.pack5))
			return false;
		if (tauxgarpac1 == null) {
			if (other.tauxgarpac1 != null)
				return false;
		} else if (!tauxgarpac1.equals(other.tauxgarpac1))
			return false;
		if (tauxgarpac2 == null) {
			if (other.tauxgarpac2 != null)
				return false;
		} else if (!tauxgarpac2.equals(other.tauxgarpac2))
			return false;
		if (tauxgarpac3 == null) {
			if (other.tauxgarpac3 != null)
				return false;
		} else if (!tauxgarpac3.equals(other.tauxgarpac3))
			return false;
		if (tauxgarpac4 == null) {
			if (other.tauxgarpac4 != null)
				return false;
		} else if (!tauxgarpac4.equals(other.tauxgarpac4))
			return false;
		if (tauxgarpac5 == null) {
			if (other.tauxgarpac5 != null)
				return false;
		} else if (!tauxgarpac5.equals(other.tauxgarpac5))
			return false;
		if (typeGar == null) {
			if (other.typeGar != null)
				return false;
		} else if (!typeGar.equals(other.typeGar))
			return false;
		return true;
	}
	

	@Override
	public String toString() {
		return "TauxgarMrh [codeGar=" + codeGar + ", libGar=" + libGar
				+ ", pack1=" + pack1 + ", pack2=" + pack2 + ", pack3=" + pack3
				+ ", tauxgarpac1=" + tauxgarpac1 + ", tauxgarpac2="
				+ tauxgarpac2 + ", tauxgarpac3=" + tauxgarpac3 + ", pack4="
				+ pack4 + ", pack5=" + pack5 + ", tauxgarpac4=" + tauxgarpac4
				+ ", tauxgarpac5=" + tauxgarpac5 + ", typeGar=" + typeGar + "]";
	}

	
}