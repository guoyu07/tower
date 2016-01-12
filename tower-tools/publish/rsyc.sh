#!/sh/bash

# 参数的说明  1-项目名(oft) 2-分支名 3-版本号(2015-03-19_1) 4-需要同步的远程IP地址(192.168.1.111)

if [ -z $5 ]; then
     user="apps"
else
    user=$5
fi

echo "$user"

release_base="/root/apps"

app_release_path="$release_base/$1/$2"

user_home="/home/$user"

apps_path="$user_home/apps/$1/$2/$3"

#######log_path######

log_path="/data1/logs/service/$1"

#####################

########global_config#####

global_config_path="/config"

####################

###########pro_config_path####

pro_config_path="$user_home"/"apps"/"$1"/"config"
#############################


################
soft_link_path="$app_release_path/current"


if [ $# > 6 ];then

  echo "参数数量不是5个,项目名称、分支名、版本号、远程IP地址[、用户名]"

fi

#### 校验工程名是否是(tsl/order/merchant)等等 #####


if [ "$1" != "oft" ]&&[ "$1" != "b2b" ]&&[ "$1" != "oapi" ];then

  echo "不是有效的项目名称oft/b2b/oapi请重新输入"

  exit

fi



ssh $user@$4  "test -d $apps_path||mkdir -p $apps_path"

ssh $user@$4  "test -d $log_path||mkdir -p $log_path"

ssh $user@$4  "test -d $global_config_path||mkdir -p $global_config_path"

ssh $user@$4  "test -d $pro_config_path||mkdir -p $pro_config_path"

ssh $user@$4  "test ! -d $apps_path||(cd $user_home/apps/$1;rm -rf current)"

rsync -aou -vzrtopg --delete --progress $app_release_path/$3/*  $user@"$4":/$user_home/apps/$1/$2/$3/

rsync -aou -vzrtopg --delete --progress $app_release_path/current  $user@"$4":/$user_home/apps/$1/$2/

rsync -aou -vzrtopg --delete --progress "$global_config_path"/*  root@"$4":$global_config_path

rsync -aou -vzrtopg --delete --progress "$pro_config_path"/*  $user@"$4":$pro_config_path