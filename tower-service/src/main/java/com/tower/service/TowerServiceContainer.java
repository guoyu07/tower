package com.tower.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.container.spring.SpringContainer;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class TowerServiceContainer {
	private SpringContainer container = null;
	public static String SERVICE_ID;
	static ClassPathXmlApplicationContext context;
	Logger logger = LoggerFactory.getLogger(getClass());

	public TowerServiceContainer(String id,String location) {
		SERVICE_ID = id;
		String tmpLocation = (location == null ? "classpath*:META-INF/config/spring/spring-service.xml": location);
		System.setProperty("dubbo.spring.config",tmpLocation);
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
		T bean = null;
		try{
			bean = context.getBean(requiredType);
		}
		catch(Exception ex){
		}
		return bean;
	}

	public ClassPathXmlApplicationContext getContext() {
		return context;
	}
}
