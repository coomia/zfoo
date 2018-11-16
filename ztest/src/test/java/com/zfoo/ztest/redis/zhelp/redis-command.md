###一. Redis启动
- windows: redis-server.exe redis.windows.conf

```
redis-server --maxclients 100000    # 服务启动时设置客户端最大连接数为1000
```

###二. Redis客户端启动
- windows: redis-cli.exe -h 127.0.0.1 -p 6379

```
config get maxclients   # 获客户端最大连接数

quit            # 关闭当前客户端连接
shutdown save   # 关闭redis服务器
```


###三. 数据类型
- String
```
set myKey "myValue"
get myKey
del myKey           # 删除
incr myKey          # 对数值类型的key加一
incrBy myKey 10     # 将key所储存的值加上给定的增量值（increment） 
decr myKey
decrBy myKey 10

```

- Hash：是一个 string 类型的 field 和 value 的映射表，每个 hash 可以存储 232 -1 键值对（40多亿）。
```
hmset myHash field1 "Hello" field "World"  #双引号可带可不带
hget myHash field1
hget myHash field2 
hGetAll myHash
```

- List：是字符串列表，按照插入顺序排序。列表最多可存储 232 - 1 元素 (4294967295, 每个列表可存储40多亿)。
```
lpush myList redis1
lpush myList redis2
lpush myList redis3

lrange myList 0 10
lpop myList             # 移出并获取列表的第一个元素
```

- Set：是string类型的无序集合，集合中最大的成员数为 232 - 1(4294967295, 每个集合可存储40多亿个成员)。
```
sadd mySet redis1
sadd mySet redis2
sadd mySet redis3

smembers mySet
srem mySet redis1       # 移除
```

- SortedSet：是string类型元素的有序集合，每个元素都会关联一个double类型的分数，通过分数进行从小到大的排序。
```
zadd mySortedSet 3 redis3
zadd mySortedSet 1 redis1
zadd mySortedSet 2 redis2

zRangeByScore mySortedSet 0 1000
zrem mySortedSet redis1
```

###四.发布订阅
```
subscribe redisChannel  # 创建了订阅频道名为 redisChat:

publish redisChannel "Redis is a great caching technique"  
                        # 然后在同一个频道redisChat发布消息，订阅者就能接收到消息。
```

###五.其它
- 批量命令，并非事务
```
multi           # 单个执行
set myKey1 aaa
set myKey2 bbb
exec            # 批量执行脚本，但批量指令并非原子化的操作，中间某条指令的失败不会导致前面已做指令的回滚，也不会造成后续的指令不做。
```

- 事务
```
watch myKey1 myKey2
multi
set myKey1 aaa
set myKey2 bbb
exec            # 如果执行命令的期间myKey1，myKey2任意一个改变，将全部回滚，一个都不执行；但是中间有语法错误或者运行错误不会回滚
unwatch
```

- 查看服务是否运行
```
ping
```

- 切换到指定的数据库，默认16个
```
select 1
```

- 获取redis服务器的信息
```
info
```

- 保存数据到硬盘
```
bgsave      # 后台异步保存
save        # 同步保存
```

- 同步master节点的数据到slave节点
```
sync
```

- 恢复数据，只需将备份文件(dump.rdb)移动到redis安装目录并启动服务即可
```
config get dir      # 获取redis目录可以使用CONFIG命令
```

- 安全相关
```
config get requirepass          # 查看是否设置了密码验证
config set requirepass admin    # 设置密码
auth admin                      # 用密码登录
```

```
keys *      # 查找所有的key
keys t??    # 三个字符以t开头
keys *o*    # 中间含有o的字符，four，two，one

flushall    # 清除所有库所有key数据
flushdb     # 清除单个库所有key数据
```
