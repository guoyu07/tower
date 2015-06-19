package com.tower.service.monitor;

import com.alibaba.dubbo.common.URL;

public interface IMonitorService {
    /**
     * 发布监控信息
     * @param monitors
     * @return
     */
    public void publish(URL url);
    
}
