/**
 * 
 */
package com.tower.service.rule.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.codehaus.plexus.util.FileUtils;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message.Level;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.command.Command;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.IEngine;
import com.tower.service.rule.IFact;

/**
 * @author alexzhu
 * 
 */
public abstract class TowerRuleEngine<T extends IFact> implements IEngine<T> {

	private String path;
	private static KieServices kieService = KieServices.Factory.get();
	private KieContainer kContainer = kieService.getKieClasspathContainer();

	private KieResources resources;
	private KieFileSystem fileSystem;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	public TowerRuleEngine() {
		this(null);
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
	}

	public TowerRuleEngine(String sessionName) {
		kContainer = kieService.getKieClasspathContainer();
	}

	public KieContainer getContainer() {
		return kContainer;
	}

	public KieContainer getkContainer() {
		return kContainer;
	}

	public void setkContainer(KieContainer kContainer) {
		this.kContainer = kContainer;
	}

	public String getName() {
		return name;
	}

	private String name;

	public static KieServices getKieService() {
		return kieService;
	}
	
	public String getKieBaseName(){
		return "FileSystemBase";
	}
	
	public String getPackageName(){
		return "rules";
	}
	
	public String getSessionName(){
		return "FileSystemKSession";
	}
	
	public abstract KieSession build();

	public void refresh() {
		
		KieResources resources = getKieService().getResources();
		
		KieModuleModel kieModuleModel = getKieService().newKieModuleModel();// 1

		KieBaseModel baseModel = kieModuleModel.newKieBaseModel(
				this.getKieBaseName()).addPackage(getPackageName());// 2
		
		baseModel.newKieSessionModel(getSessionName());// 3

		fileSystem = getKieService().newKieFileSystem();

		String xml = kieModuleModel.toXML();
		
		logger.info("KieModuleXml: "+xml);
		
		fileSystem.writeKModuleXML(xml);// 5
		
		String fileBasePath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		logger.info("FileBathPath: "+fileBasePath);
		fileBasePath = fileBasePath.substring(0, fileBasePath.length());

		List<String> fileList=null;
		try {
			fileList = FileUtils.getDirectoryNames(new File(fileBasePath), ".drl", null, false);
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
		} catch (IOException e) {
			logger.error("Build Errors",e);
		}
	}

	public void refreshRule(String ruleFile){
		fileSystem.write(ruleFile,
				resources.newFileSystemResource(path));// 6
		KieBuilder kb = getKieService().newKieBuilder(fileSystem);
		kb.buildAll();// 7
		if (kb.getResults().hasMessages(Level.ERROR)) {
			logger.error("Build Errors:\n"+kb.getResults().toString());
		}
		kContainer = getKieService().newKieContainer(
				getKieService().getRepository().getDefaultReleaseId());
	}
	
	public void execute(){
		KieSession ksession = this.build();
		ksession.fireAllRules();
		ksession.dispose();
	}
}
