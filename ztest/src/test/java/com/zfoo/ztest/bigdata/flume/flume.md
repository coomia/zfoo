##一、Flume

####简介
flume 作为 cloudera 开发的实时日志收集系统，受到了业界的认可与广泛应用。

####特点
flume是一个分布式、可靠、和高可用的海量日志采集、聚合和传输的系统。

支持在日志系统中定制各类数据发送方，用于收集数据;同时，Flume提供对数据进行简单处理，并写到各种数据接受方(比如文本、HDFS、Hbase等)的能力 。

flume的数据流由事件(Event)贯穿始终。事件是Flume的基本数据单位，它携带日志数据(字节数组形式)并且携带有头信息，这些Event由Agent外部的Source生成，当
Source捕获事件后会进行特定的格式化，然后Source会把事件推入(单个或多个)Channel中。你可以把Channel看作是一个缓冲区，它将保存事件直到Sink处理完该事件。
Sink负责持久化日志或者把事件推向另一个Source。
 
- flume的可靠性，当节点出现故障时，日志能够被传送到其他节点上而不会丢失，Flume提供了三种级别的可靠性保障，从强到弱依次分别为：
    - end-to-end（收到数据agent首先将event写到磁盘上，当数据传送成功后，再删除；如果数据发送失败，可以重新发送。），
    - Store on failure（这也是scribe采用的策略，当数据接收方crash时，将数据写到本地，待恢复后，继续发送），
    - Besteffort（数据发送到接收方后，不会进行确认）。

- flume的可恢复性
    - 还是靠Channel。推荐使用FileChannel，事件持久化在本地文件系统里(性能较差)。
    
####术语
- Client：Client生产数据，运行在一个独立的线程。
- Event：一个数据单元，消息头和消息体组成。（Events可以是日志记录、 avro 对象等。）
- Flow：Event从源点到达目的点的迁移的抽象。
- Agent：一个独立的Flume进程，包含组件Source、 Channel、 Sink。（Agent使用JVM 运行Flume。每台机器运行一个agent，但是可以在一个agent中包含多个sources和sinks。）
- Source：数据收集组件。（source从Client收集数据，传递给Channel）
- Channel：中转Event的一个临时存储，保存由Source组件传递过来的Event。（Channel连接 sources 和 sinks ，这个有点像一个队列。）
- Sink：从Channel中读取并移除Event，将Event传递到FlowPipeline中的下一个Agent（如果有的话）（Sink从Channel收集数据，运行在一个独立线程。）

##二、Flume Core（Flume 的一些核心概念）

####2.0 Agent结构　　
Flume 运行的核心是 Agent。Flume以agent为最小的独立运行单位。一个agent就是一个JVM。它是一个完整的数据收集工具，含有三个核心组件，
分别是source、 channel、 sink。通过这些组件， Event 可以从一个地方流向另一个地方，如下图所示。
![Image text](image/flume-agent-structure.png)
　　
####2.1 Source
Source是日志数据的收集端，负责将数据捕获后进行特殊的格式化，将数据封装到事件（event） 里，然后将事件推入Channel中。 Flume提供了很多内置的
Source， 支持 Avro， log4j， syslog 和 http post(body为json格式)。可以让应用程序同已有的Source直接打交道，如AvroSource，
SyslogTcpSource。 如果内置的Source无法满足需要， Flume还支持自定义Source。
![Image text](image/flume-source-structure.png)
source类型：
![Image text](image/flume-source-type.png)

- avro source：可以监听和收集指定端口的日志，使用avro的source需要说明被监听的主机ip和端口号，下面给出一个具体的例子：
```
a1.sources = r1
a1.channels = c1
a1.sources.r1.type = avro
a1.sources.r1.channels = c1
a1.sources.r1.bind = 0.0.0.0
a1.sources.r1.port = 4141
```

- exec source，exec可以通过指定的操作对日志进行读取，使用exec时需要指定shell命令，对日志进行读取，下面给出一个具体的例子：
```
a1.sources = r1
a1.channels = c1
a1.sources.r1.type = exec
a1.sources.r1.command = tail -F /var/log/secure
a1.sources.r1.channels = c1
```

- spooling-directory source，spo_dir可以读取文件夹里的日志，使用时指定一个文件夹，可以读取该文件夹中的所有文件，需要注意的是该文件夹中的文件在读取过程中不能修改，同时文件名也不能修改。下面给出一个具体的例子：
```
agent-1.channels = ch-1
agent-1.sources = src-1

agent-1.sources.src-1.type = spooldir
agent-1.sources.src-1.channels = ch-1
agent-1.sources.src-1.spoolDir = /var/log/apache/flumeSpool
agent-1.sources.src-1.fileHeader = true
```

- syslog source，syslog可以通过syslog协议读取系统日志，分为tcp和udp两种，使用时需指定ip和端口，下面给出一个udp的例子：
```
a1.sources = r1
a1.channels = c1
a1.sources.r1.type = syslogudp
a1.sources.r1.port = 5140
a1.sources.r1.host = localhost
a1.sources.r1.channels = c1
```

####2.2 Channel
Channel是连接Source和Sink的组件，大家可以将它看做一个数据的缓冲区（数据队列），它可以将事件暂存到内存中也可以持久化到本地磁盘上， 直
到Sink处理完该事件。介绍两个较为常用的Channel， MemoryChannel和FileChannel。
Channel类型：
![Image text](image/flume-channel-type.png)

- Flume的channel种类并不多，最常用的是memory channel，下面给出例子：
```
a1.channels = c1
a1.channels.c1.type = memory
a1.channels.c1.capacity = 10000
a1.channels.c1.transactionCapacity = 10000
a1.channels.c1.byteCapacityBufferPercentage = 20
a1.channels.c1.byteCapacity = 800000
```

####2.3 Sink
Sink从Channel中取出事件，然后将数据发到别处，可以向文件系统、数据库、 hadoop存数据， 也可以是其他agent的Source。在日志数据较少时，可
以将数据存储在文件系统中，并且设定一定的时间间隔保存数据；在日志数据较多时，可以将相应的日志数据存储到Hadoop中，便于日后进行相应的数据分析。 
![Image text](image/flume-sink-structure.png)
Sink类型：
![Image text](image/flume-sink-type.png)

- logger sink，就是将收集到的日志写到flume的log中，简单实用，但是在实际项目中没有太大用处。
```
agent1.sinks.sink1.type = logger
```

- avro sink，avro可以将接受到的日志发送到指定端口，供级联agent的下一跳收集和接受日志，使用时需要指定目的ip和端口：例子如下：
```
a1.channels = c1
a1.sinks = k1

a1.sinks.k1.type = avro
a1.sinks.k1.channel = c1
a1.sinks.k1.hostname = 10.10.10.10（目标主机的IP 和 端口号）
a1.sinks.k1.port = 4545
```

- file roll sink，可以将一定时间内收集到的日志写到一个指定的文件中，具体过程为用户指定一个文件夹和一个周期，然后启动agent，
这时该文件夹会产生一个文件将该周期内收集到的日志全部写进该文件内，直到下一个周期再次产生一个新文件继续写入，以此类推，周而复始。下面给出一个具体的例子：
```
a1.channels = c1
a1.sinks = k1
a1.sinks.k1.type = file_roll
a1.sinks.k1.channel = c1
a1.sinks.k1.sink.directory = /var/log/flume
```

- hdfs sink，hdfs与file roll有些类似，都是将收集到的日志写入到新创建的文件中保存起来，但区别是file roll的文件存储路径为系统的本地路径，
而hdfs的存储路径为分布式的文件系统hdfs的路径，同时hdfs创建新文件的周期可以是时间，也可以是文件的大小，还可以是采集日志的条数。具体实例如下：
```
a1.channels = c1
a1.sinks = k1
a1.sinks.k1.type = hdfs
a1.sinks.k1.channel = c1
a1.sinks.k1.hdfs.path = /flume/events/%y-%m-%d/%H%M/%S
a1.sinks.k1.hdfs.filePrefix = events-
a1.sinks.k1.hdfs.round = true
a1.sinks.k1.hdfs.roundValue = 10
a1.sinks.k1.hdfs.roundUnit = minute
```

##三、Flume拦截器、数据流以及可靠性

####3.1 Flume拦截器
当我们需要对数据进行过滤时，除了我们在Source、 Channel和Sink进行代码修改之外， Flume为我们提供了拦截器，拦截器也是chain形式的。
拦截器的位置在Source和Channel之间，当我们为Source指定拦截器后，我们在拦截器中会得到event，根据需求我们可以对event进行保留还是
抛弃，抛弃的数据不会进入Channel中。
![Image text](image/flume-interceptor.png)　　

####3.2 Flume数据流
1. Flume 的核心是把数据从数据源收集过来，再送到目的地。为了保证输送一定成功，在送到目的地之前，会先缓存数据，待数据真正到达目的地后，删除自己缓存的数据。
2. Flume 传输的数据的基本单位是 Event，如果是文本文件，通常是一行记录，这也是事务的基本单位。 Event 从 Source，流向 Channel，再到 Sink，
　  本身为一个 byte 数组，并可携带 headers 信息。 Event 代表着一个数据流的最小完整单元，从外部数据源来，向外部的目的地去。
![Image text](image/flume-stream-1.png)

值得注意的是，Flume提供了大量内置的Source、Channel和Sink类型。不同类型的Source,Channel和Sink可以自由组合。组合方式基于用户设置的配置文件，非常灵活。
比如：Channel可以把事件暂存在内存里，也可以持久化到本地硬盘上。Sink可以把日志写入HDFS, HBase，甚至是另外一个Source等等。Flume支持用户建立多级流，
也就是说，多个agent可以协同工作，并且支持Fan-in、Fan-out、Contextual Routing、Backup Routes，这也正是Flume强大之处。如下图所示：
![Image text](image/flume-stream-2.png)
　　

####3.3 Flume可靠性
Flume 使用事务性的方式保证传送Event整个过程的可靠性。 Sink 必须在Event 被存入 Channel 后，或者，已经被传达到下一站agent里，又或者，
已经被存入外部数据目的地之后，才能把 Event 从 Channel 中 remove 掉。这样数据流里的 event 无论是在一个 agent 里还是多个 agent 之间流转，
都能保证可靠，因为以上的事务保证了 event 会被成功存储起来。比如 Flume支持在本地保存一份文件 channel 作为备份，而memory channel 将
event存在内存 queue 里，速度快，但丢失的话无法恢复。

##四、Flume使用场景
Flume在英文中的意思是水道， 但Flume更像可以随意组装的消防水管，下面根据官方文档，展示几种Flow。

####4.1、多个agent顺序连接
可以将多个Agent顺序连接起来，将最初的数据源经过收集，存储到最终的存储系统中。这是最简单的情况，一般情况下，应该控制这种顺序连接的
Agent 的数量，因为数据流经的路径变长了，如果不考虑failover的话，出现故障将影响整个Flow上的Agent收集服务。 
![Image text](image/flume-usage-1.png)

####4.2、多个Agent的数据汇聚到同一个Agent 
这种情况应用的场景比较多，比如要收集Web网站的用户行为日志， Web网站为了可用性使用的负载集群模式，每个节点都产生用户行为日志，可以为
每个节点都配置一个Agent来单独收集日志数据，然后多个Agent将数据最终汇聚到一个用来存储数据存储系统，如HDFS上。
![Image text](image/flume-usage-2.png)

####4.3、多级流
Flume还支持多级流，什么多级流？结合在云开发中的应用来举个例子，当syslog， java， nginx、 tomcat等混合在一起的日志流开始流入一个agent
后，可以agent中将混杂的日志流分开，然后给每种日志建立一个自己的传输通道。
![Image text](image/flume-usage-3.png)
　　

####4.4、load balance功能
Agent1是一个路由节点，负责将Channel暂存的Event均衡到对应的多个Sink组件上，而每个Sink组件分别连接到一个独立的Agent上 。
![Image text](image/flume-usage-4.png)
