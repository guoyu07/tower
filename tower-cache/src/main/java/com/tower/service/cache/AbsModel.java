package com.tower.service.cache;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public abstract class AbsModel implements IModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4470326630561962443L;

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	public   boolean  equals(Object o)  {
	     return  EqualsBuilder.reflectionEquals( this , o);
	}
}
