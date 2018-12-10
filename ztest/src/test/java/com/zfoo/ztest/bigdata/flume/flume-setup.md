#flume搭建

- flume 需要Java1.8以上版本，然后需要配置好Java环境变量


##windows

1. 到Flume官网上http://flume.apache.org/download.html下载软件包，并且解压

2. 进入flume安装目录，修改配置文件cd conf/，增加example.conf
```
# 监听一个指定的目录，即只要应用程序向这个指定的目录中添加新的文件，source组件就可以获取到该信息，并解析该文件的内容，然后写入到channle。
# 写入完成后，标记该文件已完成或者删除该文件。其中 Sink：logger，Channel：memory
# example.conf: A single-node Flume configuration
# Name the components on this agent
# 指定Agent的组件名称
a1.sources = r1
a1.sinks = k1
a1.channels = c1

# Describe/configure the source
# 指定Flume source(要监听的路径)
a1.sources.r1.type = spooldir
a1.sources.r1.spoolDir = D:\\hello

# Describe the sink
# 指定Flume sink
a1.sinks.k1.type = logger

# Use a channel which buffers events in memory
# 指定Flume channel
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100

# Bind the source and sink to the channel
# 绑定source和sink到channel上
a1.sources.r1.channels = c1
a1.sinks.k1.channel = c1
```

3. 启动flume agent
```
flume-ng.cmd  agent -conf ../conf  -conf-file ../conf/example.conf  -name a1  -property flume.root.logger=INFO,console
```

4. 在D:\\hello文件夹下新增文件，flume可以捕获到并且输出在控制台上

###flume的其它配置
1. 监听一个指定的目录，即只要应用程序向这个指定的目录中添加新的文件，source组件就可以获取到该信息，并解析该文件的内容，然后写入到channle。
写入完成后，标记该文件已完成或者删除该文件。其中 Sink：hdfs，Channel：file
```
# Name the components on this agent
a.sources = r1
a.sinks = k1
a.channels = c1

# Describe/configure the source
a.sources.r1.type = spooldir
a.sources.r1.spoolDir = /usr/local/datainput
a.sources.r1.fileHeader = true
a.sources.r1.interceptors = i1
a.sources.r1.interceptors.i1.type = timestamp

# Describe the sink
a.sinks.k1.type = hdfs
a.sinks.k1.hdfs.path = hdfs://master:9000/output
a.sinks.k1.hdfs.writeFormat = Text
a.sinks.k1.hdfs.fileType = DataStream
a.sinks.k1.hdfs.rollInterval = 10
a.sinks.k1.hdfs.rollSize = 0
a.sinks.k1.hdfs.rollCount = 0
a.sinks.k1.hdfs.filePrefix = %Y-%m-%d-%H-%M-%S
a.sinks.k1.hdfs.useLocalTimeStamp = true

# Use a channel which buffers events in file
a.channels.c1.type = file
a.channels.c1.checkpointDir = /usr/flume/checkpoint
a.channels.c1.dataDirs = /usr/flume/data

# Bind the source and sink to the channel
a.sources.r1.channels = c1
a.sinks.k1.channel = c1
```

2. 配置多个source，共同利用一个channel和一个sink将数据写入hdfs中
```
# Name the components on this agent
a1.sources = r1 r2
a1.sinks = k1
a1.channels = c1

# Describe/configure the source

# get log from file
#a1.sources.r1.type = exec
#a1.sources.r1.command = tail -F /opt/123.txt
#a1.sources.r1.channels = c1

#get log from network port
a1.sources.r2.type = netcat
a1.sources.r2.bind = localhost
a1.sources.r2.port = 44445

# Describe the sink
a1.sinks.k1.type = avro
a1.sinks.k1.channel = c1
a1.sinks.k1.hostname = master
a1.sinks.k1.port = 4141

# Use a channel which buffers events in memory
a1.channels.c1.type = memory
a1.channels.c1.capacity = 1000
a1.channels.c1.transactionCapacity = 100


# Bind the source and sink to the channel
a1.sources.r1.channels = c1
a1.sources.r2.channels = c1
a1.sinks.k1.channel = c1
```
