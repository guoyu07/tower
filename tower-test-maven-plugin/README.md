## Usage:

+ 配置settings.xml

```
	<pluginGroups>
    	<pluginGroup>com.tower.soafw</pluginGroup>
    </pluginGroups>
        
```

+ 配置pom.xml

```
	<plugin>
		<groupId>org.eclipse.m2e</groupId>
		<artifactId>lifecycle-mapping</artifactId>
		<version>1.0.0</version>
		<configuration>
			<lifecycleMappingMetadata>
				<pluginExecutions>
					<pluginExecution>
						<pluginExecutionFilter>
							<groupId>com.tower.soafw</groupId>
							<artifactId>tower-test-maven-plugin</artifactId>
							<versionRange>[2.2.3-SNAPSHOT,)</versionRange>
							<goals>
								<goal>gen</goal>
							</goals>
						</pluginExecutionFilter>
						<action>
							<ignore></ignore>
						</action>
					</pluginExecution>
				</pluginExecutions>
			</lifecycleMappingMetadata>
		</configuration>
	</plugin>
```

+ 通过设置java 属性参数test.gen.skip=true的方式开启单元测试框架代码生成，默认是关闭的
+ eg: mvn -Dtest.gen.skip=true package