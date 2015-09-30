### dao包

新建一张表 

CREATE TABLE `cache_version` (
  `obj_name` varchar(32) DEFAULT NULL,
  `rec_version` bigint(20) NOT NULL,
  `tab_version` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8


在spring-db.xml中新增一组cache_db配置


### 缓存开关，开启：-Ddao.query.cacheable=true；关闭：-Ddao.query.cacheable=false；但是会刷新缓存；
### 查询时当master＝true时，查询不启用缓存，但是会刷新缓存；
### 主键操作dao
### 外键操作dao
### 其它操作dao
### 
### 

Spring+Mybatis+Tomcat下多数据源与 atomikos 分布式事务配置

配置两个数据源

将不同数据库的mapper和xml文件分开放在两个文件夹中如 main 和 pay

Main数据库配置如下：

<bean id="mainDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</bean>


<bean id="mainSqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="mainDataSource"/>
    <property name="configLocation" value="classpath:mybatis.xml"/>
    <property name="mapperLocations" value="classpath*:mapper/main/*.xml"/>
</bean>

<!-- scan for mappers and let them be autowired -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="mainSqlSessionFactory"/>
    <!-- Mapper接口所在包名，Spring会自动查找其下的Mapper -->
    <property name="basePackage" value="com.loftor.mapper.main"/>
</bean>


Pay数据库配置如下：

<bean id="payDataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
    <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
    <property name="url" value="jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true"/>
    <property name="username" value="root"/>
    <property name="password" value="123456"/>
</bean>

<bean id="paySqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="payDataSource"/>
    <property name="configLocation" value="classpath:mybatis.xml"/>
    <property name="mapperLocations" value="classpath*:mapper/pay/*.xml"/>
</bean>

<!-- scan for mappers and let them be autowired -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="paySqlSessionFactory"/>
    <!-- Mapper接口所在包名，Spring会自动查找其下的Mapper -->
    <property name="basePackage" value="com.loftor.mapper.pay"/>
</bean>

这样配置之后，代码中在不需要事务时可以像平时一样操作数据库都没有任何的问题。但是如果需要使用事务时，使用注解方式@Transactional，我尝试以下的方法：

<bean id="mainTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="mainDataSource"/>
</bean>

<bean id="payTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
    <property name="dataSource" ref="payDataSource"/>
</bean>

<tx:annotation-driven transaction-manager="mainTransactionManager"/>
<tx:annotation-driven transaction-manager="payTransactionManager"/>

这样配置后数据一个结果就是main数据库的数据能正常插入，但是pay数据库中的数据没法插入，也就是说只有main数据的事务被提交，而pay数据库的事务没有被提交，如果将

<tx:annotation-driven transaction-manager="mainTransactionManager"/>
<tx:annotation-driven transaction-manager="payTransactionManager"/>
顺序换一下，则pay数据库有数据，main则没有，所以TransactionManager应该是只有一个会生效。

经过一番baidu google后，得到的信息是多数据库只能用jta分布式事务管理，而且还不支持tomcat，但是辛亏还有第三方的开源解决方案:JOTM和Atomikos等。看到这两个名字后，很自然一般人都会选择JOTM，为什么?因为它名字短……抱着试试看的态度去JOTM的官网看了一下，这真的是个古董，最后一次更新都离现在有4年多了……那就看看Atomikos吧，它分为免费版和收费版，有公司负责维护，最新版目前是3.9.3。嗯好的就它了，通过下载官方的使用示例和网上前辈们的资料，折腾如下：

1.下载相关jar包

我们需要用到的jar有
transactions-jdbc
transactions-jta
transactions-api
transactions
atomikos-utils

<dependency>  
    <groupId>com.atomikos</groupId>  
    <artifactId>transactions-jdbc</artifactId>  
    <version>3.7.0</version>  
</dependency>  
<dependency>  
    <groupId>javax.transaction</groupId>  
    <artifactId>jta</artifactId>  
    <version>1.1</version>  
</dependency>

官方下载地址在 http://www.atomikos.com/Main/InstallingTransactionsEssentials
还需要一个jta.jar包，我使用的是1.1版本
为了方便大家
我方的下载地址在 http://url.cn/UW1wOH
2.配置数据源

我使用是mysql数据库，需要注意的是数据库引擎应该使用innodb
配置数据库有两种方式，一种是使用XA，另一种当然是不使用，可以baidu之，推荐使用，一下对应的配置

<!-- 一般方式 -->
<!--<bean id="mainDataSource" class="com.atomikos.jdbc.nonxa.AtomikosNonXADataSourceBean" init-method="init" destroy-method="close">-->
    <!--<property name="uniqueResourceName" value="db_main"/>-->
    <!--<property name="driverClassName" value="com.mysql.jdbc.Driver"/>-->
    <!--<property name="url" value="jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true"/>-->
    <!--<property name="user" value="root"/>-->
    <!--<property name="password" value="123456"/>-->
    <!--<property name="poolSize" value="5"/>-->
<!--</bean>-->

<!-- XA方式 -->
<bean id="mainDataSource" class="com.atomikos.jdbc.AtomikosDataSourceBean" init-method="init" destroy-method="close">
    <property name="uniqueResourceName" value="db_main"/>
    <property name="xaDataSourceClassName" value="com.mysql.jdbc.jdbc2.optional.MysqlXADataSource"/>
    <property name="xaProperties">
        <props>
            <prop key="url">jdbc:mysql://127.0.0.1:3306/main?characterEncoding=utf8&allowMultiQueries=true</prop>
            <prop key="user">root</prop>
            <prop key="password">123456</prop>
        </props>
    </property>
    <property name="minPoolSize" value="10" />
    <property name="maxPoolSize" value="100" />
    <property name="borrowConnectionTimeout" value="30" />
    <property name="testQuery" value="select 1" />
    <property name="maintenanceInterval" value="60" />
</bean>

oracle可以使用

<bean id="oracleDataSource" class="com.atomikos.jdbc.AtomikosDataSourceBean" init-method="init" destroy-method="close">
    <property name="uniqueResourceName" value="oracleDataSource"/>
    <property name="xaDataSourceClassName" value="com.sybase.jdbc3.jdbc.SybXADataSource"/>
    <property name="xaProperties">
        <props>
            <prop key="serverName">192.168.1.10</prop>
                        <prop key="portNumber">2638</prop>
                        <prop key="databaseName">test</prop>
            <prop key="user">test</prop>
            <prop key="password">test</prop>
        </props>
    </property>
    <property name="minPoolSize" value="10" />
    <property name="maxPoolSize" value="100" />
    <property name="borrowConnectionTimeout" value="30" />
    <property name="testQuery" value="select 1" />
    <property name="maintenanceInterval" value="60" />
</bean>

其中uniqueResourceName项需要配置唯一值不能重复
多个数据源类似的配置

3.sqlsessionFactory和其他的配置一致没有区别

<bean id="mainSqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
    <property name="dataSource" ref="mainDataSource"/>
    <property name="configLocation" value="classpath:mybatis.xml"/>
    <property name="mapperLocations" value="classpath*:mapper/main/*.xml"/>
</bean>

<!-- scan for mappers and let them be autowired -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
    <property name="sqlSessionFactoryBeanName" value="mainSqlSessionFactory"/>
    <!-- Mapper接口所在包名，Spring会自动查找其下的Mapper -->
    <property name="basePackage" value="com.loftor.mapper.main"/>
</bean>

4.事务管理器配置

<!-- 分布式事务 -->
<bean id="atomikosTransactionManager" class="com.atomikos.icatch.jta.UserTransactionManager" init-method="init" destroy-method="close">
    <property name="forceShutdown" value="true"/>
</bean>

<bean id="atomikosUserTransaction" class="com.atomikos.icatch.jta.UserTransactionImp">
    <property name="transactionTimeout" value="300"/>
</bean>


<bean id="transactionManager" class="org.springframework.transaction.jta.JtaTransactionManager">
    <property name="transactionManager" ref="atomikosTransactionManager"/>
    <property name="userTransaction" ref="atomikosUserTransaction"/>
</bean>

<tx:annotation-driven transaction-manager="transactionManager"/>


这步是关键，<tx:annotation-driven transaction-manager="transactionManager"/> 也可以写为<tx:annotation-driven/> 不需要配置transaction-manager 因为spring默认取的就是transactionManager。

到这里应该已经可以使用了。大功告成了。但是如果要对atomikos配置的话，只要增加一个jta.properties放在和log4j.properties同样的目录就行，配置的内容可以在官方文档找到，如：

com.atomikos.icatch.service=com.atomikos.icatch.standalone.UserTransactionServiceFactory
com.atomikos.icatch.log_base_name = jdbc
com.atomikos.icatch.tm_unique_name = com.atomikos.spring.jdbc.tm
com.atomikos.icatch.serializable_logging=false

参考资料

http://loftor.com/archives/spring-mybatis-tomcat-jta.html

http://zxlaiye.iteye.com/blog/1441299
