package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LOCGOUV database table.
 * 
 */
@Entity
@NamedQueries({
	@NamedQuery(name="Locgouv.findAll", query="SELECT l FROM Locgouv l order by l.codePosta asc"),
	@NamedQuery(name="Locgouv.findByPostaCode", query="SELECT l FROM Locgouv l WHERE l.codePosta = :codePosta"),
	@NamedQuery(name="Locgouv.findByPostaCodeAndLocalite", query="SELECT l FROM Locgouv l WHERE l.codePosta = :codePosta and l.localite= :localite"),
	@NamedQuery(name="Locgouv.findGouv", query="SELECT distinct l.gouvernora FROM Locgouv l order by l.gouvernora"),
	@NamedQuery(name="Locgouv.findByGouv", query="SELECT l FROM Locgouv l WHERE l.gouvernora = :gouvernora order by l.codePosta"),
	@NamedQuery(name="Locgouv.findByCode", query="SELECT l FROM Locgouv l WHERE l.code = :code")
})
public class Locgouv implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long code;

	@Column(name="CODE_POSTA")
	private String codePosta;

	private String delegation;

	private String gouvernora;

	private String localite;

	public Locgouv() {
	}

	public long getCode() {
		return this.code;
	}

	public void setCode(long code) {
		this.code = code;
	}

	public String getCodePosta() {
		return this.codePosta;
	}

	public void setCodePosta(String codePosta) {
		this.codePosta = codePosta;
	}

	public String getDelegation() {
		return this.delegation;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public String getGouvernora() {
		return this.gouvernora;
	}

	public void setGouvernora(String gouvernora) {
		this.gouvernora = gouvernora;
	}

	public String getLocalite() {
		return this.localite;
	}

	public void setLocalite(String localite) {
		this.localite = localite;
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
		Locgouv other = (Locgouv) obj;
		if (code != other.code)
			return false;
		return true;
	}
}