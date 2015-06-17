package com.tower.service.util.reflection.wrapper;

import com.tower.service.util.reflection.MetaObject;
import com.tower.service.util.reflection.ReflectionException;

/**
 * @author Clinton Begin
 */
public class DefaultObjectWrapperFactory implements ObjectWrapperFactory {

  public boolean hasWrapperFor(Object object) {
    return false;
  }
  
  public ObjectWrapper getWrapperFor(MetaObject metaObject, Object object) {
    throw new ReflectionException("The DefaultObjectWrapperFactory should never be called to provide an ObjectWrapper.");
  }
  
}
