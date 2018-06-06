这个包所规定的协议具有通用的特性，但是也有一些值得关注的地方：
1.协议包的类必须实现com.zfoo.protocol.model.packet.IPacket接口
2.必须是简单的javabean，不能继承任何其它的类
3.包里必须有一个标识为：private static final transient short PROTOCOL_ID的"协议序列号"，这个协议号的值必须和IPacket接口返回的值一样
4.支持的类型包括基础类型：boolean，byte，short，int，long，float，double，char，String。
5.支持的类必须要在net标签里定义，类协议的id不能重复
6.支持数组
7.支持Collection，Map，必须指定泛型类，泛型类只能是基本数据类型和协议文件里指定的协议类型，不能嵌套List（考虑到协议的通用性，扩展性，复杂性）
8.协议个格式为head(4Bit)+protocolId(2Bit)+body，head表示后面的body的长度一个int字节的长度
  protocolId表示哪一个协议short表示，2个字节，body为内容。
  boolean，等于一个byte，false为0，true为1
  int，long，使用zigzag算法+varint压缩，长度不固定
  byte，short，float，double，char，直接读，长度固定
  String，List，Map为引用类型，第一位为一个boolean，true表示后面有内容，然后跟着一个int表示String的长度
                                      false表示后面没有内容
  其它协议和String类似同样为引用类型，第一位为一个boolean，true表示后面有内容，具体内容根据协议表示
9.不支持枚举类，考虑到很多客户端不支持枚举类，枚举类又不容易控制，所以放弃支持枚举类