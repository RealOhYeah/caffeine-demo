# item-service

### 概述

其中的主要业务包括：

- 分页查询商品
- 新增商品
- 修改商品
- 修改库存
- 删除商品
- 根据id查询商品
- 根据id查询库存



### 使用技术

使用mybatis-plus,Caffeine,redis





### ItemController



```
分页查询商品
```



```
保存商品
```



```
更新商品
```



```
更新商品库存
```



```
删除商品
```



```
根据id查询商品（先去命中JVM缓存再找数据库）
```



```
根据id查询商品库存（先去命中JVM缓存再找数据库）
```





### ItemHandler implements EntryHandler`<Item>`

```
/**
 * Canal监察数据库的变化，将新增加的内容写入到JVM缓存中和Redis缓存中
 * @param item
 */
```



```
/**
 * Canal监察数据库的变化，将修改的内容写入到JVM缓存中和Redis缓存中
 * @param before
 * @param after
 */
```



```
/**
 * Canal监察数据库的变化，将需要删除的内容在JVM缓存中和Redis缓存中删除
 * @param item
 */
```



```
/**
 * Canal监察数据库的变化，将需要删除的内容在JVM缓存中和Redis缓存中删除
 * @param id
 */
```





### CaffeineConfig



```
/**
 * 创建一个Cache<Long, Item>缓存对象
 * @return
 */
```





```
/**
 * 创建一个Cache<Long, ItemStock>缓存对象
 * @return
 */
```



### RedisHandler implements InitializingBean 

```
/**
 * 缓存预热（从数据库中查询信息后存储在redis中）
 * @throws Exception
 */
```



```
/**
 * Canal监察数据库的变化，将新增/修改的内容写入到JVM缓存中和Redis缓存中
 * @param item
 */
```



### ItemApplication



```
/**
 * @description: mybatis-plus分页拦截
 * @return
 */
```









### CaffeineTest



```
/**
 * JVM进程缓存测试
 */
```



```
/*
 测试：
 Cache基于大小设置驱逐策略：
 */
```



```
/*
 测试：
 Cache基于时间设置驱逐策略：
 */
```



















































