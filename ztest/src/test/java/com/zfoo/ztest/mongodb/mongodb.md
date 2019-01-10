#mongodb安装

##Windows

###安装配置
1. 将C:\Program Files\MongoDB\Server\3.4\bin配置到环境变量中，便于全局使用。  
2. 启动mongodb：mongod.exe --dbpath "E:\mongodb" --logpath "E:\mongolog.log" --logappend --install --serviceName "MongoDB"  
3. net start MongoDB  
4. net stop MongoDB

###客户端命令行连接
- 运行cmd，输入mongo


###创建数据库
- use school，该命令如果数据库不存在，将创建一个新的数据库， 否则将返回现有的数据库。  

- db，要检查当前选择的数据库使用命令

- show dbs，查询数据库列表

###删除新的数据库
- db.dropDatabase()

###创建集合
- show collections，检查创建的集合

- db.student.drop()，从数据库中删除集合

```
在MongoDB中并不需要创建集合。 当插入一些文档 MongoDB 会自动创建集合。
capped	Boolean	（可选）如果为true，它启用上限集合。上限集合是一个固定大小的集合，当它达到其最大尺寸会自动覆盖最老的条目。 如果指定true，则还需要指定参数的大小
size	number	（可选）指定的上限集合字节的最大尺寸。如果capped 是true，那么还需要指定这个字段
autoIndexID	Boolean	（可选）如果为true，自动创建索引_id字段。默认的值是 false
max	number	（可选）指定上限集合允许的最大文件数
db.createCollection("student", { capped : true
, size : 6142800, autoIndexId : true, max : 10000 })
db.createCollection("student")
```

###插入文档
如果我们不指定_id参数插入的文档，那么 MongoDB 将为文档分配一个唯一的ObjectId。_id 是12个字节十六进制数在一个集合的每个文档是唯一的。
0-3时间戳，4-6机器唯一标识的散列标识，7-8进程号PID，9-11计数器。前三个保证同一个机器不同进程产生的标识唯一，后一个保证同一个进程产生不同的标识。
简介说就是在分布式系统中可以在本机产生全局唯一id。
db.student.insert(
{
   "_id": 1,
   "name": "sun1"
})


批量插入
db.student.insert([
{
   "_id": 1,
   "name": "sun1",
   "age": 10
},
{
   "_id": 2,
   "name": "sun2",
   "age": 20
},
{
   "_id": 3,
   "name": "sun3",
   "age": 30
},
{
   "_id": 4,
   "name": "sun4",
   "age": 40
},
{
   "_id": 5,
   "name": "sun5",
   "age": 50
},
{
   "_id": 6,
   "name": "sun6",
   "age": 60
},
{
   "_id": 7,
   "name": "sun7",
   "age": 70
},
{
   "_id": 8,
   "name": "sun8",
   "age": 80
},
{
   "_id": 9,
   "name": "sun9",
   "age": 90
},
{
   "_id": 10,
   "name": "sun10",
   "age": 100
}
])


###查询文档
find( query, fields)  
query 查询条件，相当于sql的where
fields 查询的结果，字段映射,0不显示，1显示

limit() 限制查询结果的数量  
skip() 设置第一个文档的偏移量  
sort() 排序，1升序，-1降序  
pretty() 格式化输出结果，使得查询出来的数据在命令行中更加美观的显示，不至于太紧凑  
explain("allPlansExecution")  返回查询计划信息和查询计划的执行统计信息   

db.student.find(  
{},  
{  
    "_id":0,
    "name":1
})  
.limit(9)  
.skip(5)  
.sort({"name":-1})  
.pretty()  

**$in条件**  
db.student.find(  
{ "name":{ $in:["sun1", "sun2"] } }  
).pretty()


**$and, $or条件**  
db.student.find(
{
$or:
    [
        {"_id":3},
        {"name":"sun1"}
    ]
}).pretty()

**$not取反，$exist存在这个值**    

**内嵌文档查询：**   
假如文档类型为下面这种结构，则下面两种类型都是等价的
db.student.insert(
{
   "_id": 1,
   "name": "sun1",
   "address":
   {
        "city":"ShangHai",
        "room":"hong mei lu 1701"
   }
})

db.student.find(  
{ 
    "address":
    {
        "city":"ShangHai" 
    }
})
 
db.student.find({ "address.city":"ShangHai" })

**数组文档查询：**  
假如文档类型为下面这种结构
db.student.insert(
{
   "_id": 1,
   "name": "sun1",
   "course":["math", "history", "cs", "pe"],
   "rank":[30, 50, 100]
})

查询数组中含有某一个值：
db.student.find(  
{ 
    "course":"math"
})

按照指定的数组索引查询数组元素的值：
db.student.find(  
{ 
    "course.1":"math"
})

查询数组中含有某一个值：
db.student.find(  
{ 
    "course":"math"
})

$all查询数组中含有全部值：
db.student.find(  
{ 
    "rank":
    {
        $all:[30, 50]
    }
})

$all查询数组中含有全部值：
db.student.find(  
{ 
    "rank":
    {
        $all:[30, 50]
    }
})

$elementMatch, $gt, $lt，至少有一个元素满足$elementMatch列出的所有条件
db.student.find(  
{ 
    "rank":
    {
        $elementMatch:{ $gt:60, $lt:100 }
    }
})

$size, 返回具有指定长度的数组
db.student.find(  
{ 
    "rank":{ $size:2 }
})

$slice, 对数组的索引做映射
db.student.find(  
{ 
    "rank":{ $slice:[1, 2] }
})


**使用游标查询：**   
var cursor = db.student.find({}, { "_id":0, "name":1});
while(cursor.hasNext()) {
    var obj = cursor.next();
    print(tojson(obj));
}



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
db.student.droplndex({"name":1})  


###管道模式查询  
db.student.aggregate([
{
  $match:{
    "age":{ $gt:60 }
  }
}
])  
