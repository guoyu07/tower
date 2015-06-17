package com.tower.service.dao.ibatis;

import javax.sql.DataSource;

import com.tower.service.dao.IBatchDAO;
import com.tower.service.dao.IDAO;
import com.tower.service.dao.IModel;

/**
 * ibatis 操作接口
 * 
 * @author alexzhu
 *
 * @param <T>
 */
public interface IBatisDAO<T> extends IDAO<T>,IBatchDAO<T> {

  public Class<? extends IMapper<T>> getMapperClass();

  public Class<? extends IModel> getModelClass();

  public DataSource getMasterDataSource();

  public DataSource getSlaveDataSource();

  public DataSource getMapQueryDataSource();
  
  public void validate(IModel model);
  /**
   * 该属性名是否是外键属性
   * 
   * @param property
   * @return
   */
  public boolean isFk(String property);
  
  public int getVersion();
}
