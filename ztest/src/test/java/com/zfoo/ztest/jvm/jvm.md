####安装jps等工具
yum install -y java-1.8.0-openjdk-devel


//l:输出主类的全名；v：输出jvm启动时的参数  
jps -l



//每250ms查询一次进程15485垃圾收集状况，一共查询20次   
jstat -gc 15485 250 20


//生成进程15485堆转储快照文件   
jmap -dump:format=b,file=idea.dumpfile 15485



//跟踪进程15485并打印堆栈信息   
jstack -l 15485
