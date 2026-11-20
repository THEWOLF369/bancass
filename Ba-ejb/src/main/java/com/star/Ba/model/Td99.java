package com.star.Ba.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the TD_99 database table.
 * 
 */
@Entity
@Table(name="TD_99")
@NamedQuery(name="Td99.findAll", query="SELECT t FROM Td99 t")
public class Td99 implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TD_99_AGE_GENERATOR" )
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TD_99_AGE_GENERATOR")
	private long age;

	private BigDecimal lx;

	public Td99() {
	}

	public long getAge() {
		return this.age;
	}

	public void setAge(long age) {
		this.age = age;
	}

	public BigDecimal getLx() {
		return this.lx;
	}

	public void setLx(BigDecimal lx) {
		this.lx = lx;
	}

}