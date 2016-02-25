#!/bin/bash

######## 参数1表示项目名称

if [ "$1" != "oft" ]&&[ "$1" != "b2b" ]&&[ "$1" != "ftis" ];then

echo "不是有效的项目名称oft|b2b||ftis请重新输入"

exit

fi

if [ -z $2 ]; then
    branch="master"
else
    branch=$2
fi

source_base="/root/code/projects/workspace"

app_source_path="$source_base/$1_$branch"

cd $app_source_path
echo $(dirname $(pwd))
git tag -a $3 -m '$3'
git push origin $3