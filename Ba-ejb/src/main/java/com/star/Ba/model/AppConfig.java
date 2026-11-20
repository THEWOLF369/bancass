package com.star.Ba.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="APP_CONFIG")
public class AppConfig implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="CODE_PARAM")
	    private String code;

	    @Column(name="VALUE_PARAM")
	    private String value;
	    
	    @Column(name="TYPE_PARAM")
	    private String type;

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = value;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}
	    
	    
	    
	    

}
