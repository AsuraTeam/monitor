# 版本<br>
# go大于 1.7.5 <br>
# 安装go后<br>
# 在当前路径执行<br> 
sh build.sh <br>
# 可以在 bin 目录下 生成 monitor.daemon  和 monitor 俩个二进制文件<br>

# 程序运行<br>

mkdir /home/runtime/monitor/ -p<br>
cd /home/runtime/monitor/<br>
mkdir logs configure bin <br>
cp -v configure/monitor.conf configure/ <br>

# 请用普通用户启动,不要用root跑监控程序<br>
# 为程序不可避免的异常退出,请加监控程序,或使用任务计划定期检查<br>
# 软件 supervisor 
# 任务计划  */10 * * * * /home/你的用户/runtime/monitor/bin/monitor.daemon /home/你的用户/runtime/monitor/configure/monitor.conf 2>/dev/null 


# 程序启动
# 不要使用root
nohup /home/你的用户/runtime/monitor/bin/monitor.daemon /home/你的用户/runtime/monitor/configure/monitor.conf &

# 程序分发
# 将 /home/你的用户/runtime/  目录下打包分发到其他可以运行的机器启动即可
