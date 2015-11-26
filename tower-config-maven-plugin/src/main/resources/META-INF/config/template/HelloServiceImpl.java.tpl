package com.#{company}.service.#{artifactId}.impl;

import org.springframework.transaction.annotation.Transactional;

import com.siling.service.hello.IHelloService;
import com.tower.service.impl.AbsServiceImpl;

public class HelloServiceImpl extends AbsServiceImpl implements IHelloService {
	
	@Override
	@Transactional
	public void sayHello() {
		System.out.println("hello");
	}
}
