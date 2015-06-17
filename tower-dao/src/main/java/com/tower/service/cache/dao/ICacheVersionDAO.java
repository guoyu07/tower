package com.tower.service.cache.dao;

import com.tower.service.cache.dao.model.CacheVersion;
import com.tower.service.dao.IDAO;
import com.tower.service.dao.IFKDAO;
import com.tower.service.dao.ISDAO;

public interface ICacheVersionDAO<T extends CacheVersion> extends ISDAO<T>, IFKDAO<T>, IDAO<T> {

  public int incrObjVersion(String objName, String tabNameSuffix);

  public int incrObjRecVersion(String objName, String tabNameSuffix);

  public int incrObjTabVersion(String objName, String tabNameSuffix);
}
