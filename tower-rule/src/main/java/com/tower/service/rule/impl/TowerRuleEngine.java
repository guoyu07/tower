/**
 * 
 */
package com.tower.service.rule.impl;

import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.tower.service.rule.IEngine;
import com.tower.service.rule.IFact;

/**
 * @author alexzhu
 * 
 */
public abstract class TowerRuleEngine<T extends IFact> implements IEngine<T> {
	
	private static KieServices kieService;
	private KieContainer kContainer;
	
	public KieContainer getContainer() {
		return kContainer;
	}

	public String getName() {
		return name;
	}

	private String name;
	public TowerRuleEngine(String name,T fact){
		this.name = name;
		doInit();
		doExecute();
	}
	private void doInit(){
		synchronized(TowerRuleEngine.this){
			if(kieService==null){
				kieService = KieServices.Factory.get();
			}
			if(kContainer==null){
				kContainer = kieService.getKieClasspathContainer();
			}
		}
		init();
	}
	public abstract KieSession getSession();
	public abstract List<Command<T>> getCmds();
	
	private void doExecute(){
		KieSession session = this.getSession();
		KieCommands kieCommands = kieService.getCommands();
		List<Command<T>> cmds = this.getCmds();
		execute(cmds);
		ExecutionResults results = null; 
		session.execute(kieCommands.newBatchExecution( cmds ) );
		session.fireAllRules();
	}
	@Override
	public void init() {
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
	}

	@Override
	public void refresh() {
		init();
	}
}
