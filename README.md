# U8Server
###U8Server——U8SDK服务器端（统一渠道SDK接入用户中心和支付中心）

#####U8SDK官方网站：http://www.u8sdk.com
#####U8SDK官方博客：http://www.uustory.com

U8SDK是一套类似棱镜SDK，AnySDK的统一渠道SDK接入框架，包含客户端部分和服务器端部分。和他们本质的区别是，U8SDK整套框架是技术开源的，我们所有的实现原理，都分享在博客上，为广大手游开发者，收集并提供比较完整的SDK接入方案。

同时，正式因为此，部分拒绝AnySDK，棱镜SDK等第三方接入平台的渠道，也不存在拒绝U8SDK的情况。因为，U8SDK所有的东西都是原码，每个手游公司拿去了U8SDK，和公司自己研发没有任何区别。

所以，只有一个U8SDK，但是每个手游公司，拿去就是各自公司自己的统一SDK接入解决方案了，你可能叫他ASDK,BSDK...等等。

所以，我们保持U8SDK的精简，只包含基础和必要的功能，任何附加的东西，我们都留给各个手游公司自己去丰富和完善。你可以把U8SDK整合进你们的集成方案中，比如你们用Jenkins，那么可以很方便地就将打包的环节，集成到你们的集成流程中。

所有的用户数据和支付数据，都走公司自己的服务器，U8Server目前也已开源，所有手游公司，都能基于U8Server搭建自己的统一用户登录认证中心和支付中心。所有的数据走向和存储，都只有手游公司自己知道，不需要经过第三方做中转。这也是和棱镜SDK他们的本质区别。

#####U8SDK目前包含Android和iOS两个平台的渠道SDK接入方案。

#####U8SDK客户端部分：

1. U8SDK抽象层工程(Android,iOS)
2. 各个渠道SDK接入工程(Android, iOS)
3. 一键打包工具（python实现, Android的打包工具和iOS的打包工具都是python实现）
4. U8SDK接入Demo（原生平台，Unity3D，Cocos2dx）

#####U8SDK服务器端部分(不分Adroid和iOS)：

U8Server：统一的用户登录认证中心和支付中心，目前开源。基于标准的J2EE框架(Struts2+Spring+Hibernate)研发。关于U8Server使用的基础框架，之前就开源了，关于U8Server中注解的使用，工程目录结构等可以看看[U8Framework4SSH](https://github.com/u8-xiaohei/U8Framework4SSH)这里的说明文件。

###U8Server地址说明

U8Server中，URL可以分为三个类型，分别以不同的前缀开头
/user:	用户登录认证相关的地址
/pay:	支付获取订单号，以及支付回调相关的地址
/admin: U8Server后台管理系统的地址

比如你将U8Server部署在www.u8sdk.com域名下，那么，我们对这几种URL进行一个说明：

http://www.u8sdk.com/user/getToken : 渠道SDK登录认证协议地址（对应的类是com.u8.server.web.UserAction）
http://www.u8sdk.com/user/verifyAccount : 游戏服务器来U8Server做二次认证的协议地址（对应的类是com.u8.server.web.UserAction）


http://www.u8sdk.com/pay/getOrderID : 客户端支付之前，先来U8Server获取一个订单号的协议地址(对应的类是com.u8.server.web.PayAction)

http://www.u8sdk.com/pay/uc/payCallback/10 : UC渠道的支付回调通知地址（com.u8.server.web.UCPayCallbackAction）
http://www.u8sdk.com/pay/baidu/payCallback/15 ：百度渠道的支付回调通知地址（com.u8.server.web.BaiduPayCallbackAction）
http://www.u8sdk.com/pay/baidu/payCallback/15 : 小米渠道的支付回调通知地址（com.u8.server.web.XiaoMiPayCallbackAction）
其他渠道类似

关于支付回调地址的说明：
目前支付回调地址的规则必须是 【http://www.u8sdk.com/pay/渠道名称/payCallback/渠道号】，每个渠道SDK的回调逻辑单独一个类，在com.u8.server.web路径下。

http://www.u8sdk.com/admin/login ：后台登录界面（com.u8.server.web.admin.AdminIndexAction）
后台管理页面相关入口类都在com.u8.server.web.admin包名下
后台管理页面(jsp)都在WEB-INF/admin/路径下

关于U8SDK和U8Server的设计和文档等，请参考[U8SDK的官方博客](http://www.uustory.com)

后面所有关于U8Server详细的文档，我们都会及时更新在博客上，请及时关注

