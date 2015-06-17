package com.tower.service.dao.ibatis;

import com.tower.service.dao.IIDAO;
import com.tower.service.dao.IModel;

public interface IIBatisDAO<T extends IModel> extends IBatisDAO<T>, IIDAO<T> {

  public Class<? extends IIMapper<T>> getMapperClass();

}
