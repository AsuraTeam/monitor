#!/bin/bash
# QQ: 270851812

# 运行系统 Centos 6.5 
# 为方便使用,软件包已集成mvn, java, redis,tomcat工具，直接可以使用
# 脚本用root用户执行
# 准备工作
# 1、mysql准备好，将cmdb.sql 导入到数据库
#    对需要链接的服务器进行授权
#    mysql>grant select,update,insert,delete on cmdb.* to monitor@你的ip地址 identified by "aZkl299feM";
#    mysql>flush privileges;
# 2、准备一个redis服务
# 3、修改对应的环境变量
# 4、本系统强制依赖时间，请保证所有服务器时间一致


## 以上配置完成后修改下面的变量

export mysql_server=10.10.10.10

# redis本脚本自带redis单机服务安装，如果有自己安装好的redis，请修改下面的127.0.0.1为对应的redis服务器地址
# 使用本机只是单机模式，多节点部署需要配置同一个ip地址
export redis_server=127.0.0.1


if [ "$mysql_server" == "10.10.10.10" ] ; then
  echo "请配置好mysql服务"
  exit
fi

# 程序运行路径
RUNPATH="/home/runtime/"
mkdir $RUNPATH 

cd tools
rsync -var tomcat_8081d /etc/init.d/
chkconfig --add tomcat_8081d
chkconfig tomcat_8081d on

tar xjf m2.tar.bz2
tar xjf jdk7.tar.bz2
tar xjf redis.tar.bz2
tar xzf maven.tar.gz
tar xjf tomcat_8081.tar.bz2

rsync -arz redis/ $RUNPATH/redis/
rsync -arz jdk7/ $RUNPATH/jdk7/
rsync -arz maven/ $RUNPATH/maven/
rsync -arz tomcat_8081/ $RUNPATH/tomcat_8081/
rsync -arz .m2/ ~/.m2/


if [ "$redis_server" == "127.0.0.1" ] ; then
    cd $RUNPATH/redis/bin && ./redis-server ../conf/redis.conf
    echo "cd $RUNPATH/redis/bin && ./redis-server ../conf/redis.conf" >> /etc/rc.local
fi

# 设置环境变量
grep MAVEN_HOME /etc/profile 
if [ $? -gt 0 ] ; then
cat >>/etc/profile <<EOF
export MAVEN_HOME=/home/runtime/maven
export PATH=\${PATH}:\${MAVEN_HOME}/bin
export JAVA_HOME=/home/runtime/jdk7
export CLASSPATH=\$JAVA_HOME/jre/lib/rt.jar:\$JAVA_HOME/lib/dt.jar:\$JAVA_HOME/lib/tools.jar
export PATH=\$PATH:\$JAVA_HOME/bin:\$CATALINA_HOME/bin
EOF
fi


cd ../
sed -i "s/redis.server=10.10.10.10/redis.server=$redis_server/g" src/main/resources/system.properties 
sed -i "s/os.dbm.com/$mysql.server/g" src/main/resources/jdbc.properties

source /etc/profile
cd server
mvn clean package
if [ $? -eq 0 ] ; then
   unzip  target/*.war -d  $RUNPATH/tomcat_8081/webapps/ROOT 
   cd $RUNPATH/tomcat_8081/bin; sh restart.sh
else
   echo "mvn失败了"
fi
