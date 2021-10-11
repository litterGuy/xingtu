# 星图日志分析系统反编译的源码

1. 目前仅试验了运行main函数。不确定打成jar包后，会不会出现查找配置文件路径问题

# 逆向exe

1. 原包使用exe4j打成的exe
2. 运行start.bat脚本
3. 运行cmd，执行命令%Temp%。
4. 在打开的目录中查找e4j开头的文件夹
5. rzb-sa.jar反编译

# 使用
1. maven打包成可执行的jar文件
2. reources目录下的bin和conf俩文件夹需要和jar放置到同一文件夹下。
3. application.yml文件放置到同jar一文件夹下
4. spring-boot.sh脚本放置到jar同一文件夹下
5. 执行spring-boot.sh脚本