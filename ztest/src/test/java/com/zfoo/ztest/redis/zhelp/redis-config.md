###Redis相关配置

#####1.快照持久化和AOF持久化
- 快照持久化通过直接保存Redis的键值对，AOF持久化是通过保存Redis执行的写命令来记录Redis的内存数据
- RDB比AOF方式更健壮。官方文档也指出，AOF的确也存在一些BUG，这些BUG在RDB没有存在。

- 快照持久化配置
```
dbfilename dump.rdb         # 快照文件的名称
database 16                 #单机版数据库分片，默认16，不同分片可以有不同的key；集群只有一个分片
```

- AOF持久化
```
appendonly yes              # 打开aof设置，同时将快照功能置于低优先级的位置

appendfilename "appendonly.aof" # AOF文件名称
                            
appendfsync everysec        # always：每个 Redis 命令都要同步写入硬盘。这样会严重降低 Redis 的性能
                            # everysec：每秒执行一次同步，显式地将多个写命令同步到硬盘
                            # no：Redis不会主动调用fsync去将AOF日志内容同步到磁盘，所以这一切就完全依赖于操作系统的调试了。Linux是每30秒进行一次fsync，将缓冲区中的数据写到磁盘上
```

```
Redis打开AOF持久化功能后，Redis在执行完一个写命令后，都会将执行的写命令追回到Redis内部的缓冲区的末尾。这个过程是命令的追加过程。 
接下来，缓冲区的写命令会被写入到AOF文件，这一过程是文件写入过程。对于操作系统来说，调用write函数并不会立刻将数据写入到硬盘，
为了将数据真正写入硬盘，还需要调用fsync函数，调用fsync函数即是文件同步的过程。只有经过文件同步过程，AOF 文件才在硬盘中真正保存了Redis的写命令。
```


#####2.密码
- 单机密码设置
```
masterauth                  # master节点密码，当slave要拉去master的时候需要这个密码
requirepass                 # redis服务器密码
```

- 集群密码设置
```
masterauth 1234             # 修改所有redis集群中的redis.conf文件 
requirepass 1234            # 修改所有redis集群中的redis.conf文件 

    # 注意所有节点的密码都必须一致
    # 然后，通过指令找到安装的redis在ruby环境中的配置client.rb，加上密码
```
