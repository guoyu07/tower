package com.tower.service.rule.impl;

import org.kie.api.KieServices;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSessionConfiguration;

import com.tower.service.rule.IEngine;

public interface IDroolsEngine extends IEngine {

	public abstract void setKieService(KieServices kieService);

	public abstract KieServices getKieService();

	public abstract KieContainer getContainer();

	public abstract void setkContainer(KieContainer kContainer);

	public abstract TowerDroolsSession getSession(String sessionName);

	public abstract TowerDroolsSession getSession(KieSessionConfiguration conf);

	public abstract TowerDroolsSession getSession(Environment environment);

	public abstract TowerDroolsSession getSession(Environment environment,
			KieSessionConfiguration conf);

	public abstract TowerDroolsSession getSession(String kSessionName,
			Environment environment);

	public abstract TowerDroolsSession getSession(String kSessionName,
			KieSessionConfiguration conf);

	public abstract TowerDroolsSession getSession(String kSessionName,
			Environment environment, KieSessionConfiguration conf);

	public abstract StatelessTowerDroolsSession getStatelessSession();

	public abstract StatelessTowerDroolsSession getStatelessSession(
			KieSessionConfiguration conf);

	public abstract StatelessTowerDroolsSession getStatelessSession(
			String kSessionName);

	public abstract StatelessTowerDroolsSession getStatelessSession(
			String kSessionName, KieSessionConfiguration conf);

	public abstract String getFileBasePath();

	public abstract void setFileBasePath(String fileBasePath);

	public abstract KieFileSystem getFileSystem();

	public abstract void setFileSystem(KieFileSystem fileSystem);

	public abstract void setKieBaseName(String kieBaseName);

	public abstract String getKieBaseName();

	public abstract void setPackages(String packages);

	public abstract String getPackages();

	public abstract void setSessionName(String sessionName);

	public abstract String getSessionName();

	public abstract void refreshRule(String ruleFile);

}