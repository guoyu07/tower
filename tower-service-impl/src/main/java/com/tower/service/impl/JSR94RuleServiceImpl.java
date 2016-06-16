package com.tower.service.impl;

import org.springframework.beans.factory.InitializingBean;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tower.service.IService;
import com.tower.service.config.IConfigChangeListener;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.rule.IEngine;
import com.tower.service.rule.impl.TowerJSR94Engine;

public class JSR94RuleServiceImpl extends TowerJSR94Engine implements IService,IEngine,IConfigChangeListener, InitializingBean {
    
    protected static ServiceConfig config;
    
	/**
	 * Logger for this class
	 */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public JSR94RuleServiceImpl(){
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
}
