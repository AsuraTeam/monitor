# monitor<br>
运维工作中自己开发的监控系统，功能强大灵活<br>
系统安装简单,配置简单<br>
遇到任何问题可联系作者QQ:270851812@qq.com<br>
<br>
<b>整体架构</b>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img1.png)
<br>

<b>监控大盘</b>
<br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img2.png)
<br>
<b>图像展示</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img3.png)
<br>
<b>系统概览</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img4.png)
<br>
<b>报警统计</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img5.png)
<br>

<font color="red">
   <h1>特别注意:本系统不能在公网开发,只能在私有网络运行，避免数据泄露或篡改</h1>
</font>
<br>

<h3>系统组成部分:</h3><br>
   1、java环境， jdk1.7<br>
   2、tomcat<br>
   3、redis<br>
   4、mysql数据库<br>
   5、mvn<br>
   6、agent<br>
   
 运行系统 Centos 6.5 <br>
 为方便使用,软件包tools/下面已集成mvn, java, redis,tomcat工具，直接可以使用, 无需自行下载<br>
 

<h3>准备工作:</h3><br>
   1、mysql准备好，将cmdb.sql 导入到数据库<br>
     对需要链接的服务器进行授权<br>
     mysql>grant select,update,insert,delete on cmdb.* to monitor@你的ip地址 identified by "aZkl299feM";<br>
     mysql>flush privileges;<br>
   2、准备一个redis服务<br>
   3、修改对应的环境变量<br>
   4、本系统强制依赖时间，请保证所有服务器时间一致<br>
 
<h4> 安装步骤:</h4><br>
   1、安装mysql数据库<br>
   2、安装jdk7<br>
   3、安装redis服务<br>
   4、安装tomcat<br>
   5、安装mvn<br>
   其中redis, tomcat, mvn，jdk7 可以直接使用tools里面的包， 安装程序统一部署到 /home/runtime 目录<br>
   
   目录结构:<br>
   #ls /home/runtime<br>
    jdk7 tomcat_8081 redis maven 数据库自行配置即可<br>
    
<h1>agent安装配置</h1>
<h3>6、安装agent</h3><br>
      1、使用mvn打包<br>
      2、程序运行环境在tools/monitor.tar.bz2, 解压到 /home/runtime/目录<br>
      3、将tools里面的jdk7解压并改名为 /home/runtime/monitor/java/ 目录<br>
      4、修改agent配置文件，将所有v.asura.com替换为你的服务端的地址，不要使用带端口的<br>
         修改redis.server 和server端使用的redis一致<br>
      5、启动 cd /home/runtime/monitor/bin; sh agent start<br>
 
 
 
 
