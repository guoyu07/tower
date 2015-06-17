package com.tower.service.util.reflection.wrapper;

import com.tower.service.util.reflection.MetaObject;

/**
 * @author Clinton Begin
 */
public interface ObjectWrapperFactory {

  boolean hasWrapperFor(Object object);
  
  ObjectWrapper getWrapperFor(MetaObject metaObject, Object object);
  
}
