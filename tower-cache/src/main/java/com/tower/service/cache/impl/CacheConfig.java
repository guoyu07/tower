package com.tower.service.cache.impl;

import org.springframework.stereotype.Component;

import com.tower.service.config.DynamicConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileDict;
import com.tower.service.config.dict.ConfigFileTypeDict;

@Component(ConfigComponent.CacheConfig)
public class CacheConfig extends DynamicConfig{
    public CacheConfig(){
    }
    
    public void init(){
        setFileName(System.getProperty(ConfigFileDict.CACHE_CONFIG_FILE,
            ConfigFileDict.DEFAULT_CACHE_CONFIG_NAME));
        this.setType(ConfigFileTypeDict.XML);
        super.init();
    }
}
