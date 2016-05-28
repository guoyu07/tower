/**
 * 
 */
package com.tower.service.rule.impl;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.builder.ReleaseId;
import org.kie.api.runtime.KieContainer;

import com.tower.service.rule.IEngine;
import com.tower.service.rule.IFact;

/**
 * @author alexzhu
 * 
 */
public abstract class TimeDynamicEngine<T extends IFact> extends DynamicEngine<T> implements IEngine<T> {
	
	public TimeDynamicEngine(){
		super();
	}
	
	private long pollingInterval=10000L;
	
	public long getPollingInterval() {
		return pollingInterval;
	}

	public void setPollingInterval(long pollingInterval) {
		this.pollingInterval = pollingInterval;
	}

	public void refresh(){
		KieServices kieServices = getKieService();
		ReleaseId releaseId = kieServices.newReleaseId( this.getGroupId(), this.getArtifactId(), this.getVersion() );
		KieContainer kContainer = kieServices.newKieContainer( releaseId );
		KieScanner kScanner = kieServices.newKieScanner( kContainer );
		// Start the KieScanner polling the Maven repository every 10 seconds
		kScanner.start( this.getPollingInterval() );
	}
}
