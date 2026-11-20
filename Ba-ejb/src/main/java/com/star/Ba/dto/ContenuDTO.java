package com.star.Ba.dto;

import com.star.Ba.model.Contenu;

public class ContenuDTO {
	private Contenu c;
	private boolean checked = false;
	private boolean disabled = true;
	private boolean disabledCheckBox = false;
	private Integer obligatoire = 0;
	private Integer nextTab;

	public ContenuDTO() {
		// TODO Auto-generated constructor stub
	}

	public ContenuDTO(Contenu c, Integer obligatoire) {
		super();
		this.c = c;
		this.obligatoire = obligatoire;
	}

	public ContenuDTO(Contenu c) {
		super();
		this.c = c;
	}

	public Contenu getC() {
		return c;
	}

	public void setC(Contenu c) {
		this.c = c;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public Integer getObligatoire() {
		return obligatoire;
	}

	public void setObligatoire(Integer obligatoire) {
		this.obligatoire = obligatoire;
	}

	public void setDisabledCheckBox(boolean disabledCheckBox) {
		this.disabledCheckBox = disabledCheckBox;
	}

	public boolean isDisabledCheckBox() {
		return disabledCheckBox;
	}

	public Integer getNextTab() {
		return nextTab;
	}

	public void setNextTab(Integer nextTab) {
		this.nextTab = nextTab;
	}

	@Override
	public String toString() {
		return "ContenuDTO [c=" + c + ", checked=" + checked + ", disabled="
				+ disabled + ", disabledCheckBox=" + disabledCheckBox
				+ ", obligatoire=" + obligatoire + "]"+nextTab;
	}

}
