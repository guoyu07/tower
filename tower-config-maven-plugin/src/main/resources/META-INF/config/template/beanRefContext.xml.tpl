<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-2.0.xsd">
	<bean id="towerContext"
		class="com.tower.service.web.spring.TowerApplicationContextFactoryBean">
		<constructor-arg index="0">
		    <value>#{artifactId}-web</value>
		</constructor-arg>
		<constructor-arg index="1">
			<value>classpath*:META-INF/config/spring/spring-dubbo.xml</value>
		</constructor-arg>
	</bean>
</beans>