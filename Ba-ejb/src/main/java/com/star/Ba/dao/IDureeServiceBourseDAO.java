package com.star.Ba.dao;

import java.math.BigDecimal;

import com.star.Ba.model.DureServiceBourse;

public interface IDureeServiceBourseDAO {

	DureServiceBourse getDureeServiceBourse_by_Duree(BigDecimal dure);

}
