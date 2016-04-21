### dao包

#### 缓存

新建一张表 

CREATE TABLE `cache_version` (
  `obj_name` varchar(32) DEFAULT NULL,
  `rec_version` bigint(20) NOT NULL,
  `tab_version` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8


在spring-dao.xml中新增一组cache_db配置

#### 开关
+ 开启：-DX-Cached=true；
+ 关闭：-DX-Cached=false；但是会刷新缓存；
+ 查询时当master＝true时，查询不启用缓存，但是会刷新缓存；
+ 主键操作dao
+ 外键操作dao
+ 其它操作dao

#### [缓存算法](DAO2.0.md)

#### 分库
+ 通过DataSourceRouter在serviceImpl相关业务中实现

#### 分表
+ 通过数据访问层tabNameSuffix接口参数支持

#### 读写分离
+ 通过设置datasource.properties xxxx_db(写)、xxxx_db_slave（读）、xxxx_db_map_query（读）地址支持

#### 事务
+ 通过开启spring-dao.xml中transactionManager相关注释;

#### [传统分布式事务原理参考文档](JTA.MD)
#### [创新分布式事务原理参考文档](JTA2.0.MD)
