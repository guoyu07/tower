package com.tower.service.filter;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

	private BlockingQueue<URL> messages = new ArrayBlockingQueue<URL>(100);
	private Logger logger = LoggerFactory.getLogger(getClass());
	private String reqidKey = "ReqId";
	private IMonitorService monitor;

	public ServiceFilter() {
		logger.info("ServiceFilter created");
		new Publisher();
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
		String interfaceStr = url.getParameter("interface");
		long start = System.currentTimeMillis();
		Result result = invoker.invoke(invocation);
		long timeused = (System.currentTimeMillis() - start);
		if (monitor != null
				&& !interfaceStr.contains(IMonitorService.class.getName())) {
			url = url.addParameter("timeused", timeused);
			try {
				messages.put(url);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private class Publisher extends Thread {
		public Publisher() {
			this.start();
		}

		public void run() {
			while (true) {
				try {
					if (monitor != null) {
						URL url = messages.take();
						monitor.publish(url);
						Thread.sleep(10);
					} else {
						Thread.sleep(1000);
						continue;
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
