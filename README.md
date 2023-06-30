# 博客后端

## Git设置

```shell
git config user.name sixmillions
git config user.email liubw95@163.com
```

## lombok

单元测试中不能使用`@RequiredArgsConstructor` 注入，会报错

```text
org.junit.jupiter.api.extension.ParameterResolutionException: No ParameterResolver registered for parameter
```

## flyway使用

1. 第一次会在数据库中生成一个表（flyway_schema_history），用于版本控制
2. 脚本执行后不能修改，如果修改了历史脚本，启动会报错；如果想修改，最简单的办法是清空数据库

### 简单使用

https://juejin.cn/post/7177643244166053949

https://juejin.cn/post/7245329078969401402

需要有操作数据的依赖，不然会报错，例如mybatis

https://blog.csdn.net/XiaoLiu_66/article/details/128571154

### 单元测试禁用flyway

application-test.yml + @ActiveProfiles("test")

```yaml
spring:
  flyway:
    enabled: false # 单元测试禁用flyway
```

