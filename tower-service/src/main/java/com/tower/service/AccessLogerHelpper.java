package com.tower.service;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
public class AccessLogerHelpper {

    public static Object process(ProceedingJoinPoint pjp) throws Throwable{
        String ip = RpcContext.getContext().getRemoteAddressString();
        if(ip!=null){
            ip = ip+" ";
        }
        String keys = null;
        long time = System.currentTimeMillis();     
        Map svcDef = AliasUtil.getAlias(keys,pjp, true);
        try {
            time = System.currentTimeMillis();
            return pjp.proceed(); 
        } 
        catch(Exception ex){
            _logger.error(ex);
            throw ex;
        }
        finally {        	
            time = System.currentTimeMillis() - time;
            if(svcDef!=null){
            	_logger.info("ip={} {}{}{}{} {} ms", ip,svcDef.get("className"),".",svcDef.get("methodName"),svcDef.get("detail"),time);
            }
        }
    }
    private transient static Logger _logger = LoggerFactory.getLogger(AccessLogerHelpper.class);
}
