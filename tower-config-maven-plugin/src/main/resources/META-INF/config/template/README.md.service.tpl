### service规范

+ 需要动态加载配置必须继承实现com.tower.service.impl.AbsServiceImpl同时实现其对应的服务接口
+ 框架配置必须放在对应项目的resources/META-INF/config/spring/spring-service.xml中
+ 运行时配置必须放在service.xml
### 代码生成器
+ 1,按照下列步骤逐步生成代码
+ 2,运行项目#{artifactId}-domain/src/test/java/com/#{company}/service/#{artifactId}/DtoGen.java
+ 3,从相关model对象中拷贝相关属性到上步生成的xxxDto中
+ 4,运行项目#{artifactId}-service/src/test/java/com/#{company}/service/#{artifactId}/ServiceGen.java
+ 5,运行项目#{artifactId}-service-impl/src/test/java/com/#{company}/service/#{artifactId}/ServiceGen.java