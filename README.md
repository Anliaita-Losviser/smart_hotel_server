# smart_hotel_server

## 1.MySQL数据库表设计

| 序号 | 数据表名        | 中文名称  |
|----|-------------|-------|
| 1  | employees   | 员工表   |
| 2  | authorities | 权限表   |
| 3  | floors      | 楼层表   |
| 4  | roomTypes   | 房间类型表 |
| 5  | rooms       | 房间表   |
| 6  | prices      | 价格表   |

### 1.1 employees

employees表为员工表，用于存储商家内部的员工信息。具体表结构如下：

| 字段名         | 数据类型        | 说明     | 备注      |
|-------------|-------------|--------|---------|
| id          | bigint      | 主键     | 雪花算法生成  |
| name        | varchar(32) | 姓名     |         |
| username    | varchar(32) | 用户名    | 唯一      |
| password    | varchar(64) | 密码     |         |
| phone       | varchar(11) | 手机号    |         |
| sex         | varchar(2)  | 性别     | 1女 2男   |
| id_number   | varchar(18) | 身份证号   |         |
| status      | int         | 账号状态   | 1正常 0锁定 |
| authority   | varchar(64) | 权限标识码  | 逻辑外键    |
| create_time | datetime    | 创建时间   |         |
| update_time | datetime    | 最后修改时间 |         |

### 1.2 authorities

权限表，控制不同员工的权限

| 字段名         | 数据类型        | 说明     | 备注     |
|-------------|-------------|--------|--------|
| id          | bigint      | 主键     | 雪花算法生成 |
| name        | varchar(64) | 权限名    | 唯一     |
| code        | varchar(64) | 权限标识码  | 唯一     |
| create_time | datetime    | 创建时间   |        |
| update_time | datetime    | 最后修改时间 |        |

### 1.3 floors
楼层表

| 字段名         | 数据类型        | 说明     | 备注     |
|-------------|-------------|--------|--------|
| id          | bigint      | 主键     | 雪花算法生成 |
| name        | varchar(32) | 楼层名    |        |
| code        | varchar(32) | 楼层代码   | 唯一     |
| create_time | datetime    | 创建时间   |        |
| update_time | datetime    | 最后修改时间 |        |

### 1.4 roomTypes
房间类型表

| 字段名         | 数据类型        | 说明     | 备注     |
|-------------|-------------|--------|--------|
| id          | bigint      | 主键     | 雪花算法生成 |
| name        | varchar(64) | 类型名称   |        |
| code        | varchar(64) | 类型标识码  | 唯一     |
| create_time | datetime    | 创建时间   |        |
| update_time | datetime    | 最后修改时间 |        |

### 1.5 rooms
房间表

| 字段名           | 数据类型          | 说明     | 备注              |
|---------------|---------------|--------|-----------------|
| id            | bigint        | 主键     | 雪花算法生成          |
| code          | varchar(32)   | 房间号    | 唯一              |
| floor_code    | varchar(32)   | 楼层代码   | 非空，逻辑外键         |
| type_code     | varchar(64)   | 房间类型代码 | 非空，逻辑外键         |
| status        | int           | 房间状态   | 1正常 0维修         |
| charge_mode   | int           | 计费模式   | 0正常 1折扣 2钟点 3特价 |
| special_price | decimal(10,2) | 特别价格   |                 |
| create_time   | datetime      | 创建时间   |                 |
| update_time   | datetime      | 最后修改时间 |                 |

### 1.6 prices
价格表

| 字段名            | 数据类型          | 说明     | 备注     |
|----------------|---------------|--------|--------|
| id             | bigint        | 主键     | 雪花算法生成 |
| type_code      | varchar(64)   | 房间类型代码 | 逻辑外键   |
| nomal_price    | decimal(10,2) | 普通价格   | 非空     |
| discount_price | decimal(10,2) | 折扣价格   |        |
| hour_price     | decimal(10,2) | 钟点价格   |        |
