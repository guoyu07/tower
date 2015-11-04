package com.#{company}.service.#{artifactId}.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import com.tower.service.dao.ibatis.TxUtil;

@Aspect
public class TxAspect {
	
	@Around("execution(@org.springframework.transaction.annotation.Transactional * * (..))")
	public Object processAround(ProceedingJoinPoint pjp) throws Throwable {
		return TxUtil.processAround(pjp);
	}
}
