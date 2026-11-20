package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LOCGOUV database table.
 * 
 */
@Entity
@Table(name="FRACT_HAYYA")
@NamedQueries({
	@NamedQuery(name="FractHayya.findAll", query="SELECT l FROM FractHayya l order by l.id_fract_hayya asc")
})
public class FractHayya implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_fract_hayya;

	@Column(name="FRACTIONNEMENT")
	private String fractionnement;

	 

	public FractHayya() {
	}



	public long getId_fract_hayya() {
		return id_fract_hayya;
	}



	public void setId_fract_hayya(long id_fract_hayya) {
		this.id_fract_hayya = id_fract_hayya;
	}



	public String getFractionnement() {
		return fractionnement;
	}



	public void setFractionnement(String fractionnement) {
		this.fractionnement = fractionnement;
	}

	 
	
}