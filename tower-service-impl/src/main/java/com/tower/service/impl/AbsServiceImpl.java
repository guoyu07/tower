package com.tower.service.impl;

import javax.annotation.Resource;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tower.service.IService;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.domain.result.IResult;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public abstract class AbsServiceImpl<T extends IResult> implements IService<T> {
    
    @Resource(name=ConfigComponent.ServiceConfig)
    protected ServiceConfig config;
    
	/**
	 * Logger for this class
	 */
    protected Logger logger = LoggerFactory.getLogger(this.getClass());

	public AbsServiceImpl(){
	    
	}
	
	protected static RpcContext context = RpcContext.getContext();
    @Override
    public String getRemoteHost() {
        return context.getRemoteHost();
    }

	public String hello(String name){
		if (logger.isInfoEnabled()) {
			logger.info("hello(String name={}) - start", name); //$NON-NLS-1$
		}

		String returnString = "hello," + name;

		if (logger.isInfoEnabled()) {
			logger.info("hello(String name={}) - end - return value={}", name, returnString); //$NON-NLS-1$
		}
		return returnString;
	}

}
