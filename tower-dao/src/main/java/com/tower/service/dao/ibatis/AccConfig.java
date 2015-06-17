package com.tower.service.dao.ibatis;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.tower.service.config.DynamicConfig;
import com.tower.service.config.dict.ConfigComponent;
import com.tower.service.config.dict.ConfigFileDict;

@Component(ConfigComponent.AccConfig)
public class AccConfig extends DynamicConfig {

  public AccConfig() {
  }

  @PostConstruct
  public void init(){
      setFileName(System.getProperty(ConfigFileDict.ACCESS_CONTROL_CONFIG_FILE,
          ConfigFileDict.DEFAULT_ACCESS_CONTROL_CONFIG_NAME));
      super.init();
  }

}
