package com.tower.service.dao.ibatis;

import org.aspectj.lang.ProceedingJoinPoint;

import com.tower.service.datasource.TxHolder;
import com.tower.service.log.Logger;
import com.tower.service.log.LoggerFactory;

public class TxUtil {
  private static Logger logger = LoggerFactory.getLogger(TxUtil.class);

  public static Object processAround(ProceedingJoinPoint pjp) throws Throwable {
    boolean started = TxHolder.started();
    logger.info("begin tx: " + started);
    try {
      if (!started) {
        TxHolder.set();
      }
      Object ret = pjp.proceed();
      if (!started) {
        SqlmapUtils.commit();
        logger.info("end commit");
      }
      return ret;
    } catch (Exception ex) {
      if (!started) {
        SqlmapUtils.rollback();
        logger.info("end rollback");
      }
      throw ex;
    } finally {
      if (!started) {
        TxHolder.unset();
        SqlmapUtils.close();
        logger.info("end tx");
      }
    }
  }
}
