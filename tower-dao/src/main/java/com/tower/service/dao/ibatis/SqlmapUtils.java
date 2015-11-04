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
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;

import com.tower.service.datasource.TxHolder;

/**
 * Sqlmap工具.
 *
 * TODO: ThreadLocal SqlSession
 */
public final class SqlmapUtils {

	public static void addMapper(Class<?> mapperType,
			SqlSessionFactory factory) {
		Configuration configuration = factory.getConfiguration();
		if (!configuration.hasMapper(mapperType)) {
			configuration.addMapper(mapperType);
		}
	}

	public static SqlSession openSession(SqlSessionFactory sessionFactory) {
		return SqlSessionUtils.getSqlSession(sessionFactory);
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
}
