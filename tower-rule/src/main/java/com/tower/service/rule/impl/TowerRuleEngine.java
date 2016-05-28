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
import org.kie.api.command.Command;
import org.kie.api.io.KieResources;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;

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

	public TowerRuleEngine() {
		System.setProperty("drools.dateformat", "yyyy-MM-dd HH:mm:ss");
	}

	public static KieServices getKieService() {
		return kieService;
	}

	public abstract KieSession getSession();

	public abstract List<Command<T>> getCmds();

	@Override
	public void init() {
		resources = getKieService().getResources();
		KieModuleModel kieModuleModel = getKieService().newKieModuleModel();// 1

		KieBaseModel baseModel = kieModuleModel.newKieBaseModel(
				"FileSystemKBase").addPackage("rules");// 2
		baseModel.newKieSessionModel("FileSystemKSession");// 3

		fileSystem = getKieService().newKieFileSystem();

		String xml = kieModuleModel.toXML();
		System.out.println(xml);
		fileSystem.writeKModuleXML(xml);// 5
		String fileBasePath = Thread.currentThread().getContextClassLoader()
				.getResource("").getPath();
		System.out.println(fileBasePath);
		fileBasePath = fileBasePath.substring(0, fileBasePath.length());

		List<String> fileList = null;//GetAndReadAllFile.getFileList(new File(fileBasePath + "\\com\\yeepay\\ytf\\rules"), "drl");
		for (String sfile : fileList) {
			fileSystem.write("src/main/resources/rules/Rule.drl",
					resources.newFileSystemResource(new File(sfile)));// 6
		}

		KieBuilder kb = getKieService().newKieBuilder(fileSystem);
		kb.buildAll();// 7
		if (kb.getResults().hasMessages(Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n"
					+ kb.getResults().toString());
		}
		kContainer = getKieService().newKieContainer(
				getKieService().getRepository().getDefaultReleaseId());
	}

	@Override
	public void refresh() {
		fileSystem.write("src/main/resources/rules/UploadRule.drl",
				resources.newFileSystemResource(path));// 6
		KieBuilder kb = getKieService().newKieBuilder(fileSystem);
		kb.buildAll();// 7
		if (kb.getResults().hasMessages(Level.ERROR)) {
			throw new RuntimeException("Build Errors:\n"
					+ kb.getResults().toString());
		}
		kContainer = getKieService().newKieContainer(
				getKieService().getRepository().getDefaultReleaseId());

		// kSession = kContainer.newKieSession("FileSystemKSession");

	}
}
