package com.star.Ba.dao;

import java.math.BigDecimal;

import com.star.Ba.model.AgeServiceBourse;

public interface IAgeServiceBourseDAO {

	AgeServiceBourse getAgeServiceBourse_by_Age(BigDecimal ageServiceBourse);

}
