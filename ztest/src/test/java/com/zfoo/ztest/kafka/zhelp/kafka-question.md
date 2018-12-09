####1.传统的消息传递方式和kafka的区别？
传统的消息有两种模式：队列和发布订阅。  
在队列模式：消费者池从服务器读取消息（每个消息只被其中一个读取）;   
发布订阅模式：消息广播给所有的消费者。  
这两种模式都有优缺点，队列的优点是允许多个消费者瓜分处理数据，这样可以扩展处理。  
但是，队列不像多个订阅者，一旦消息者进程读取后故障了，那么消息就丢了。  
而发布和订阅允许你广播数据到多个消费者，由于每个订阅者都订阅了消息，所以没办法缩放处理。  


kafka同事满足以上两种模式的消息传递方式：  
通过并行topic的parition —— kafka提供了顺序保证和负载均衡。
每个partition仅由同一个消费者组中的一个消费者消费到。并确保消费者是该partition的唯一消费者，并按顺序消费数据（只能保证一个分区的顺序）。  
每个topic有多个分区，则需要对多个消费者做负载均衡，但请注意，相同的消费者组中不能有比分区更多的消费者，否则多出的消费者一直处于空等待，不会收到消息。   

如果所有的consumer都具有相同的group,这种情况和queue模式很像;消息将会在consumers之间负载均衡.
如果所有的consumer都具有不同的group,那这就是"发布-订阅";消息将会广播给所有的消费者.

####2.请说明Kafka相对传统技术有什么优势?
wicked fast 变态的快，单一的Kafka代理可以处理成千上万的客户端，每秒处理数兆字节的读写操作；  
发布订阅；  
分布式；  
复制集实现了数据同步，故障转移;


####3.Kafka服务器能接收到的最大信息是多少？kafka中如何处理超大消息？
Kafka服务器可以接收到的消息的最大大小是1MB。

Kafka设计的初衷是迅速处理短小的消息，一般10K大小的消息吞吐性能最好（可参见LinkedIn的kafka性能测试）。
但有时候，我们需要处理更大的消息，比如XML文档或JSON内容，一个消息差不多有10-100M，这种情况下，Kakfa应该如何处理？
针对这个问题，有以下几个建议：
- 最好的方法是不直接传送这些大的数据。如果有共享存储，如NAS, HDFS, S3等，可以把这些大的文件存放到共享存储，然后使用Kafka来传送文件的位置信息。
- 第二个方法是，将大的消息数据切片或切块，在生产端将数据切片为10K大小，使用分区主键确保一个大消息的所有部分会被发送到同一个kafka分区（这样每一部分的拆分顺序得以保留），如此以来，当消费端使用时会将这些部分重新还原为原始的消息。
- 第三，Kafka的生产端可以压缩消息，如果原始消息是XML，当通过压缩之后，消息可能会变得不那么大。在生产端的配置参数中使用compression.codec和commpressed.topics可以开启压缩功能，压缩算法可以使用GZip或Snappy。
  
 不过如果上述方法都不是你需要的，而你最终还是希望传送大的消息，那么，则可以在kafka中设置下面一些参数：

broker 配置:
- message.max.bytes (默认:1000000) – broker能接收消息的最大字节数，这个值应该比消费端的fetch.message.max.bytes更小才对，否则broker就会因为消费端无法使用这个消息而挂起。
- log.segment.bytes (默认: 1GB) – kafka数据文件的大小，确保这个数值大于一个消息的长度。一般说来使用默认值即可（一般一个消息很难大于1G，因为这是一个消息系统，而不是文件系统）。
- replica.fetch.max.bytes (默认: 1MB) – broker可复制的消息的最大字节数。这个值应该比message.max.bytes大，否则broker会接收此消息，但无法将此消息复制出去，从而造成数据丢失。

Consumer 配置:
- fetch.message.max.bytes (默认 1MB) – 消费者能读取的最大消息。这个值应该大于或等于message.max.bytes。

所以，如果你一定要选择kafka来传送大的消息，还有些事项需要考虑。要传送大的消息，不是当出现问题之后再来考虑如何解决，而是在一开始设计的时候，就要考虑到大消息对集群和主题的影响。

性能: 根据前面提到的性能测试，kafka在消息为10K时吞吐量达到最大，更大的消息会降低吞吐量，在设计集群的容量时，尤其要考虑这点。
可用的内存和分区数：Brokers会为每个分区分配replica.fetch.max.bytes参数指定的内存空间，假设replica.fetch.max.bytes=1M，且有1000个分区，则需要差不多1G的内存，
确保 分区数*最大的消息不会超过服务器的内存，否则会报OOM错误。同样地，消费端的fetch.message.max.bytes指定了最大消息需要的内存空间，同样，分区数*最大需要内存空间 不能超过服务器的内存。
所以，如果你有大的消息要传送，则在内存一定的情况下，只能使用较少的分区数或者使用更大内存的服务器。

####4.解释Kafka的Zookeeper是什么?我们可以在没有Zookeeper的情况下使用Kafka吗?
不可能越过Zookeeper，直接联系Kafka broker。一旦Zookeeper停止工作，它就不能服务客户端请求。  
Zookeeper主要用于在集群中不同节点之间进行通信在Kafka中，它被用于提交偏移量，因此如果节点在任何情况下都失败了，  
它都可以从之前提交的偏移量中获取除此之外，它还执行其他活动，如: leader检测、分布式同步、配置管理、识别新节点何时离开或连接、集群、节点实时状态等等。


####5.解释如何提高远程用户的吞吐量?
如果用户位于与broker不同的数据中心，则可能需要调优套接口缓冲区大小，以对长网络延迟进行摊销。  


####6.解释一下，在数据制作过程中，你如何能从Kafka得到准确的信息?
在数据中，为了精确地获得Kafka的消息，你必须遵循两件事: 在数据消耗期间避免重复，在数据生产过程中避免重复。
这里有两种方法，可以在数据生成时准确地获得一个语义:
每个分区使用一个单独的写入器，每当你发现一个网络错误，检查该分区中的最后一条消息，以查看您的最后一次写入是否成功
在消息中包含一个主键(UUID或其他)，并在用户中进行反复制。  


####7.解释如何减少ISR中的扰动?broker什么时候离开ISR(kafka replica)?
ISR是一组与leaders完全同步的消息副本，也就是说ISR中包含了所有提交的消息。ISR应该总是包含所有的副本，直到出现真正的故障。  
如果一个副本从leader中脱离出来，将会从ISR中删除。  


####8.如果副本在ISR中停留了很长时间表明什么?
如果一个副本在ISR中保留了很长一段时间，那么它就表明，跟踪器无法像在leader收集数据那样快速地获取数据。  


####9.请说明如果首选的副本不在ISR中会发生什么?
如果首选的副本不在ISR中，控制器将无法将leadership转移到首选的副本。


####10.Data Replication如何Propagate(扩散出去)消息？
每个Partition有一个leader与多个follower，producer往某个Partition中写入数据是，只会往leader中写入数据，然后数据才会被复制进其他的Replica中。   

####11.数据是由leader push过去还是有flower pull过来？ 
kafka是由follower周期性或者尝试去pull(拉)过来(其实这个过程与consumer消费过程非常相似)，写是都往leader上写，  
但是读并不是任意flower上读都行，读也只在leader上读，flower只是数据的一个备份，保证leader被挂掉后顶上来，并不往外提供服务。  


####12.Data Replication何时Commit？
同步复制：只有所有的follower把数据拿过去后才commit，一致性好，可用性不高。  
异步复制：只要leader拿到数据立即commit，等follower慢慢去复制，可用性高，立即返回，一致性差一些。  
Commit：是指leader告诉客户端，这条数据写成功了。kafka尽量保证commit后立即leader挂掉，其他flower都有该条数据。 
kafka不是完全同步，也不是完全异步，是一种ISR机制：  
1.leader会维护一个与其基本保持同步的Replica列表，该列表称为ISR(in-sync Replica)，每个Partition都会有一个ISR，而且是由leader动态维护；   
2.如果一个flower比一个leader落后太多，或者超过一定时间未发起数据复制请求，则leader将其重ISR中移除；  
3.当ISR中所有Replica都向Leader发送ACK时，leader才commit。  

####13.既然所有Replica都向Leader发送ACK时，leader才commit，那么flower怎么会leader落后太多？ 
producer往kafka中发送数据，不仅可以一次发送一条数据，还可以发送message的数组；  
批量发送，同步的时候批量发送，异步的时候本身就是就是批量；底层会有队列缓存起来，批量发送，对应broker而言，就会收到很多数据(假设1000)，  
这时候leader发现自己有1000条数据，flower只有500条数据，落后了500条数据，就把它从ISR中移除出去，这时候发现其他的flower与他的差距都很小，就等待；  
如果因为内存等原因，差距很大，就把它从ISR中移除出去。  

commit策略 
server配置：    
rerplica.lag.time.max.ms=10000  #如果leader发现flower超过10秒没有向它发起fech请求，那么leader考虑这个flower是不是程序出了点问题，就把它从ISR中移除。
rerplica.lag.max.messages=4000 # 相差4000条就移除  

topic配置：  
min.insync.replicas=1 # 需要保证ISR中至少有多少个replica  

Producer配置：  
// 0:相当于异步的，不需要leader给予回复，producer立即返回，发送就是成功,Partition的Leader还没有commit消息,Leader与Follower数据不同步,既有可能丢失也可能会重发;
// 1：当leader接收到消息之后发送ack，丢会重发，丢的概率很小
//-1：当所有的follower都同步消息成功后发送ack.  丢失消息可能性比较低
request.required.asks=0   


####14.Data Replication如何处理Replica恢复?
leader挂掉了，从它的follower中选举一个作为leader，并把挂掉的leader从ISR中移除，继续处理数据。一段时间后该leader重新启动了，  
它知道它之前的数据到哪里了，尝试获取它挂掉后leader处理的数据，获取完成后它就加入了ISR。


