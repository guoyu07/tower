package com.tower.service.dao.ibatis;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import com.tower.service.datasource.TxHolder;

/**
 * Sqlmap工具.
 *
 * TODO: ThreadLocal SqlSession
 */
public final class SqlmapUtils {

  /**
   * Add mapper to defined SqlSessionFactory.
   *
   * @param mapperType
   * @param definition
   */
  public static void addMapper(Class<?> mapperType, DataSource dataSource) {

    SqlSessionFactory factory = getFactory(dataSource);
    Configuration configuration = factory.getConfiguration();
    if (!configuration.hasMapper(mapperType)) {
      configuration.addMapper(mapperType);
    }
  }

  /**
   * Add mapper to defined SqlSessionFactories.
   *
   * @param mapperType
   * @param definitions
   */
  public static void addMapper(Class<?> mapperType, DataSource[] dataSources) {
    if (dataSources == null || dataSources.length <= 0) {
      return;
    }
    for (DataSource dataSource : dataSources) {
      addMapper(mapperType, dataSource);
    }
  }

  /**
   * 返回SqlSessionWrapper.
   *
   * @param definition
   * @return
   */
  public static SqlSession openSession(DataSource dataSource) {
    SqlSessionFactory factory = getFactory(dataSource);
    if (TxHolder.isSingleTx()) {
      return factory.openSession();
    }
    Map<SqlSessionKey, SqlSession> map = getMap(true);
    SqlSessionKey key = new SqlSessionKey(dataSource, factory.getConfiguration()
        .getDefaultExecutorType());
    SqlSession session = map.get(key);
    if (session != null) {
      return session;
    } else {
      session = factory.openSession();
      map.put(key, session);
    }
    return session;
  }

  public static void close() {
    Map<SqlSessionKey, SqlSession> map = getMap(false);
    if (map == null) {
      return;
    }
    for (SqlSession session : map.values()) {
      try {
        session.close();
      } catch (Exception e) {
      }
    }
    map.clear();
    _sessions.remove();
  }
  
  public static void commit(){
    Map<SqlSessionKey, SqlSession> map = getMap(false);
    if (map == null) {
      return;
    }
    for (SqlSession session : map.values()) {
      try {
        session.close();
      } catch (Exception e) {
      }
    }
  }

  public static void rollback(){
    Map<SqlSessionKey, SqlSession> map = getMap(false);
    if (map == null) {
      return;
    }
    for (SqlSession session : map.values()) {
      try {
        session.rollback();
      } catch (Exception e) {
      }
    }
  }
  private static Map<SqlSessionKey, SqlSession> getMap(boolean create) {
    Map<SqlSessionKey, SqlSession> map = _sessions.get();
    if (map == null) {
      map = new HashMap<SqlSessionKey, SqlSession>();
      if (create) {
        _sessions.set(map);
      }
    }
    return map;
  }

  /**
   * 返回SqlSessionWrapper.
   *
   * @param definition
   * @param executorType
   * @return
   */
  public static SqlSession openSession(DataSource dataSource, ExecutorType executorType) {
    SqlSessionFactory factory = getFactory(dataSource);
    return factory.openSession(executorType);
  }

  //

  /**
   * 返回所需的SqlSessionFactoryWrapper.
   *
   * @param definition
   *          需要的数据源定要.
   * @return SqlSessionFactory
   */
  public static SqlSessionFactory getFactory(DataSource definition) {
    if (_factories.containsKey(definition)) {
      return _factories.get(definition);
    }
    return createSqlSessionFactory(definition);
  }

  //

  private static synchronized SqlSessionFactory createSqlSessionFactory(DataSource dataSource) {
    SqlSessionFactory factory = _factories.get(dataSource);
    if (factory != null) {
      return factory;
    }

    TransactionFactory transactionFactory = new JdbcTransactionFactory();
    Environment environment = new Environment("Default", transactionFactory, dataSource);
    Configuration configuration = new Configuration(environment);
    configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
    configuration.addInterceptor(new StatementHandlerPlugin());
    factory = new SqlSessionFactoryBuilder().build(configuration);
    _factories.put(dataSource, factory);

    return factory;
  }

  /**
   * 默认是单事务提交
   * 
   * @param session
   */
  public static void release(SqlSession session) {
    if (TxHolder.isSingleTx()) {
      session.commit();
      session.close();
    }
  }

  private static Map<DataSource, SqlSessionFactory> _factories = new HashMap<DataSource, SqlSessionFactory>();
  private final static ThreadLocal<Map<SqlSessionKey, SqlSession>> _sessions = new ThreadLocal<Map<SqlSessionKey, SqlSession>>();

}
