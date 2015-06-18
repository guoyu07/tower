package com.tower.service.monitor;

public interface IMonitorService {
    /**
     * 发布监控信息
     * @param monitors
     * @return
     */
    public void publish(String monitors);
    
}
