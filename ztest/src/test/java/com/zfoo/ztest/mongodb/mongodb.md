##CAP定理（CAP theorem /ˈθɪərəm/）

C：一致性(Consistency)，所有节点在同一时间具有相同的数据  
A：可用性(Availability)，保证每个请求在一定时间内，不管成功或者失败都有响应  
P：分隔容忍(Partition tolerance)，系统中任意信息的丢失或失败不会影响系统的继续运作，一般指增加删除节点

CA：  
    CA要求在多个节点满足数据的严格一致性，且延迟较低。
    如果要满足P，则可以容忍数据丢包，因为数据在跨节点传输时有可能会丢包，如果丢包就无法满足一致性。
    所以CA一般单点集群，传统的数据库系统就是具有CA特征的数据库，所以在可扩展性上不强，水平扩展能力比较差。

CP：  
    满足一致性，分区容忍性的系统，因为P会导致系统的节点无线延长，也就无法在一定时间内保证可用性。
    通常性能不是特别高。

AP：   
    满足可用性，分区容忍性的系统，就要放弃强一致性，保证最终一致性。因为为了保证可用性，有时候节点就会用自己的本地数据，导致数据不一致性。


##mongodb

###安装配置
将C:\Program Files\MongoDB\Server\3.4\bin配置到环境变量中，便于全局使用。  
启动mongodb：  
mongod.exe --dbpath "E:\mongodb" --logpath "E:\mongolog.log" --logappend --install --serviceName "MongoDB"  
net start MongoDB  
net stop MongoDB



###创建数据库
该命令如果数据库不存在，将创建一个新的数据库， 否则将返回现有的数据库。
use school  

要检查当前选择的数据库使用命令
db

查询数据库列表
show dbs

###删除新的数据库
db.dropDatabase()

###创建集合
在MongoDB中并不需要创建集合。 当插入一些文档 MongoDB 会自动创建集合。
capped	Boolean	（可选）如果为true，它启用上限集合。上限集合是一个固定大小的集合，当它达到其最大尺寸会自动覆盖最老的条目。 如果指定true，则还需要指定参数的大小
size	number	（可选）指定的上限集合字节的最大尺寸。如果capped 是true，那么还需要指定这个字段
autoIndexID	Boolean	（可选）如果为true，自动创建索引_id字段。默认的值是 false
max	number	（可选）指定上限集合允许的最大文件数
db.createCollection("student", { capped : true, size : 6142800, autoIndexId : true, max : 10000 })
db.createCollection("student")

检查创建的集合
show collections


从数据库中删除集合
db.student.drop()


###插入文档
如果我们不指定_id参数插入的文档，那么 MongoDB 将为文档分配一个唯一的ObjectId。_id 是12个字节十六进制数在一个集合的每个文档是唯一的。
0-3时间戳，4-6机器唯一标识的散列标识，7-8进程号PID，9-11计数器。前三个保证同一个机器不同进程产生的标识唯一，后一个保证同一个进程产生不同的标识。
简介说就是在分布式系统中可以在本机产生全局唯一id。
db.student.insert(
{
   _id: 1,
   name: "sun1"
})


批量插入
db.student.insert([
{
   _id: 2,
   name: "sun2"
},
{
   _id: 3,
   name: "sun3"
}
])


###查询文档
find( query, fields, limit, skip)  
query 查询条件，相当于sql的where
fields 查询的结果  
limit 限制查询结果的数量  
skip 跳过多少个文档  

db.student.find()

查询and条件
db.student.find(
{
    "_id":3,
    "name":"sun2"
}).pretty()

文档排序，1用于升序，而-1是用于降序
db.student.find().sort({"name":-1})

查询or条件
db.student.find(
{
$or:
    [
        {"_id":3},
        {"name":"sun1"}
    ]
}).pretty()


###更新文档
db.student.update(
{
    "name":"sun"
},
{$set:
    {
        "name":"jie"
    }
})


覆盖更新save()
db.student.save(
{
   _id: 1,
   name: "sun"
})

###删除文档
db.student.remove(
{
    "name":"sun1"
})


###索引
db.student.ensureIndex({"name":1})

