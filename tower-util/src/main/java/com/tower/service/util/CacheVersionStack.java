package com.tower.service.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 缓存同步优化机制（一个对象的多个版本变更合并成一次变更）
 * @author alexzhu
 *
 */
public class CacheVersionStack {

    private static final ThreadLocal<Map<String,Integer>> stacks = new ThreadLocal<Map<String,Integer>>();

    public static void set(String id) {
    	if(stacks.get()==null){
    		Map<String,Integer> stack = new ConcurrentHashMap<String,Integer>();
    		stacks.set(stack);
    	}
    	if(stacks.get().containsKey(id)){
    		Integer cnt = stacks.get().get(id);
    		stacks.get().put(id, cnt++);
    	}
    }

    public static void unset() {
    	stacks.remove();
    }

    public static Map<String,Integer> get() {
    	return stacks.get();
    }
}
