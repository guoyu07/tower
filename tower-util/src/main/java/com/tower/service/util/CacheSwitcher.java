package com.tower.service.util;


public class CacheSwitcher {

    private static final ThreadLocal<Boolean> CACHES = new ThreadLocal<Boolean>();

    public static void set(Boolean cached) {
    	CACHES.set(cached);
    }

    public static void unset() {
    	CACHES.remove();
    }

    public static Boolean get() {
    	Boolean cached = CACHES.get();
    	if(cached!=null){
    		return CACHES.get();
    	}
    	return true;
    }
}
