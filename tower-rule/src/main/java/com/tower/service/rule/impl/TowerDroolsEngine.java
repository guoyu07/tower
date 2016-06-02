/**
 * 
 */
package com.tower.service.rule.impl;

import java.io.File;
import java.util.List;

import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSessionConfiguration;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.IEngine;

/**
 * @author alexzhu
 * 
 */
public abstract class TowerDroolsEngine implements IEngine {

	private String kieBaseName = "FileSystemBase";
	private String packages = "rules";
	private String sessionName = "FileSystemKSession";
	private String fileBasePath=Thread.currentThread().getContextClassLoader()
			.getResource("").getPath();
	private static KieServices kieService = KieServices.Factory.get();
	private KieContainer kContainer = kieService.getKieClasspathContainer();

	private KieResources resources;
	private KieFileSystem fileSystem;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public TowerDroolsEngine() {
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
	}

	public KieContainer getContainer() {
		return kContainer;
	}

	public void setkContainer(KieContainer kContainer) {
		this.kContainer = kContainer;
	}

	public TowerDroolsSession getSession(String sessionName) {
		return new TowerDroolsSession(kContainer.newKieSession(sessionName));
	}
	
	public TowerDroolsSession getSession(KieSessionConfiguration conf){
		return new TowerDroolsSession(kContainer.newKieSession(conf));
	}

	public TowerDroolsSession getSession(Environment environment){
		return new TowerDroolsSession(kContainer.newKieSession(environment));
	}

	public TowerDroolsSession getSession(Environment environment, KieSessionConfiguration conf){
		return new TowerDroolsSession(kContainer.newKieSession(environment,conf));
	}
	
	public TowerDroolsSession getSession(String kSessionName, Environment environment){
		return new TowerDroolsSession(kContainer.newKieSession(kSessionName,environment));
	}
	
	public TowerDroolsSession getSession(String kSessionName, KieSessionConfiguration conf){
		return new TowerDroolsSession(kContainer.newKieSession(kSessionName,conf));
	}
	
	public TowerDroolsSession getSession(String kSessionName, Environment environment, KieSessionConfiguration conf){
		return new TowerDroolsSession(kContainer.newKieSession(kSessionName,environment,conf));
	}
	
	public StatelessTowerDroolsSession getStatelessSession(){
		return new StatelessTowerDroolsSession(kContainer.newStatelessKieSession());
	}
	
	public StatelessTowerDroolsSession getStatelessSession(KieSessionConfiguration conf){
		return new StatelessTowerDroolsSession(kContainer.newStatelessKieSession(conf));
	}
	
	public StatelessTowerDroolsSession getStatelessSession(String kSessionName){
		return new StatelessTowerDroolsSession(kContainer.newStatelessKieSession(kSessionName));
	}

	public StatelessTowerDroolsSession getStatelessSession(String kSessionName, KieSessionConfiguration conf){
		return new StatelessTowerDroolsSession(kContainer.newStatelessKieSession(kSessionName,conf));
	}
	
	public static KieServices getKieService() {
		return kieService;
	}
	
	public String getFileBasePath() {
		return fileBasePath;
	}

	public void setFileBasePath(String fileBasePath) {
		this.fileBasePath = fileBasePath;
	}

	public KieFileSystem getFileSystem() {
		return fileSystem;
	}

	public void setFileSystem(KieFileSystem fileSystem) {
		this.fileSystem = fileSystem;
	}

	public void setKieBaseName(String kieBaseName) {
		this.kieBaseName = kieBaseName;
	}

	public String getKieBaseName(){
		return kieBaseName;
	}
	
	public void setPackages(String packages) {
		this.packages = packages;
	}
	
	public String getPackages(){
		return packages;
	}

	public void setSessionName(String sessionName) {
		this.sessionName = sessionName;
	}

	public String getSessionName(){
		return sessionName;
	}
	
	public void refresh() {
		
		KieResources resources = getKieService().getResources();
		
		KieModuleModel kieModuleModel = getKieService().newKieModuleModel();// 1

		KieBaseModel baseModel = kieModuleModel.newKieBaseModel(
				this.getKieBaseName()).addPackage(getPackages());// 2
		
		baseModel.newKieSessionModel(getSessionName());// 3

		fileSystem = getKieService().newKieFileSystem();

		String xml = kieModuleModel.toXML();
		
		logger.info("KieModuleXml: "+xml);
		
		fileSystem.writeKModuleXML(xml);// 5
		
		logger.info("FileBathPath: "+fileBasePath);
		fileBasePath = fileBasePath.substring(0, fileBasePath.length());

		List<String> fileList=null;
		try {
			fileList = null;//FileUtils.getDirectoryNames(new File(fileBasePath), ".drl", null, false);
			for (String sfile : fileList) {
				fileSystem.write("/config/rules/Rule.drl",
						resources.newFileSystemResource(new File(sfile)));// 6
			}
			KieBuilder kb = getKieService().newKieBuilder(fileSystem);
			kb.buildAll();// 7
			if (kb.getResults().hasMessages(Level.ERROR)) {
				logger.error("Build Errors:\n"+kb.getResults().toString());
			}
			kContainer = getKieService().newKieContainer(
					getKieService().getRepository().getDefaultReleaseId());
		} catch (Exception e) {
			logger.error("Build Errors",e);
		}
	}

	public void refreshRule(String ruleFile){
		fileSystem.write(ruleFile,
				resources.newFileSystemResource(this.getFileBasePath()));// 6
		KieBuilder kb = getKieService().newKieBuilder(fileSystem);
		kb.buildAll();// 7
		if (kb.getResults().hasMessages(Level.ERROR)) {
			logger.error("Build Errors:\n"+kb.getResults().toString());
		}
		kContainer = getKieService().newKieContainer(
				getKieService().getRepository().getDefaultReleaseId());
	}
}
