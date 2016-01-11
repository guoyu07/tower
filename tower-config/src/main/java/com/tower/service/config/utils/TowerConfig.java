package com.tower.service.config.utils;

import java.io.IOException;
import java.util.Properties;

public class TowerConfig {
	private static Properties towerConfig=null;
	public synchronized static String getConfig(String key){
        if(towerConfig==null){
            loadProperties();
        }
        return towerConfig.getProperty(key);
    }
    private static void loadProperties(){
    	towerConfig=new Properties();
        try {
        	towerConfig.load(TowerConfig.class.getResourceAsStream("/META-INF/tower.properties"));
        } catch (IOException e) {
        }
    }
    
    public static void main(String[] args){
    	System.out.println(TowerConfig.getConfig("db.user"));
    }
}
