package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LOCGOUV database table.
 * 
 */
@Entity
@Table(name="TRANCHE_HAYYA")
@NamedQueries({
	@NamedQuery(name="TrancheHayya.findAll", query="SELECT l FROM TrancheHayya l order by l.id_tranche_hayya asc")
})
public class TrancheHayya implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_tranche_hayya;

	@Column(name="TRANCHE")
	private String tranche;

	 

	public TrancheHayya() {
	}



	public long getId_tranche_hayya() {
		return id_tranche_hayya;
	}



	public void setId_tranche_hayya(long id_tranche_hayya) {
		this.id_tranche_hayya = id_tranche_hayya;
	}



	public String getTranche() {
		return tranche;
	}



	public void setTranche(String tranche) {
		this.tranche = tranche;
	}



 
 
	 
	
}