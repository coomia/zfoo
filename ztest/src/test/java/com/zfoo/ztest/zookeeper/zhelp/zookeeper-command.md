####Zookeeper监控工具
netflix:exhibitor   监控zookeeper，增删改查  
zabbix

####1.连接
/opt/zookeeper/bin/zkCli.sh -timeout 5000 -server 192.168.238.128:2181   启动zookeeper的客户端  

####2.查看
h 查看zookeeper的所有数据结构  

ls /  列出根目录得所有节点  
ls2 / ls+stat  

get /node_1    查询/node_1节点的信息

stat /node_1  查看/node_1节点的状态  

    cZxid        创建事务的id，create zookeeper transaction id zookeeper中每一次对数据的操作的是一个事务，会分配一个事务的id  
    ctime
    mZxid         Mofify 节点最新一次更新发生时的zxid  
    mtime
    pZxid          是与该节点的子节点（或该节点）的最近一次创建/删除子节点的事务id，修改子节点的数据内容不算  
    cversion  
    dateVersion  
    aclVersion  
    ephemeralOwner    ephemeral adj.  朝生暮死的, 短暂的, 短命的  临时节点 如果该节点为ephemeral节点, ephemeralOwner值表示与该节点绑定的session id. 如果该节点不是ephemeral节点, ephemeralOwner值为0.   
    dataLength  
    numChildren  


 
####3.更新
set /node_1 "set Method"    更新节点的信息  

create -e /node_2 "hello node_2"     创建一个node_2的临时节点  
create -s /node_3 "hello node_3"     创建一个node_3的顺序节点，会自增主键  

delete /node_1  删除/node_1节点，如果节点下有另外的节点则不能删除  
rmr /node_1     循环删除/node_1  

setquota -n 2 /node_1   -n设置子节点的数目为2，如果超过了，会记录日志在bin/zookeeper.out中；-b，设置数据长度值（包括子节点）  
delquota -n /node_1    删除配额  

###Zookeeper安装


####1.Windows下的安装
```
1.下载windows下的zookeeper安装包并解压到任意目录
2.加入conf目录，重命名zoo_sample.cfg为zoo.cfg
3.编辑zoo.cfg：
    dataDir=D:\\Java\\zookeeper-3.4.10\\zookeeper-3.4.10\\data      # 存储数据的目录
    dataLogDir=D:\\Java\\zookeeper-3.4.10\\zookeeper-3.4.10\\logs   # 日志目录
```

####2.Linux下的安装
```
将zookeeper-3.4.10.tar.gz下载到/opt目录，这个目录是专门放下载的第三方软件的目录  
tar -xzvf zookeeper-3.4.10.tar.gz   解压  
mv zookeeper-3.4.10 zookeeper      换一个好记一点的目录  
cd zookeeper/conf     进入zookeeper配置文件目录  
cp zoo_sample.cfg zoo.cfg     建立一个配置文件，zoo_sample.cfg是一个demo  
vi zoo.cfg       改变dataDir=/var/zookeeper    这个是zookeeper的共享文件放置的目录，快照文件目录  
在最后一行加上，服务器的ip，格式：server.id=host:port:port，  
server.1=192.168.238.128:8888:9999  
server.2=192.168.238.129:8888:9999  
server.3=192.168.238.130:8888:9999  

关闭防火墙  
getenforce  #查看SELinux是否开启，建议关闭，  
#enforcing：强制模式，代表 SELinux 运作中；disabled：关闭  
vim /etc/selinux/config   #设置SELinux的开启和关闭，必须要重新启动  

firewall-cmd --zone=public --add-port=2181/tcp --permanent  
firewall-cmd --zone=public --add-port=8888/tcp --permanent  
firewall-cmd --zone=public --add-port=9999/tcp --permanent  
firewall-cmd --reload  
开放端口  
 
cd /var  
mkdir zookeeper   在var目录创建一个zookeeper文件夹  
cd zookeeper       
vi myid          在文件里写1  

cd /opt/zookeeper/bin  
./zkServer.sh start   启动zookeeper服务器    /opt/zookeeper/bin/zkServer.sh start  
./zkServer.sh stop               
./zkCli.sh timeout 5000 -server 192.168.238.128：2181  

yum install telnet  
telnet 192.168.238.128 2181  
```
