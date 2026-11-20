package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.*;

import java.util.List;


/**
 * The persistent class for the QUALITE database table.
 * 
 */
@Entity
@NamedQuery(name="Qualite.findAll", query="SELECT q FROM Qualite q")
public class Qualite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="QUALITE_IDQUALITE_GENERATOR", sequenceName="QUALITE")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="QUALITE_IDQUALITE_GENERATOR")
	@Column(name="ID_QUALITE")
	private long idQualite;

	private String qualite;

	//bi-directional many-to-one association to DetailMrh
	@OneToMany(mappedBy="qualite")
	private List<DetailMrh> detailMrhs;

	public Qualite() {
	}

	public long getIdQualite() {
		return this.idQualite;
	}

	public void setIdQualite(long idQualite) {
		this.idQualite = idQualite;
	}

	public String getQualite() {
		return this.qualite;
	}

	public void setQualite(String qualite) {
		this.qualite = qualite;
	}

	public List<DetailMrh> getDetailMrhs() {
		return this.detailMrhs;
	}

	public void setDetailMrhs(List<DetailMrh> detailMrhs) {
		this.detailMrhs = detailMrhs;
	}

	public DetailMrh addDetailMrh(DetailMrh detailMrh) {
		getDetailMrhs().add(detailMrh);
		detailMrh.setQualite(this);

		return detailMrh;
	}

	public DetailMrh removeDetailMrh(DetailMrh detailMrh) {
		getDetailMrhs().remove(detailMrh);
		detailMrh.setQualite(null);

		return detailMrh;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((detailMrhs == null) ? 0 : detailMrhs.hashCode());
		result = prime * result + (int) (idQualite ^ (idQualite >>> 32));
		result = prime * result + ((qualite == null) ? 0 : qualite.hashCode());
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
		Qualite other = (Qualite) obj;
		if (detailMrhs == null) {
			if (other.detailMrhs != null)
				return false;
		} else if (!detailMrhs.equals(other.detailMrhs))
			return false;
		if (idQualite != other.idQualite)
			return false;
		if (qualite == null) {
			if (other.qualite != null)
				return false;
		} else if (!qualite.equals(other.qualite))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Qualite [idQualite=" + idQualite + ", qualite=" + qualite
				+ ", detailMrhs=" + detailMrhs + "]";
	}

}