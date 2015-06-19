package com.tower.service.filter;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcException;
import com.tower.service.TowerServiceContainer;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;
import com.tower.service.monitor.IMonitorService;
import com.tower.service.util.RequestID;

@Activate(group = { Constants.PROVIDER, Constants.CONSUMER })
public class ServiceFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(getClass());
	private String reqidKey = "ReqId";
	private IMonitorService monitor;
	public ServiceFilter() {
		logger.info("ServiceFilter created");
	}

	@Override
	public Result invoke(Invoker<?> invoker, Invocation invocation)
			throws RpcException {
		if(monitor==null){
			monitor = TowerServiceContainer.getBean(IMonitorService.class);
		}
		RpcContext context = RpcContext.getContext();
		String remoteIp = context.getRemoteHost();
		boolean provider = context.isProviderSide();
		if (provider) {
			String reqId = context.getAttachment(reqidKey);
			RequestID.set(reqId);
			logger.info(reqidKey + " from client@" + remoteIp);
		} else {
			String reqId = RequestID.get();
			context.setAttachment(reqidKey, reqId);
			logger.info(reqidKey + " to service@" + remoteIp);
		}
		URL url = invoker.getUrl();
		Result result = invoker.invoke(invocation);
		if(monitor!=null){
			System.out.println("publish...");
		}
		return result;
	}

}
