### 部署规范

+ jenkins自动化构件
	+ sh publish.sh xxx
	+ sh ./rsyc.sh tsl 2015-08-04_1 192.168.1.117 root

+ 部署用户

	+ deployer
	
+ 部署应用程序目录结构

	+ ~/apps
	
			|
		 	order
		 	merchant
		 	tsl
		 	  |
		 	  config/ 应用配置目录
		 	  current/ (ln -s vn/bin current)
		 	  v1
		 	  v2
		 	  v3
		 	  ...
		 	  vn
		 	  	|
		 	  	bin/
		 	  		|
		 	  		start-job.sh
		 	  		startService.sh
		 	  		startWeb.sh
		 	  	tsl-job/
		 	  		|
		 	  		tsl-job-xxx.tar.gz
		 	  	tsl-service-impl/
		 	  		|
		 	  		tsl-service-impl-xxxx.tar.gz
		 	  	tsl-web/
		 	  		|
		 			pom.xml
		 			src/
		 			target/tsl-web.war
		 	  			
	+ 日志目录结构
		+ /data1/logs/service/tsl/
		
								|
								dlog.log
								elog.log
								request.log
	＋ 全局配置文件目录
		+ /config

