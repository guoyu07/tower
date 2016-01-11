#!/sh/bash

prefix="$1"

app_home_dir="$(dirname $(pwd))"

tar -zxvf ../$prefix-service-impl/$prefix-service-impl-1.0-SNAPSHOT-bin.tar.gz

$prefix_service_pid=`ps x | grep $prefix-service-impl-1.0-SNAPSHOT.jar | grep -v grep | awk '{print $1}'`

echo "prefix_service_pid:"$$prefix_service_pid

 [ -n "$"$prefix"_service_pid" ] && kill $$prefix_service_pid

sleep 3

nohup java -Ddubbo.shutdown.hook=true -Dapp.home.dir=$app_home_dir -jar $prefix-service-impl-1.0-SNAPSHOT/lib/$prefix-service-impl-1.0-SNAPSHOT.jar >/dev/null  &