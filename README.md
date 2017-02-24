# monitor<br>
运维工作中自己开发的监控系统，功能强大灵活<br>
系统安装简单,配置简单，相比zabbix, nagios,cacti，小米监控等都使用相当简单。只需要会写脚本，语言不限就可以实现任意监控需求。<br>
系统安装简单,配置简单<br>
<br>
1、数据采集免配置: agent自发现、agent主动推送, 任何数据自定义脚本<br>
2、可扩展性, 扩展简单,随时扩，随心扩<br>
3、历史数据查询, 可以秒级返回全年数据趋势图， 多个指标数据, 趋势图更明晰:<br>
   3天 7天，15天，30天，60天，90天，120天，180天，240天，360天时间段趋势<br>
4、架构设计高可用: 整个系统无核心单点，所涉及到的 <br>
   负载均衡(nginx, haproxy, lvs)都可以用来负载server端， mysql(无数据压力，不做数据存储), redis(需要高可用配置), 共享存储设备(本地磁盘，nfs等).<br>
5、任何数据图像实时查看,历史查看功能<br>
6、针对统一系统,基础监控无需添加配置,自动添加(比如cpu,负载,磁盘使用率,网络流量,ss状态信息等,都可以自定义实现)<br>
7、报警组配置,支持微信，钉钉，手机，邮件功能(微信,钉钉需要单独处理,不是人都有的)<br>
8、分布式监控,每个装agent的都是一个监控系统,除配置外(配置只能在server端完成)<br>
9、自定义数据上报时间,最低5秒上报一次数据<br>
10、不同server性能对比,更简单,更清晰<br>
11、支持图像搜索,主机名,ip地址搜索图像<br>
12、图像收藏功能,常用的直接在收藏列表点开查看<br>
13、支持ldap认证登陆<br>
</br>

欢迎加入 阿修罗监控系统 QQ群 149469467<br>
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
<b>报警信息查看</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img9.png)
<br>
<b>图像展示</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img3.png)
<br>
<b>大图显示</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img12.png)
<br>
<b>多维数据显示(任何机器任何数据组合对比)</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img11.png)
<br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img15.png)
<br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img16.png)
<br>
<b>图像搜索功能</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img13.png)
<br>
<b>图像收藏功能</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img14.png)
<br>
<b>更多时间段图像(缩小后的)</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img10.png)
<br>
<b>系统概览</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img4.png)
<br>
<b>报警统计</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img5.png)
<br>
<b>指标报警分析报表</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img6.png)
<br>
<b>监控添加页面</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img7.png)
<br>
<b>脚本添加页面</b></br>
<br>
![image](https://github.com/AsuraTeam/monitor/blob/master/images/img8.png)
<br>

<font color="red">
   <h1>特别注意:本系统不能在公网开放,只能在私有网络运行，避免数据泄露或篡改</h1>
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
 
<br>
系统登录
系统默认密码用户名为 admin admin<br>
登录后请修改密码<br>
如果需要ldap登录，需要修改配置文件进行配置ldap服务信息<br>
</br>
 
 
<br>
<h1>配置流程</h1> 
<br>
1、配置资源信息<br>
   点击 资源信息->资源配置 配置下列资源数据<br>
   该模块主要是记录资源数据<br>
   1、机房配置(可选)<br>
   2、机柜配置(可选)<br>
   3、环境配置<br>
   4、业务线配置<br>
   5、管理员配置<br>
   6、服务器类型配置<br>
   7、服务类型配置<br>
   8、系统类型配置<br>
   以上配置只为第9条准备<br>
   9、服务器配置<br> 
2、配置告警相关配置<br>
   点击 监控分析->监控配置->告警发送配置 配置下列信息<br>
   注意：发送报警只对组发送<br>
   1、配置联系组<br>
   2、配置联系人<br>
   3、消息通道配置<br>
      消息通道配置默认有邮件和手机的配置模板，自行修改即可

配置完以上信息基本就可以跑演示版本了<br>

演示步骤:<br>
1、首先在服务端访问
   curl http://127.0.0.1:8081/monitor/configure/cache/all 初始化缓存信息<br>
2、启动agent
   启动agent后，稍等一会就可以看到一个演示版本了
