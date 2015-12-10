package com.tower.service.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.tower.service.cache.CacheSwitcher;
import com.tower.service.util.RequestID;

public class RequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String rid = request.getHeader("X-Request-ID");
		String cached = request.getHeader("X-Cached");
		LOGGER.debug("rid: {}", rid);
		RequestID.set(rid);
		LOGGER.debug("{}", RequestID.get());
		if("true".equalsIgnoreCase(cached)||"false".equalsIgnoreCase(cached)){
			CacheSwitcher.set(Boolean.valueOf(cached));
		}
		else{
			CacheSwitcher.set(true);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
		RequestID.unset();
		CacheSwitcher.unset();
	}
}
