package com.tower.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.dubbo.container.spring.SpringContainer;

public class TowerServiceContainer {
	private SpringContainer container = null;
	static ClassPathXmlApplicationContext context;

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
		container.start();
		context = SpringContainer.getContext();
	}

	public static <T> T getBean(Class<T> requiredType) {
		return context.getBean(requiredType);
	}

	public ClassPathXmlApplicationContext getContext() {
		return context;
	}
}
