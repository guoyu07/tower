package com.tower.service.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;



public abstract class AbsDTO implements IDTO{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5848372546081971597L;

	/**
	 * 
	 */

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
