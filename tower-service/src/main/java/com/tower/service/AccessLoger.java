package com.tower.service;

import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;

import com.alibaba.dubbo.rpc.RpcContext;
import com.tower.service.cache.CacheVersion;
import com.tower.service.cache.CacheVersionStack;
import com.tower.service.cache.ICacheVersion;
import com.tower.service.concurrent.AsynBizExecutor;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
public class AccessLoger {

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
            CacheVersionStack.init();
            return pjp.proceed(); 
        } 
        catch(Exception ex){
            _logger.error(ex);
            throw ex;
        }
        finally {
        	
//        	new AsynBizExecutor("syn.cache") {
//				
//				@Override
//				public void execute() {
//					ICacheVersion<CacheVersion> cacher = CacheVersionStack.getCacher();
//					Map<String,Integer> recs = CacheVersionStack.getRecIncs();
//					Map<String,Integer> tabs = CacheVersionStack.getTabIncs();
//					
//					cacher.incrObjRecVersion(null, null);
//				}
//			};
        	
			CacheVersionStack.unset();
			
            time = System.currentTimeMillis() - time;
            _logger.info("ip={} {}{}{}{} {} ms", ip,svcDef.get("className"),".",svcDef.get("methodName"),svcDef.get("detail"),time);
        }
    }
    private transient static Logger _logger = LoggerFactory.getLogger(AccessLoger.class);
}
