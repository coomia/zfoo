##查询系统状态相关指令
####1.计算机基础信息查询
```
uname -a        #显示所有系统相关的信息   
localectl       #系统语系
dmidecode -t 1  #显示出整个系统的硬件信息，例如主板型号等等
lspci           #查阅您系统内的 PCI 总线相关设备
```


####2.程序和服务相关
```
jobs -l         #查看目前后台工作的程序
fg %1           #取出第一个后台运行的程序
bg %2           #让第二个后台程序运行
kill -9 %3      #强制删除一个第3个不正常的工作，jobs里的程序
kill -15 %2     #以正常的程序方式终止一个程序。和-9 是不一样的
killall -9 httpd  
                #强制终止所有以httpd启动的程序，killall -i -9 bash，每次询问

kill -9 4597    #完全终止一个程序PID，包括执行它的程序
killall -9 cupsd   
ps -l           #属于你自己这次登入的PID相关信息列示出来(只与自己的bash有关)
ps aux          #列出目前所有的正在内存当中的程序
ps axjf         #列出类似程序树的程序显示
pstree -Aup     #列出目前系统上面所有的程序树，同时秀出PID与users

top -d 2        #每两秒钟更新一次top，观察整体信息，默认为5秒

top -d 2 -p $$  #$$当前程序的pid，观察当前程序
                #输入r然后输入这个PID号，改变NI的值，让其优先执行
                #P：以CPU的使用资源排序显示；M：以Memory的使用资源排序显示；
                #T：由该Process使用的CPU时间累积(TIME+)排序。
                
free -m         #显示目前系统的内存容量，单位KB
vmstat -d       #系统上面所有的磁盘的读写状态
fuser -mvu /proc  
                #找到所有使用到/proc这个文件系统的程序吧
lsof -u root | grep bash  
                #属于root 的bash这支程序所开启的文件	
pidof bash      #列出目前系统上面bash的pid
```


##软件安装
```
yum install package_name
rpm -ivh package_name  
                #yum更强大不仅支持线上还支持线下
rpm -q logrotate    
                #RedHat Package Manager，是否有安装logrotate这个软件
rpm -ql logrotate   
                #属于该软件所提供的所有目录与文件
rpm -qi logrotate    
                #列出logrotate这个软件的相关说明数据
                #-qa：列出所有的已经安装软件名称，后面加名称表示安装依赖
                #-qc：列出该软件的所有配置文件（找出在/etc/下面的文件名而已）
                #-qd：列出该软件的所有说明文档（找出与man有关的文件而已）
                #-qR：列出与该软件有关的相依软件所含的文件（Required的意思）
                #-qf：由后面接的文件名称，找出该文件属于哪一个已安装的软件；
rpm -Va         #列出目前系统上面所有可能被更动过的文件
rpm -V logrotate 
                #列出你的Linux内的logrotate 这个软件是否被更动过
rpm -e logrotate  
                #移除logrotate
rpm --rebuilddb  
                #重建数据库RPM数据库

yum info mdadm  #找出mdadm这个软件的功能为何
yum repolist all   	
                #yellowdog updater modified，列出yum服务器上面提供的所有软件名称
yum clean all     
                #删除已下载过的所有容器的相关数据 (含软件本身与列表)
yum list updates  
                #列出目前服务器上可供本机进行升级的软件有哪些
yum list java*    
                #查看云端所有可用的java版本
yum install madam     
                #安装madam
yum remove madam   
                #移除madam
yum grouplist   #目前软件库与本机上面的可用与安装过的软件群组有哪些
yum groupinfo "Scientific Support"
yum -y update   #全系统更新

cat /etc/services | grep "www"  #/etc/services记录了所有服务端口，查看www服务的端口
systemctl list-unit-files --type=service  #查看所有服务
systemctl status atd.service       #查看atd这个服务的状态
systemctl stop atd.service    #停止这个服务，但是下次开机还会重启
systemctl disable atd.service  #设置开机不是自动重启 
systemctl start atd.service    #启动atd服务
systemctl restart atd.service  #重新启动


##通用的安装软件的步骤：
yum install httpd
systemctl daemon-reload
systemctl start httpd    # 启动httpd服务
systemctl enable httpd  # 开机启动
systemctl status httpd
```


##指令说明/用法/历史使用
```
info date       #查看date使用方法
man date        #查看date使用方法（推荐）
man -f date     #查看date的使用方法文档的位置
which ifconfig  #寻找可执行命令的位置
type ifconfig   #type更强大，可以取代which

alias           #命令别名

history         #历史输入命令的记录，echo $HISTSIZE
history -c      #清空历史命令
history -w      #立刻将目前的资料写入~/.bash_history当中
history  >>  testfile     #>, >> 数据流重导向：输出，分别是覆盖和追加
!n              #执行history的n条命令
                
                
tab             #接在一串指令的第一个字的后面，则为命令补全
ctrl+c          #cancel
ctrl+d          #exit
ctrl+z          #暂停程序

/usr/share/doc  #说明文件保存的位置
```

##时间相关指令
```
date            #软件时钟：Linux自己的系统时间，由 1970/01/01 开始记录的时间参数
hwclock         #硬件时钟：计算机系统在 BIOS 记录的实际时间，这也是硬件所记录的
hwclock -w      #将软件时钟写入 BIOS
ntpdate time.servers.ip && hwclock -w  
                #Linux 进行网络校时：最简单的方法即是使用
timedatectl     #查看时区
timedatectl set-timezone "Asia/Taipei"    
                #设置时区
timedatectl set-time "2018-01-22 11:29"   
                #设置时间,同事设置软件时钟和硬件时钟
```


##账号管理相关指令
####1.账号的基本命令
```
ntsysv          #类图形接口，管理启动的服务
startx          #打开图形窗口

run level 0     #关机
run level 3     #纯文本模式
run level 5     #图形接口模式
run level 6     #重新启劢
init 0          #关机
ulimit -a       #出你目前身份(假设为root)的所有限制资料数值
ulimit -f 10240 #限制用户仅能建立10MBytes以下的容量的文件

exit            #注销当前账号
id              #查询某人或自己的相关UID/GID信息
who             #观察系统使用的状态
whoami          #显示目前的身份 
passwd          #修改自己的密码
groups          #显示所有的群组
```
####2.创建一个账号的流程
```
useradd -r sun  #建立一个账号sun
passwd sun      #修改sun的密码

passwd -S sun   #列出密码相关参数，即shadow档案内的大部分信息
passwd -l sun   #将sun的密码锁定
passwd -u sun   #将sun的密码解除锁定
change -l sun   #列出sun的详细密码参数

cp -a /etc/skel /home/sun     
                #skeletal骨骼的，此文件是一个用户创建的时候一个模板
                
chown -R sun:sun /home/sun  
                #文件夹下全部的归属都改变为sun
                
chmod 700 /home/sun       
                #仅要修改目录的权限而非内部文件的权限
                
userdel -r sun  #删除一个账号，连同家目录一起删除
```

####3.创建一个group
```
groupadd groupsun
groupdel groupsun

useradd -G groupsun -s -m sun
                #为sun添加群组
```


##查看Linux风险，登录状态相关指令
```
find /home -name .bashrc > list_right 2> list_error  
                #<, << 数据流重导向：输入，将stdout和stderr存到不同的文件中去
ls -al /etc | less 
                #管线命令能处理stdout，但是不能处理stderr
last | grep "root" 
                #last登录记录，取出包含root的那几行
last | grep -n "root"
                #加上行号
last | grep -v "root"
                #只要没有root就取出
last | sort     #
```

##开关机相关指令
```
sync            #将数据同步写入硬盘中，只不过一般账号用户所更新的硬盘数据就仅有自己的数据，不像root可以更新整个系统中的数据了
shutdown        #关机，只有 root 有权力

shutdown -h now    
                #立刻关机，等同于halt和poweroff
                
shutdown -h 20:25  
                #系统在今天的20:25分会关机，若在21:25下达此指令，则隔天才关机
                
shutdown -h +10    
                #系统再过十分钟后自动关机
                
shutdown -r now    
                #等于reboot
                
shutdown -r +30 'The system will reboot'   
                #再过三十分钟系统会重新启动，显示后面的讯息给所有在在线的使用者
```

##环境变量
```
env             #查看系统所有变量 set还会把自定义的变量打印出来
read -p "Please keyin your name: " -t 30 named  
                #30秒内输入名字，输入作为named变量
stty -a         #列出所有的按键与按键的内容
echo $PATH      #PATH路径，这个是用来指定执行文件执行的时候，指令搜寻的目录路径
echo $RANDOM    #获取随机数，每次都不一样
echo $SHELL     #当前使用的shell位置
echo $$         #shell使用的线程号
echo $?         #shell的回传值
```

##shell脚本
```
sh -n scriptfile  
                #不要执行script，仅查询语法的问题
sh -v scriptfile
                #再执行sccript前，先将scripts的内容输出到屏幕上；
sh -x scriptfile
                #将使用到的script语句内容显示到屏幕上，这是很有用的参数！
```


##cron定时任务相关指令
```
/etc/init.d/atd restart  
                #启动atd服务
systemctl enable atd
                #设置成开机启动
at now + 5 minutes   
                #启动一个任务5分钟后执行，ctrl+d结束
atq             #查询目前主机上面有多少个at工作排程，下达指令必须用绝对路径
atrm 5          #移除第五个at任务

crontab -e      #编辑cron任务，0 12 * * * mail root -s "at 12:00"，每天12点提醒
                #分钟，小时，日期，月份，周。不同于java中更细致的划分，注意
crontab -l      #查看系统所使用的全部cron表达式
crontab -r      #移除全部的cron表达式任务
#很多时候被植入木马都是以例行命令的方植入，
#所以可以藉由检查/var/log/cron的内容来视察是否有其它非法cron执行了
```


##其它
```
mail jaysunxiao -s "nice to meet you"  
                #以  .  结束
mail            #使用mail查看邮件，？为使用帮助
write jaysunxiao
                #给jaysunxiao发送信息
wall "I will shutdown my linux server..."  
                #给全部的使用者发送信息
```

