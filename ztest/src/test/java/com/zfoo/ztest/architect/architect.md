architect  [ar·chi·tect || 'ɑːkɪtekt]  
n.建筑师; 缔造者, 创造者; 设计师  

###专业术语
####项目管理与监理有关 
1.Project Manager 项目经理   
2.Project Stakeholders 项目干系人   
3.general engineer 总监理工程师  
4.vice-general engineer  副监理工程师   
5.supervising engineer 监理工程师   
6.supervisory staff 监理人员 



###计算机软件、网络、数据库、系统等相关 
1.Senior System Architect  高级系统架构师   
2.Software Architect  软件架构师 
3.Network Architect 网络架构师   
4.System analyst 系统分析师  


1.senior software engineer 高级软件工程师    
2.software QA engineer(software quality assurance engineer) 软件测试工程师   
3.test engineer 测试工程师  
4.software engineer 软件工程师   
5.network engineer 网络工程师   
6.database engineer  数据库工程师 
7.information security engineer 信息安全工程师   
 
1.system administrator（SA）系统管理员   
2.network administrator 网络管理员   
3.database administrator（DBA）数据库管理员  

1.designer 设计师   
2.developer 开发人员（开发者） 
3.programmer 程序员 
4.data processing specialist 信息处理员 



####有别于社交网络、搜索和游戏等网站，电商网站的用户流量有哪些特点？  
杨超：个人觉得电商网站流量特点，突发性流量暴增，根本无法精确的预估的量。  
可能刚开始几万的量，突然几分钟就上到几十、几百、上千万、十倍百倍千倍的往上增。  
相比社交、搜索、游戏网站，差异最大点，就在直接牵涉精确的金额的问题。所以对于精准和延时，缓存有一些差异化的。  

社交网络：一般延时可做大点，及时性通讯可以端对端。  
搜索：一般多级缓存，大多计算好往前推，延时也可做大点，另外搜索本就模糊的匹配，精准性方面要求没那么严格。  
游戏网站：大多客户端大型游戏，客户端数据缓存几秒之后再进行传输，或者一些直接本地存数据，后端根服务器交互。  

###高流量、高并发情况下，如何保证整个系统的可靠性和稳定性???  
入口层：过滤掉大部分软件刷的情况，衍生了风控系统，秒杀系统。  
应用层: 读写分离、缓存、队列、令牌、系统拆分、隔离、系统升级（可水平扩容方向）。  
可靠性和稳定性：会做一堆的容灾方案，从机房、网络、应用、存储、渠道、业务等多维度容灾。做一堆的降级策略，从流量、应用、渠道、业务 等对多维度做。  
其他：  
时间换空间：降低单次请求时间，这样在单位时间内系统并发就会提升。  
空间换时间：拉长整体处理业务时间，换取后台系统容量空间  
![Image text](image/time-space.png)


###在线商城的架构图
![Image text](image/online-shop-architect.png)