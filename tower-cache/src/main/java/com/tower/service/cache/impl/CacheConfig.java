package com.tower.service.cache.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.DynamicConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileDict;

@Component(ConfigComponent.CacheConfig)
public class CacheConfig extends DynamicConfig{
    public CacheConfig(){
    }
    
    @PostConstruct
    public void init(){
        setFileName(System.getProperty(ConfigFileDict.CACHE_CONFIG_FILE,
            ConfigFileDict.DEFAULT_CACHE_CONFIG_NAME));
        super.init();
    }
}
