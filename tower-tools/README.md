## Usage:
+ cd tower-tools
+ 运行 ./gen_xxxx.sh 脚本
	+ ./gen_all.sh 项目名 [公司名] 生成项目所有模块,公司名称默认为siling
	+ ./gen_job.sh 项目名 [公司名] 生成项目job所需的相关模块,公司名称默认为siling
	+ ./gen_service.sh 项目名 [公司名] 生成项目service所需的相关模块,公司名称默认为siling
	+ ./gen_web.sh 项目名 [公司名] 生成项目web所需的相关模块,公司名称默认为siling
	
### 部署	
+ 安装jenkins
+ 安装gitlab插件
	+ 创建gitlab用户
	+ 安装ssh key
+ 安装maven插件
+ 配置jenkins

	+ 工作空间根目录:
		+ 登录到部署机器
		+ mkdir -p /root/code/projects/workspace
		+ /root/code/projects/workspace/${ITEM_FULL_NAME}
	+ 构建记录根目录:${ITEM_ROOTDIR}/builds/${ITEM_FULL_NAME}
+ 安装发布脚本
	+ mkdir -p /root/shell_bash
	+ 拷贝 tower-tools/publish下的*.sh
	+ [设置keychain](publish/README.md)
	
+ 发布代码
	+ login 2 部署机器	
	+ cd /root/shell_bash
	+ publish.sh 项目名称 分支名
	+ rsyc.sh 项目名称 分支名 版本号 远程IP地址 用户名
	+ current/start_job.sh
	+ current/startService.sh
	+ current/startWeb.sh
	