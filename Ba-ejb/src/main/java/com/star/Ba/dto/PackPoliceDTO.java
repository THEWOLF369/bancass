package com.star.Ba.dto;

import java.util.List;

import com.star.Ba.model.PackPolice;

public class PackPoliceDTO {
	private List<PackPolice> list;
	private String msg;
	private int etat;

	public PackPoliceDTO() {
	}

	public List<PackPolice> getList() {
		return list;
	}

	public void setList(List<PackPolice> list) {
		this.list = list;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

}
