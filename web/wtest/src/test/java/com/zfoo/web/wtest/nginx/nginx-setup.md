##windows下nginx安装，配置，使用

####安装
- 直接解压安装包

####启动
- nginx.exe(即nginx -c conf\nginx.conf)，默认使用80端口，日志见文件夹C:\nginx\logs

####关闭
- nginx -s stop

##配置

####基本配置
location / {
    root   /usr/local/river;
    index  index.html index.htm;
}
error_page  404              /index.html;

####反向代理
```
upstream tomcatserver1 {
    server 192.168.25.141:8080;
}
upstream tomcatserver2 {
    server 192.168.25.141:8081;
}

server {
    listen       80;
    server_name  8080.kongnull.com;
    location / {
        proxy_pass   http://tomcatserver1;
        index  index.html index.htm;
    }
}
server {
    listen       80;
    server_name  8081.kongnull.com;
    location / {
        proxy_pass   http://tomcatserver2;
        index  index.html index.htm;
    }
}
```

####负载均衡
```
#定义负载均衡设备的 Ip及设备状态 
upstream myServer {   
    server 127.0.0.1:9090 down; 
    server 127.0.0.1:8080 weight=2; 
    server 127.0.0.1:6060; 
    server 127.0.0.1:7070 backup; 
}

upstream 每个设备的状态:
down        # 表示单前的server暂时不参与负载 
weight      # 默认为1.weight越大，负载的权重就越大。 
max_fails   # 允许请求失败的次数默认为1.当超过最大次数时，返回proxy_next_upstream 模块定义的错误 
backup      # 其它所有的非backup机器down或者忙的时候，请求backup机器。所以这台机器压力会最轻。
```


####nginx的高可用
nginx + keepalived
