package com.tower.service.domain;

import org.apache.commons.lang.builder.ToStringBuilder;



public abstract class AbsDTO implements IDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5848372546081971597L;

	/**
	 * 
	 */

	public String toString(){
    	return ToStringBuilder.reflectionToString(this);
    }
}
