### 部署规范


+ 部署用户

	+ deployer
	
+ 部署应用程序目录结构

	+ ~/apps
	
			|
			bin/
				|
				start.sh service version
		 	order
		 	merchant
		 	tsl
		 	  |
		 	  current
		 	  v1
		 	  v2
		 	  v3
		 	  ...
		 	  vn
		 	  	|
		 	  	tsl-job/
		 	  		|
		 	  		start.sh
		 	  		lib/
		 	  	tsl-service-impl/
		 	  		|
		 	  		start.sh
		 	  		lib/
		 	  	tsl-web/
		 	  		|
		 	  		start.sh
		 	  		tsl-web.war
		 	  			
	+ 日志目录结构
		+ /data1/logs/service/tsl/
		
								|
								dlog.log
								elog.log
								request.log
	＋ 全局配置文件目录
		+ /config

