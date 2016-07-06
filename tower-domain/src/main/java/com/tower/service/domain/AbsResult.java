package com.tower.service.domain;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class AbsResult implements IResult{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 244997433342542672L;

	public String toString(){
    	return ToStringBuilder.reflectionToString(this);
    }
}
