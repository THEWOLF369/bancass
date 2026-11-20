package com.star.Ba.model;

import java.io.Serializable;


import javax.persistence.*;



/**
 * The persistent class for the SPECULATION database table.
 * 
 */
@Entity
@Table(name = "SPECULATION")
@NamedQueries({
	@NamedQuery(name = "Speculation.findAll", query = "SELECT a FROM Speculation a"),
	@NamedQuery(name = "Speculation.findBycode", query = "SELECT a FROM Speculation a WHERE a.code_spec = :code_spec")})
public class Speculation implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ID_SPEC")
	@SequenceGenerator(name = "ID_SPEC", sequenceName = "ID_SPEC", allocationSize = 1)
	@Column(name = "ID_SPEC")
	private int id_spec;
	
	@Column(name = "CODE_SPEC")
	private int code_spec;
	
	@Column(name = "LIBELLE_SPEC")
	private String libelle_spec;
	
	@Column(name = "CODE_PROD_SPEC")
	private int code_prod_spec;
	
	@Column(name = "LIBELLE_PROD_SPEC")
	private String libelle_prod_spec;
	
	@Column(name = "TYPE_PACK")
	private int type_pack;
	
	@Column(name = "NATURE")
	private String nature;
	
	@Column(name = "CLASSE")
	private String classe;
	
	@Column(name = "PRIX")
	private Integer prix;

	public Speculation(int id_spec, int code_spec, String libelle_spec,
			int code_prod_spec, String libelle_prod_spec, int type_pack,
			String nature, String classe, Integer prix) {
		super();
		this.id_spec = id_spec;
		this.code_spec = code_spec;
		this.libelle_spec = libelle_spec;
		this.code_prod_spec = code_prod_spec;
		this.libelle_prod_spec = libelle_prod_spec;
		this.type_pack = type_pack;
		this.nature = nature;
		this.classe = classe;
		this.prix = prix;
	}

	public Speculation() {
	
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classe == null) ? 0 : classe.hashCode());
		result = prime * result + code_prod_spec;
		result = prime * result + code_spec;
		result = prime * result + id_spec;
		result = prime * result
				+ ((libelle_spec == null) ? 0 : libelle_spec.hashCode());
		result = prime
				* result
				+ ((libelle_prod_spec == null) ? 0 : libelle_prod_spec
						.hashCode());
		result = prime * result + ((nature == null) ? 0 : nature.hashCode());
		result = prime * result + prix;
		result = prime * result + type_pack;
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
		Speculation other = (Speculation) obj;
		if (classe == null) {
			if (other.classe != null)
				return false;
		} else if (!classe.equals(other.classe))
			return false;
		if (code_prod_spec != other.code_prod_spec)
			return false;
		if (code_spec != other.code_spec)
			return false;
		if (id_spec != other.id_spec)
			return false;
		if (libelle_spec == null) {
			if (other.libelle_spec != null)
				return false;
		} else if (!libelle_spec.equals(other.libelle_spec))
			return false;
		if (libelle_prod_spec == null) {
			if (other.libelle_prod_spec != null)
				return false;
		} else if (!libelle_prod_spec.equals(other.libelle_prod_spec))
			return false;
		if (nature == null) {
			if (other.nature != null)
				return false;
		} else if (!nature.equals(other.nature))
			return false;
		if (prix != other.prix)
			return false;
		if (type_pack != other.type_pack)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Speculation [id_spec=" + id_spec + ", code_spec=" + code_spec
				+ ", libelle_spec=" + libelle_spec + ", code_prod_spec="
				+ code_prod_spec + ", libelle_prod_spec=" + libelle_prod_spec
				+ ", type_pack=" + type_pack + ", nature=" + nature
				+ ", classe=" + classe + ", prix=" + prix + "]";
	}

	public int getId_spec() {
		return id_spec;
	}

	public void setId_spec(int id_spec) {
		this.id_spec = id_spec;
	}

	public int getCode_spec() {
		return code_spec;
	}

	public void setCode_spec(int code_spec) {
		this.code_spec = code_spec;
	}

	public String getLibelle_spec() {
		return libelle_spec;
	}

	public void setLibelle_spec(String libelle_spec) {
		this.libelle_spec = libelle_spec;
	}

	public int getCode_prod_spec() {
		return code_prod_spec;
	}

	public void setCode_prod_spec(int code_prod_spec) {
		this.code_prod_spec = code_prod_spec;
	}

	public String getLibelle_prod_spec() {
		return libelle_prod_spec;
	}

	public void setLibelle_prod_spec(String libelle_prod_spec) {
		this.libelle_prod_spec = libelle_prod_spec;
	}

	public int getType_pack() {
		return type_pack;
	}

	public void setType_pack(int type_pack) {
		this.type_pack = type_pack;
	}

	public String getNature() {
		return nature;
	}

	public void setNature(String nature) {
		this.nature = nature;
	}

	public String getClasse() {
		return classe;
	}

	public void setClasse(String classe) {
		this.classe = classe;
	}

	public Integer getPrix() {
		return prix;
	}

	public void setPrix(Integer prix) {
		this.prix = prix;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
		

   
	
	

	
	
	

	


}