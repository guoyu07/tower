### job规范

+ 分页处理job必须继承实现com.tower.service.job.impl.AbsPageableJob,同时必须实现IPageableJob接口
+ 循环处理（处理结果影响待处理数据范围）job必须继承实现com.tower.service.job.impl.AbsJob,同时必须实现INormalJob接口
+ 应用配置必须放在对应项目的resources/META-INF/config/spring/spring-job.xml中
+ 运行时配置必须放在job.xml
	