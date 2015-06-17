package com.tower.service.dao.ibatis;

import com.tower.service.dao.ILDAO;
import com.tower.service.dao.IModel;

/**
 * id 为long型的数据访问接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface ILBatisDAO<T extends IModel> extends IBatisDAO<T>, ILDAO<T> {

  public Class<? extends ILMapper<T>> getMapperClass();

}
