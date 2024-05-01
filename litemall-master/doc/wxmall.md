# 3 litemall小商城

技术：

* 小商城前端，即litemall-wx模块和renard-wx模块
  * 微信小程序
* 小商城后端，即litemall-wx-api模块
  * Spring Boot 2.x
  * Spring MVC
  * [weixin-java-tools](https://gitee.com/binary/weixin-java-tools)


目前发现存在的一些问题：
  
* `缺失`后台服务返回的token存在有效期，小商场应该自动刷新
* `缺失`账号多次登录失败，应该小商城出现图片验证码限制，或者后台账号锁定
* `改善`商品搜索中采用更好的搜索机制
* `改善`商品搜索中，支持热门搜索"换一批"
* `改善`地址优化，目前每一次点击都会请求后台，应该缓存已有的数据
* `改善`商品数量和规格中，如果货品数量不足，则显示效果，通常是是两种效果
  * 某个规格选择以后，另外规格的某些规格是实线，而某些是虚线，
  * 商品的都规格选择以后，“立即购买”和“加入购物车显示”灰色
* `改善`商品好评计算与显示，例如90%好评
* `改善`商品的评论列表中显示评价的评论分数、商品规格
* `改善`商品的评论列表中的图片点击可放大，同时用户评价的多个图片可以选择左右滑动查看。
* `改善`商品的订单中支持订单号搜索功能
* `改善`在一些内容比较多的页面中支持“顶部”功能

## 3.0 小商场环境

按照项目README文档中的“快速启动”一节，开发者可以快速启动小商场项目。
但是小程序端只可以显示数据和图片，而微信登录会失败、微信支付也会失败，
因为appid不是开发者自己的，

这里进一步介绍开发者需要设置的小商场环境。

### 3.0.1 微信登录配置

开发者在微信小程序官网申请以后，可以有app-id和app-secret信息。

1. 在litemall-core模块的src/main/resources的application-core.yml资源文件中设置
    ```
    litemall
        wx
            app-id: 开发者申请的app-id
            app-secret: 开发者申请的app-secret
    ```

2. 在litemall-wx模块的project.config.json文件中设置

    ```
    "appid": "开发者申请的app-id",
    ```

3. 启动后台服务

4. 建议开发者关闭当前项目或者直接关闭微信开发者工具，重新打开（因为此时litemall-wx模块的appid可能未更新）。
   编译运行，尝试微信登录

### 3.0.2 微信支付配置

开发者在微信商户平台申请以后，可以有app-id和app-secret信息。

1. 在litemall-core-api模块的src/main/resources的application-core.yml资源文件中设置

    ```
    litemall
        wx
            mch-id: 开发者申请的mch-id
            mch-key: 开发者申请的mch-key
            notify-url: 开发者部署服务的微信支付成功回调地址
    ```

    注意
    > * notify-url是微信商户平台向小商场后台服务发送支付结果的地址。
    >    因此这就要求该地址是可访问的。
    > * 目前小商场后台服务的默认request mapping是`/wx/order/pay-notify`（见WxOrderController类的payNotify）,
    >    因此notify-url应该设置的地址类似于`http://www.example.com/wx/order/pay-notify`
    > * 当开发者真正上线后台服务时，强烈建议默认request mapping要重新命名，不能对外公开。

2. 启动后台服务

3. 部署后台服务到云服务器

4. litemall-wx的api.js设置云服务器的域名。
   编译运行，尝试微信支付。
   
### 3.0.3 微信退款配置

目前管理平台的退款功能需要进行微信商户退款配置

1. 从微信商户平台下载商户证书（或者叫做API证书），保存到合适位置，
   请阅读[文档](https://pay.weixin.qq.com/wiki/doc/api/wxa/wxa_api.php?chapter=4_3)
2. 在litemall-core-api模块的src/main/resources的application-core.yml资源文件中设置

    ```
    litemall
        wx
            key-path: 证书文件访问路径
    ```
3. 启动小程序前端和后端，进行下单、支付、申请退款操作

4. 启动管理后台前端和后端，进行订单退款操作，然后验证手机是否收到退款。

注意：
> 虽然这里管理后台退款接入了微信退款API，但是从安全角度考虑，**强烈建议**
> 开发者删除管理后台微信退款代码，然后分成两个步骤实现管理员退款操作：
> * 首先，管理员登录微信平台进行退款操作；
> * 然后，管理员登陆管理后台点击退款按钮，进行订单退款状态变更和商品库存回库。

## 3.1 litemall-wx-api

本节介绍小商场的后台服务模块。

### 3.1.1 授权服务

见WxAuthController类。

### 3.1.2 首页服务

见WxHomeController类。

### 3.1.3 类目服务

见WxCatelogController类。

### 3.1.4 商品服务

见WxGoodsController类。

### 3.1.5 品牌服务

见WxBrandController类。

### 3.1.6 专题服务

见WxTopicController类。

### 3.1.7 搜索服务

见WxSearchController类。

### 3.1.8 购物车服务

见WxCartController类。

### 3.1.9 订单服务

见WxOrderController类。

### 3.1.10 评价服务

见WxCommentController类。

注意：
> 订单商品评价功能见WxOrderController类的comment方法。

### 3.1.11 团购服务

见WxGrouponController类。

### 3.1.12 收藏服务

见WxCollectController类。

### 3.1.13 足迹服务

见WxFootprintController类。

### 3.1.14 收货地址服务

见WxAddressController类。

### 3.1.15 区域服务

见WxRegionController类。

### 3.1.16 安全

#### 3.1.16.1 Token

用户登录成功以后，后端会返回`token`，之后用户请求都会携带token。

见WxWebMvcConfiguration类、LoginUser和LoginUserHandlerMethodArgumentResolver类。

小商城后端服务每一次请求都会检测是否存在HTTP头部域`X-Litemall-Token`。
如果存在，则内部查询转换成LoginUser，然后作为请求参数。
如果不存在，则作为null请求参数。

而具体的后端服务controller中，则可以利用LoginUser来检查。

例如用户地址服务中：
```
@RestController
@RequestMapping("/wx/address")
@Validated
public class WxAddressController {
    @GetMapping("list")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        
        ...
    }
```
如果检测`userId`是null，则返回错误信息“用户未登录”。

## 3.2 litemall-wx

这里的代码基于[nideshop-mini-program](https://gitee.com/tumobi/nideshop-mini-program)，但是做了一定的修改：

* 数据属性名称调整，原项目中数据属性名称是下划线法命名（例如goods_id），而这里采用骆驼式命名法（例如goodsId），因此代码中需要进行相应调整；
* 代码清理重构，删除了一些目前不必要的文件，梳理一些逻辑功能；
* BUG修补，修改了一些错误；
* 功能完善拓展，例如商品立即购买功能、商品评价功能；

具体变化可以采用工具进行对比。

注意
> litemall-wx模块代码基于nideshop-mini-program的commit版本[acbf6276eb27abc6a48887cddd223d7261f0088e](https://github.com/tumobi/nideshop-mini-program/commit/acbf6276eb27abc6a48887cddd223d7261f0088e)。
> 由于改动变化较大，因此之后litemall-wx将独立开发，不会合并nideshop-mini-program的更新。

### 3.2.1 业务API设置

业务API存放在`config/api.js`。

但是可以发现这样的代码：

```
// 本机开发时使用
var WxApiRoot = 'http://localhost:8082/wx/';
// 局域网测试使用
// var WxApiRoot = 'http://192.168.0.101:8082/wx/';
// 云平台部署时使用
//  var WxApiRoot = 'http://122.51.199.160:8082/wx/';
```

也就是说这里存在三种类型的API服务地址，这里是考虑到开发存在三种情况：

1. 本机开发时，localhost是当前开发机的地址；
2. 手机预览时，192.168.0.101是开发机的IP地址；
3. 当后台部署在云服务器中时，122.51.199.160是云服务器的IP地址；
4. 此外，更最重要的是，如果小程序正式部署时，这里的地址必须是域名，
而不能是IP地址。

因此，开发阶段开发者可以按照具体情况切换1，2或3的选项。

### 3.2.2 页面

* 首页
* 专题页
* 专题详情页
* 专题评论页
* 专题评论添加页
* 品牌页
* 品牌详情页
* 人气推荐页
* 新品首发页
* 分类页
* 分类详情页
* 查找页
* 商品详情页
* 商品评论页
* 购物车页
* 下单页
* 下单地址页
* 下单地址添加页
* 支付结果页
* 个人页
* 订单列表页
* 订单详情页
* 优惠券页
* 收藏页
* 足迹页
* 收货地址页
* 收货地址添加页
* 登录页
* 注册页
* 找回密码页

### 3.2.3 登录设计

按照官方文档，开发者采用`wx.login`方法即可实现登录操作；
然而，由于`wx.login`只能返回临时登录凭证`code`，从服务器也只能返回对应的sessionId,
因此虽然已经可以视作登录，但是在小程序中不能显示有意义的登录状态，
因此实际很多小程序是继续采用`wx.getUserInfo`来进一步请求用户信息。

因此本模块中，用户的登录状态也是由`wx.login`和`wx.getUserInfo`组成。

#### 3.2.3.1 登录检测

开发者可以采用`user.checkLogin`来检查是否`已登录`，而其检测逻辑是：

1. 可以从storage获取`userInfo`和`token`
2. 同时`wx.checkSession`也成功。

但是如果每次都使用`checkLogin`可能也不太好，因此目前机制是：

1. 应用启动时检测一次，如果登录则设置app.globalData.hasLogin为已登录状态;
之后，其他页面只要查看这个状态即可知道目前是否已登录；
2. 如果后端token过期返回501错误码时，则前端清理`userInfo`和`token`；
3. 用户执行退出操作，则清理`userInfo`和`token`，同时设置hasLogin未登录状态。

注意：
> 这里的逻辑可能有点乱。。。，但是目前实际效果看没有问题。

#### 3.2.3.2 登录时机

登录请求用户信息的时机存在两种设计：

1. 一种是小程序加载时，即申请用户信息，这种实现较简单，但是用户体验可能不是很好；
2. 另外一种是小程序加载时不需要，但是小程序用户需要真正用户信息时才请求用户登录，
而这种实现较复杂。

目前采用第二种方式实现，这里又可以进一步分成两种情况：

* 用户主动登录

  用户主动登录，指的是`个人`页面中用户没有登录显示`点击登录`的效果。

* 用户被动登录

  用户被动登录，指的是用户想购买商品或者需要用户登录才能操作的行为，
  此时因为向服务器请求时token没有设置，因此服务器拒绝用户的请求，同时返回`501`业务代码。
 
 以上无论哪种情况，都会导致用户被重定向到`登录`页面来进行登录操作。
 
#### 3.2.3.3 登录操作

如前面讨论，这里的登录操作实际包含两个操作`wx.login`和`wx.getUserInfo`。
开发者可以采用`user.loginByWeixin`来进行登录操作。

按照小程序官网文档，用户登录前应该检测以下，来避免频繁无意义的登录操作，
因此较合适的做法如下所示:

```
    user.checkLogin().catch(() => {

      user.loginByWeixin().then(res => {
        this.setData({
          userInfo: res.data.userInfo,
        });
      }).catch((err) => {
        util.showErrorToast('登录失败');
      });

    });
```

#### 3.2.3.4 登出操作

在`个人`页面，如果用户已经登录，则会出现`退出登录`按钮，支持用户退出当前登录状态。

退出逻辑如下所示：
```
        util.request(api.AuthLogout, {}, 'POST');
        app.globalData.hasLogin = false;
        wx.removeStorageSync('token');
        wx.removeStorageSync('userInfo');
        wx.reLaunch({
          url: '/pages/index/index'
        });
```

### 3.2.4 storage

litemall-wx模块采用storage来存储一些数据，以及支持组件间数据通信。

#### 3.2.4.1 userInfo和token

#### 3.2.4.2 cartId

#### 3.2.4.3 addressId

### 3.2.5 加入购物车和立即购买

### 3.2.6 团购

## 3.3 renard-wx

renard-wx是另外一个小程序前端，其后端API也是litemall-wx-api。

和litemall-wx的区别是：
1. 界面样式有所调整；
2. 功能进一步简化。

## 3.4 开发新组件

本章节介绍如何开发新的微信小程序功能。

### 3.4.1 小商场前端页面

### 3.4.2 前后端交互服务API

### 3.4.3 小商场后端服务

### 3.4.4 数据库
Microsoft Windows [版本 10.0.17763.5458]
(c) 2018 Microsoft Corporation。保留所有权利。

C:\Users\Administrator>java -Dfile.encoding=UTF-8 -jar C:\jiaoben\litemall-all-0.1.0-exec.jar
20:40:10,409 |-INFO in ch.qos.logback.core.joran.spi.ConfigurationWatchList@59d016c9 - URL [jar:file:/C:/jiaoben/litemall-all-0.1.0-exec.jar!/BOOT-INF/classes!/logback-spring.xml] is not of type file
20:40:10,528 |-INFO in ch.qos.logback.classic.joran.action.ContextNameAction - Setting logger context name as [logback]
20:40:10,530 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.ConsoleAppender]
20:40:10,531 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [console]
20:40:10,543 |-INFO in ch.qos.logback.core.joran.action.NestedComplexPropertyIA - Assuming default type [ch.qos.logback.classic.encoder.PatternLayoutEncoder] for [encoder] property
20:40:10,572 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.rolling.RollingFileAppender]
20:40:10,577 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [file]
20:40:10,588 |-INFO in c.q.l.core.rolling.TimeBasedRollingPolicy@411506101 - No compression will be used
20:40:10,590 |-INFO in c.q.l.core.rolling.TimeBasedRollingPolicy@411506101 - Will use the pattern logs/log-%d{yyyy-MM-dd}.log for the active file
20:40:10,594 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - The date pattern is 'yyyy-MM-dd' from file name pattern 'logs/log-%d{yyyy-MM-dd}.log'.
20:40:10,594 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Roll-over at midnight.
20:40:10,604 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Setting initial period to Fri Apr 19 22:59:09 CST 2024
20:40:10,607 |-INFO in ch.qos.logback.core.joran.action.NestedComplexPropertyIA - Assuming default type [ch.qos.logback.classic.encoder.PatternLayoutEncoder] for [encoder] property
20:40:10,610 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[file] - Active log file name: logs/log.log
20:40:10,610 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[file] - File property is set to [logs/log.log]
20:40:10,611 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - About to instantiate appender of type [ch.qos.logback.core.rolling.RollingFileAppender]
20:40:10,611 |-INFO in ch.qos.logback.core.joran.action.AppenderAction - Naming appender as [error]
20:40:10,612 |-INFO in c.q.l.core.rolling.TimeBasedRollingPolicy@496729294 - No compression will be used
20:40:10,614 |-INFO in c.q.l.core.rolling.TimeBasedRollingPolicy@496729294 - Will use the pattern logs/error-%d{yyyy-MM-dd}.log for the active file
20:40:10,615 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - The date pattern is 'yyyy-MM-dd' from file name pattern 'logs/error-%d{yyyy-MM-dd}.log'.
20:40:10,615 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Roll-over at midnight.
20:40:10,616 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Setting initial period to Fri Apr 19 22:59:09 CST 2024
20:40:10,616 |-INFO in ch.qos.logback.core.joran.action.NestedComplexPropertyIA - Assuming default type [ch.qos.logback.classic.encoder.PatternLayoutEncoder] for [encoder] property
20:40:10,620 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[error] - Active log file name: logs/error.log
20:40:10,621 |-INFO in ch.qos.logback.core.rolling.RollingFileAppender[error] - File property is set to [logs/error.log]
20:40:10,621 |-INFO in ch.qos.logback.classic.joran.action.RootLoggerAction - Setting level of ROOT logger to ERROR
20:40:10,621 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating ERROR level on Logger[ROOT] onto the JUL framework
20:40:10,623 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [console] to Logger[ROOT]
20:40:10,623 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [file] to Logger[ROOT]
20:40:10,623 |-INFO in ch.qos.logback.core.joran.action.AppenderRefAction - Attaching appender named [error] to Logger[ROOT]
20:40:10,624 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.mybatis] to ERROR
20:40:10,624 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating ERROR level on Logger[org.mybatis] onto the JUL framework
20:40:10,624 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.springframework] to ERROR
20:40:10,625 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating ERROR level on Logger[org.springframework] onto the JUL framework
20:40:10,625 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.linlinjava.litemall.core] to ERROR
20:40:10,626 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating ERROR level on Logger[org.linlinjava.litemall.core] onto the JUL framework
20:40:10,626 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.linlinjava.litemall.db] to ERROR
20:40:10,626 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating ERROR level on Logger[org.linlinjava.litemall.db] onto the JUL framework
20:40:10,627 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.linlinjava.litemall.admin] to DEBUG
20:40:10,628 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating DEBUG level on Logger[org.linlinjava.litemall.admin] onto the JUL framework
20:40:10,630 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.linlinjava.litemall.wx] to DEBUG
20:40:10,631 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating DEBUG level on Logger[org.linlinjava.litemall.wx] onto the JUL framework
20:40:10,631 |-INFO in ch.qos.logback.classic.joran.action.LoggerAction - Setting level of logger [org.linlinjava.litemall] to DEBUG
20:40:10,632 |-INFO in ch.qos.logback.classic.jul.LevelChangePropagator@5fdcaa40 - Propagating DEBUG level on Logger[org.linlinjava.litemall] onto the JUL framework
20:40:10,632 |-INFO in ch.qos.logback.classic.joran.action.ConfigurationAction - End of configuration.
20:40:10,632 |-INFO in org.springframework.boot.logging.logback.SpringBootJoranConfigurator@74e52303 - Registering current configuration as safe fallback point

.   ____          _            __ _ _
/\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
\\/  ___)| |_)| | | | | || (_| |  ) ) ) )
'  |____| .__|_| |_|_| |_\__, | / / / /
=========|_|==============|___/=/_/_/_/
:: Spring Boot ::        (v2.1.5.RELEASE)

20:40:10.817 logback [main] INFO  org.linlinjava.litemall.Application - Starting Application v0.1.0 on WIN-4NDIACRELBT with PID 12064 (C:\jiaoben\litemall-all-0.1.0-exec.jar started by Administrator in C:\Users\Administrator)
20:40:10,821 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Elapsed period: Fri Apr 19 22:59:09 CST 2024
20:40:10,822 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [logs\log.log] to [logs\log-2024-04-19.log]
20:40:10.825 logback [main] DEBUG org.linlinjava.litemall.Application - Running with Spring Boot v2.1.5.RELEASE, Spring v5.1.7.RELEASE
20:40:10.826 logback [main] INFO  org.linlinjava.litemall.Application - The following profiles are active: db,core,admin,wx
sss
[]
20:40:17.890 logback [scheduling-1] INFO  o.l.litemall.admin.job.CouponJob - 绯荤粺寮€鍚换鍔℃鏌ヤ紭鎯犲埜鏄惁宸 茬粡杩囨湡
20:40:17.919 logback [main] INFO  org.linlinjava.litemall.Application - Started Application in 7.989 seconds (JVM running for 8.559)
20:40:17.929 logback [scheduling-1] INFO  o.l.litemall.admin.job.CouponJob - 绯荤粺缁撴潫浠诲姟妫€鏌ヤ紭鎯犲埜鏄惁宸 茬粡杩囨湡
sss
[]
sss
[]
sss
[]
sss
[]
sss
[]
sss
[]
20:47:02.000 logback [http-nio-8080-exec-10] ERROR c.a.d.pool.DruidAbstractDataSource - discard long time none received connection. , jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, lastPacketReceivedIdleMillis : 392165
20:47:02.000 logback [http-nio-8080-exec-1] ERROR c.a.d.pool.DruidAbstractDataSource - discard long time none received connection. , jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, lastPacketReceivedIdleMillis : 392165
20:47:02,001 |-INFO in c.q.l.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy - Elapsed period: Fri Apr 19 22:59:09 CST 2024
20:47:02.000 logback [http-nio-8080-exec-8] ERROR c.a.d.pool.DruidAbstractDataSource - discard long time none received connection. , jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, lastPacketReceivedIdleMillis : 392165
20:47:02,005 |-INFO in c.q.l.co.rolling.helper.RenameUtil - Renaming file [logs\error.log] to [logs\error-2024-04-19.log]
20:47:02.011 logback [http-nio-8080-exec-1] ERROR c.a.d.pool.DruidAbstractDataSource - discard long time none received connection. , jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, lastPacketReceivedIdleMillis : 407821
20:47:02.015 logback [http-nio-8080-exec-1] ERROR c.a.d.pool.DruidAbstractDataSource - discard long time none received connection. , jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, jdbcUrl : jdbc:mysql://localhost:3306/litemall?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true&verifyServerCertificate=false&useSSL=false, lastPacketReceivedIdleMillis : 407828
20:47:06.060 logback [http-nio-8080-exec-5] ERROR o.l.l.c.c.GlobalExceptionHandler - Invalid bound statement (not found): org.linlinjava.litemall.db.dao.LitemallGoodsDyPriceMapper.deleteByExample
org.apache.ibatis.binding.BindingException: Invalid bound statement (not found): org.linlinjava.litemall.db.dao.LitemallGoodsDyPriceMapper.deleteByExample
at org.apache.ibatis.binding.MapperMethod$SqlCommand.<init>(MapperMethod.java:227)
at org.apache.ibatis.binding.MapperMethod.<init>(MapperMethod.java:49)
at org.apache.ibatis.binding.MapperProxy.cachedMapperMethod(MapperProxy.java:65)
at org.apache.ibatis.binding.MapperProxy.invoke(MapperProxy.java:58)
at jdk.proxy2/jdk.proxy2.$Proxy85.deleteByExample(Unknown Source)
at org.linlinjava.litemall.db.service.LitemallGoodsDyService.deleteByGid(LitemallGoodsDyService.java:42)
at org.linlinjava.litemall.admin.service.AdminGoodsService.delete(AdminGoodsService.java:255)
at org.linlinjava.litemall.admin.service.AdminGoodsService$$FastClassBySpringCGLIB$$5c76aa2c.invoke(<generated>)
at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:295)
at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:98)
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)
at org.linlinjava.litemall.admin.service.AdminGoodsService$$EnhancerBySpringCGLIB$$e278cac9.delete(<generated>)
at org.linlinjava.litemall.admin.service.AdminGoodsService$$FastClassBySpringCGLIB$$5c76aa2c.invoke(<generated>)
at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
at org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:295)
at org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:98)
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)
at org.linlinjava.litemall.admin.service.AdminGoodsService$$EnhancerBySpringCGLIB$$8ba00faf.delete(<generated>)
at org.linlinjava.litemall.admin.web.AdminGoodsController.delete(AdminGoodsController.java:79)
at org.linlinjava.litemall.admin.web.AdminGoodsController$$FastClassBySpringCGLIB$$e9ecea06.invoke(<generated>)
at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)
at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:749)
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)
at org.springframework.validation.beanvalidation.MethodValidationInterceptor.invoke(MethodValidationInterceptor.java:119)
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
at org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor$1.proceed(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:82)
at org.apache.shiro.authz.aop.AuthorizingMethodInterceptor.invoke(AuthorizingMethodInterceptor.java:39)
at org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor.invoke(AopAllianceAnnotationsAuthorizingMethodInterceptor.java:115)
at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)
at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:688)
at org.linlinjava.litemall.admin.web.AdminGoodsController$$EnhancerBySpringCGLIB$$9a2be098.delete(<generated>)
at java.base/jdk.internal.reflect.DirectMethodHandleAccessor.invoke(DirectMethodHandleAccessor.java:103)
at java.base/java.lang.reflect.Method.invoke(Method.java:580)
at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)
at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)
at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:104)
at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:892)
at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:797)
at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)
at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1039)
at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:942)
at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1005)
at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:908)
at javax.servlet.http.HttpServlet.service(HttpServlet.java:660)
at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:882)
at javax.servlet.http.HttpServlet.service(HttpServlet.java:741)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:61)
at org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)
at org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)
at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)
at org.apache.shiro.web.servlet.AdviceFilter.executeChain(AdviceFilter.java:108)
at org.apache.shiro.web.servlet.AdviceFilter.doFilterInternal(AdviceFilter.java:137)
at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
at org.apache.shiro.web.servlet.ProxiedFilterChain.doFilter(ProxiedFilterChain.java:66)
at org.apache.shiro.web.servlet.AbstractShiroFilter.executeChain(AbstractShiroFilter.java:450)
at org.apache.shiro.web.servlet.AbstractShiroFilter$1.call(AbstractShiroFilter.java:365)
at org.apache.shiro.subject.support.SubjectCallable.doCall(SubjectCallable.java:90)
at org.apache.shiro.subject.support.SubjectCallable.call(SubjectCallable.java:83)
at org.apache.shiro.subject.support.DelegatingSubject.execute(DelegatingSubject.java:387)
at org.apache.shiro.web.servlet.AbstractShiroFilter.doFilterInternal(AbstractShiroFilter.java:362)
at org.apache.shiro.web.servlet.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:125)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at com.github.xiaoymin.swaggerbootstrapui.filter.SecurityBasicAuthFilter.doFilter(SecurityBasicAuthFilter.java:84)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at com.github.xiaoymin.swaggerbootstrapui.filter.ProductionSecurityFilter.doFilter(ProductionSecurityFilter.java:53)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.springframework.web.filter.CorsFilter.doFilterInternal(CorsFilter.java:96)
at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at com.alibaba.druid.support.http.WebStatFilter.doFilter(WebStatFilter.java:124)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:99)
at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:92)
at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.springframework.web.filter.HiddenHttpMethodFilter.doFilterInternal(HiddenHttpMethodFilter.java:93)
at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:200)
at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:107)
at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)
at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)
at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:200)
at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)
at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:490)
at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)
at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)
at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)
at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)
at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:408)
at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:66)
at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:836)
at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1747)
at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)
at java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
at java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)
at java.base/java.lang.Thread.run(Thread.java:1583)
sss
[]
