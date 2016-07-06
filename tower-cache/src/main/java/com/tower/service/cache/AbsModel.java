package com.tower.service.cache;

import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class AbsModel implements IModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4470326630561962443L;

	public String toString(){
    	return ToStringBuilder.reflectionToString(this);
    }
}
