package com.tower.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.container.spring.SpringContainer;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class TowerServiceContainer {
	private SpringContainer container = null;
	static ClassPathXmlApplicationContext context;
	Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * classpath*:META-INF/config/spring/spring-service.xml
	 * 
	 * @param location
	 */
	public TowerServiceContainer(String location) {
		System.setProperty(
				"dubbo.spring.config",
				location == null ? "classpath*:META-INF/config/spring/spring-service.xml"
						: location);
		container = new SpringContainer();
	}

	public void start() {
		try{
			container.start();
			context = SpringContainer.getContext();
		}
		catch(Exception ex){
			logger.error("初始化出错", ex);
		}
	}

	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}

	public ClassPathXmlApplicationContext getContext() {
		return context;
	}
}
