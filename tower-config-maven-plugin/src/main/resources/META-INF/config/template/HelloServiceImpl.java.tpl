package com.#{company}.service.#{artifactId}.impl;

import org.springframework.transaction.annotation.Transactional;

import com.#{company}.service.#{artifactId}.IHello;

public class HelloImpl implements IHello {
	
	@Override
	@Transactional
	public void sayHello() {
		System.out.println("hello");
	}

}
