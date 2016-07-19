package com.tower.service.impl;

import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.Environment;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSessionConfiguration;
import org.springframework.beans.factory.InitializingBean;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tower.service.IService;
import com.tower.service.config.IConfigChangeListener;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.impl.IDroolsEngine;
import com.tower.service.rule.impl.StatelessTowerDroolsSession;
import com.tower.service.rule.impl.TowerDroolsEngine;
import com.tower.service.rule.impl.TowerDroolsSession;

public class RuleServiceImpl extends TowerDroolsEngine implements IService,IDroolsEngine,IConfigChangeListener, InitializingBean {
    
	
	private IDroolsEngine delegate;
    protected ServiceConfig config;
    
	/**
	 * Logger for this class
	 */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public RuleServiceImpl(){
	}
	
	protected static RpcContext context = RpcContext.getContext();
    @Override
    public String getRemoteHost() {
        return context.getRemoteHost();
    }

	@Override
	public void afterPropertiesSet() throws Exception {
		if(config!=null){
			config.addChangeListener(this);
		}
	}
	
	public ServiceConfig getConfig() {
		return config;
	}

	public void setConfig(ServiceConfig config) {
		this.config = config;
	}

	@Override
	public void configChanged() {
		
	}
	
	
	public KieContainer getContainer(){
		return delegate.getContainer();
	}

	public void setkContainer(KieContainer kContainer){
		delegate.setkContainer(kContainer);
	}

	public TowerDroolsSession getSession(String sessionName){
		return delegate.getSession(sessionName);
	}

	public TowerDroolsSession getSession(KieSessionConfiguration conf){
		return delegate.getSession(conf);
	}

	public TowerDroolsSession getSession(Environment environment){
		return delegate.getSession(environment);
	}

	public TowerDroolsSession getSession(Environment environment,
			KieSessionConfiguration conf){
		return delegate.getSession(environment, conf);
	}

	public TowerDroolsSession getSession(String kSessionName,
			Environment environment){
		return delegate.getSession(kSessionName, environment);
	}

	public TowerDroolsSession getSession(String kSessionName,
			KieSessionConfiguration conf){
		return delegate.getSession(kSessionName, conf);
	}

	public TowerDroolsSession getSession(String kSessionName,
			Environment environment, KieSessionConfiguration conf){
		return delegate.getSession(kSessionName, environment, conf);
	}

	public StatelessTowerDroolsSession getStatelessSession(){
		return delegate.getStatelessSession();
	}

	public StatelessTowerDroolsSession getStatelessSession(
			KieSessionConfiguration conf){
		return delegate.getStatelessSession(conf);
	}

	public StatelessTowerDroolsSession getStatelessSession(
			String kSessionName){
		return delegate.getStatelessSession(kSessionName);
	}

	public StatelessTowerDroolsSession getStatelessSession(
			String kSessionName, KieSessionConfiguration conf){
		return delegate.getStatelessSession(kSessionName, conf);
	}

	public String getFileBasePath(){
		return delegate.getFileBasePath();
	}

	public void setFileBasePath(String fileBasePath){
		delegate.setFileBasePath(fileBasePath);
	}

	public KieFileSystem getFileSystem(){
		return delegate.getFileSystem();
	}

	public void setFileSystem(KieFileSystem fileSystem){
		delegate.setFileSystem(fileSystem);
	}

	public void setKieBaseName(String kieBaseName){
		delegate.setKieBaseName(kieBaseName);
	}

	public String getKieBaseName(){
		return delegate.getKieBaseName();
	}

	public void setPackages(String packages){
		delegate.setPackages(packages);
	}

	public String getPackages(){
		return delegate.getPackages();
	}

	public void setSessionName(String sessionName){
		delegate.setSessionName(sessionName);
	}

	public String getSessionName(){
		return delegate.getSessionName();
	}

	public void refresh(){
		delegate.refresh();
	}

	public void refreshRule(String ruleFile){
		delegate.refreshRule(ruleFile);
	}
}
