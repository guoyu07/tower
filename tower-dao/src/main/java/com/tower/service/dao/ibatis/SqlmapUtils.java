package com.tower.service.dao.ibatis;

import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * Sqlmap工具.
 *
 * TODO: ThreadLocal SqlSession
 */
public final class SqlmapUtils {

	public static void addMapper(Class<?> mapperType, SqlSessionFactory factory) {
		Configuration configuration = factory.getConfiguration();
		if (!configuration.hasMapper(mapperType)) {
			configuration.addMapper(mapperType);
		}
	}

	public static SqlSession openSession(SqlSessionFactory sessionFactory) {
		return SqlSessionUtils.getSqlSession(sessionFactory);
	}

	public static void release(SqlSession session,SqlSessionFactory sessionFactory){
		TransactionStatus status = null;
		try{
			status = TransactionAspectSupport.currentTransactionStatus();
		}
		catch(Exception ex){
		}
		if (status==null) {//没有加入事务，即单操作单事务
			session.commit();
			SqlSessionUtils.closeSqlSession(session, sessionFactory);
		}
	}
}
