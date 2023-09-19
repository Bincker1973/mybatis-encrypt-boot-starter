# Mybatis-Encrypt-Boot-Starter
## 简介
Mybatis数据加解密模块

## 特性  
- 支持列级密码控制,提高数据安全性  
- 支持自定义加解密算法  
- 支持数据完整性校验
- 支持几乎所有数据类型加解密,原代码改动小
- 支持多线程数据解密，加快查询速度
- 支持自定义解密线程池
- 支持加密数据eq查询
- 同时兼容Mybatis和Mybatis Plus
- 内置AES、DES等基础数据加密实现

## 快速开始
1. 添加依赖
2. 添加注解
3. 更改字段数据类型
4. 完成

## 注意事项
- 加密字段在数据库中需要为Bytea类型
- 加密字段无法在查询中进行计算，只能进行eq查询
- 需要通过加密字段进行查询时，参数必须为实体类或EncryptParam和LambdaEncryptParam

## 进度
✔ 实现加密基础框架  
✔ AES默认实现  
✔ 数据插入加密  
✔ 单个字段数据查询  
✔ Mybatis Plus数据查询  
✔ Mybatis Plus Lambda数据查询  
× 数据更新测试  
× 数据完整性校验  
× Mybatis测试  
