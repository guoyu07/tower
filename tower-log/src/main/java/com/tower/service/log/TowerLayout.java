package com.tower.service.log;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.LoggingEvent;

import com.tower.service.util.Request;

public class TowerLayout extends PatternLayout {

	 
	  public String format(LoggingEvent event) {
		
	    return  ""+Request.getId()+"|"+super.doLayout(event);
	  }
}
