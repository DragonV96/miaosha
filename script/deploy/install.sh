#!/bin/sh
# 部署安装脚本

echo "*************************部署开始*************************"
# 安装前环境配置
echo "=============正在关闭selinux...========================="
setenforce 0
sed -i -e "s|^[^#]SELINUX=.*|SELINUX=disabled|" /etc/selinux/config

echo "=============重启防火墙...========================="
service firewalld restart
sleep 5s
echo "=============防火墙开机自启...========================="
systemctl enable firewalld
echo "=============开放ssh的防火墙端口22...========================="
firewall-cmd --zone=public --add-port=22/tcp --permanent
echo "=============开放mysql的防火墙端口3306...========================="
firewall-cmd --zone=public --add-port=3306/tcp --permanent
echo "=============开放ichat的防火墙端口6379...========================="
firewall-cmd --zone=public --add-port=6379/tcp --permanent
echo "=============开放fastdfs的防火墙端口80...========================="
firewall-cmd --zone=public --add-port=80/tcp --permanent
echo "=============开放fastdfs的防火墙端口5672...========================="
firewall-cmd --zone=public --add-port=5672/tcp --permanent
echo "=============重启防火墙...========================="
service firewalld restart

#启动docker服务
echo "=============启动docker服务...========================="
systemctl start docker
sudo systemctl enable docker

#移动文件到指定位置
echo "=============将数据库sql文件移动到指定位置...========================="
mkdir /usr/local/mysql/
cp miaosha.sql /usr/local/mysql/

#加载本地镜像文件
echo "===============加载mysql镜像文件...==============="
docker load -i mysql.tar
echo "===============加载ichat镜像文件...==============="
docker load -i miaosha.tar
echo "===============加载redis镜像文件...==============="
docker load -i redis.tar
echo "===============加载rabbitmq镜像文件...==============="
docker load -i rabbitmq.tar

#创建运行环境容器并启动
echo "===============创建mysql容器并启动...==============="
docker run --name mysql --net=host --restart=always -v /usr/local/mysql/conf:/etc/mysql/conf.d -v /usr/local/mysql/data:/var/lib/mysql -v /usr/local/mysql/log:/log -e MYSQL_ROOT_PASSWORD=root --privileged=true -d mysql:5.7.26 --lower_case_table_names=1

echo "===============创建redis容器并启动...==============="
docker run --name redis --net=host --restart=always -v /usr/local/redis/conf/redis.conf:/etc/redis.conf -v /usr/local/redis/data:/data --privileged=true -d redis:latest /usr/local/bin/redis-server /etc/redis.conf --appendonly yes --requirepass root123

echo "===============创建rabbitmq容器并启动...==============="
docker run --name rabbitmq --net=host --restart=always -v /usr/local/rabbitmq/log:/var/log/rabbitmq -e RABBITMQ_DEFAULT_USER=guest -e RABBITMQ_DEFAULT_PASS=guest -e RABBITMQ_DEFAULT_VHOST=/ -d rabbitmq:3.7.7-management

sleep 60s
#初始化mysql数据库（sql路径为宿主机）
echo "===============初始化ichat数据库脚本miaosha.sql...==============="
docker exec -i mysql sh -c "exec mysql -uroot -proot" < /usr/local/mysql/miaosha.sql

#创建miaosha容器并启动
echo "===============创建miaosha容器并启动...==============="
docker run --name miaosha --net=host --restart=always -m 1024m -e JAVA_OPTIONS="-Xmx768m" -e appName="miaosha.jar" --privileged=true -e -v /usr/local/apps/miaosha/log:/data/log -d miaosha:1.0.0
echo "*************************部署完成*************************"

