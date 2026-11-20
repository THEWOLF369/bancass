package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the LOCGOUV database table.
 * 
 */
@Entity
@Table(name="CROISS_HAYYA")
@NamedQueries({
	@NamedQuery(name="CroissHayya.findAll", query="SELECT l FROM CroissHayya l order by l.id_croiss_hayya asc")
})
public class CroissHayya implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long id_croiss_hayya;

	@Column(name="CROISSANCE")
	private String croissance;

	 

	public CroissHayya() {
	}



 


	public long getId_croiss_hayya() {
		return id_croiss_hayya;
	}






	public void setId_croiss_hayya(long id_croiss_hayya) {
		this.id_croiss_hayya = id_croiss_hayya;
	}






	public String getCroissance() {
		return croissance;
	}



	public void setCroissance(String croissance) {
		this.croissance = croissance;
	}



	 
	
}