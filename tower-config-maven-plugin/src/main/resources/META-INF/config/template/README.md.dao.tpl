### dao 实现

+ 实现标准

	+ 配置必须动态加载
	+ 应用配置与资源配置分离
	
	+ 动态数据源［当数据库配置变化时，会加载最新配置］
		+ 实现类 com.tower.service.datasource.DynamicDataSource
			+ 可以通过设置datasourceImpl属性来改变数据库连接池的实现，默认:com.tower.service.datasource.DBCPDataSource[DBCP实现的封装]
			+ 其他实现:com.tower.service.datasource.StatsDataSource［DruidDataSource实现的封装］
+ ... 

+配置
	
	+ 应用配置
		+ 保存在#{artifactId}-dao/src/main/resource/META-INF/config/spring
		+ spring-dao.xml
		+ 通过配置MultipleDataSource实例的targetDataSources属性列表
	+ 事务配置
		+ 找到spring-dao.xml,取消transactionManager的注释
	+ 资源&开关配置
		+ 资源文件默认保存在/config目录,当没有找到时，程序会从tsl-dao/src/main/resource/META-INF/config/local中读取配置信息
		+ database.properties
		+ acc.xml

+ dao生成器的使用
	
	+ 找到#{artifactId}-dao/src/main/resource/META-INF/config/local/database.properties
		+ 找到并修改相应的url、username、password信息，保存退出
	+ 找到#{artifactId}-dao/src/test/java/com/#{company}/service/#{artifactId}/dao/DaoGen.java
		+ 目前支持mysql、sql server两种形式；
		+ 找到'表名'并且替换成真实的表名eg:Code，保存退出并且直接运行该类，生成该表数据访问层代码
		+ 生成出来的代码基本上可以满足90%以上的需求，详见如下列表；
			+ ICodeDAO.java
			+ CodeIbatisDAOImpl.java
			+ CodeMapper.java
			+ Code.java
			+ CodeMapper.xml
		
+ dao操作扩展实现
	
	+ 1,在相应的对象数据访问层接口定义接口 aa
	+ 2,在mapper接口上定义相关接口 aa
	+ 3,在mapper文件上定义sql实现 id 为 aa [注意：parameterType必须为"java.util.Map"，表名必须采用‘${TowerTabName}’替换]
	+ 4,在数据访问层定义实现，实现代码如下
		
		```
		/**
	 	 * 
	 	 * @param params 操作支持的参数
	 	 * @param tabNameSuffix 分表访问支持
	     */
	     
	  pubic void aa(Map param,String tabNameSuffix){
	    
	    SqlSession session = SqlmapUtils.openSession(getMapQueryDataSource());
	    try{
    	    IMapper<EccOrderinfo> mapper = session.getMapper(getMapperClass());
    	    Map params = new HashMap();
    	    params.put("TowerTabName", this.get$TowerTabName(tabNameSuffix));//分表访问支持
    	    List<EccOrderinfo> returnList = mapper.aa(params);
	    } catch (Exception t) {
	      throw new DataAccessException(IBatisDAOException.MSG_2_0001, t);
	    } finally {
	      session.commit();
	      session.close();
	    }
	 }
		
		```