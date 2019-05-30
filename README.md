# 秒杀系统解决方案demo

- 前端采用Bootstrap + jquery进行页面静态化的布局与渲染，作为必要的视图展示
- 后端采用Springboot + mybatis，快速开发，节省大量开发时间
- 中间件采用Redis缓存和消息队列RabbitMQ，提升秒杀并发量，缓解数据库压力
- 数据库为mysql
- 测试工具为JMeter，进行压测（使用中间件前后进行压力测试，将多次压测的qps结果做对比）

## 实现功能：
- 用户的注册与登录，实现了分布式session
- 商品列表
- 商品详情
- 秒杀商品
- 秒杀订单详情展示

### clone至本地后通过maven导入工程，只需修改application.yml配置即可启动
