接口文档

https://heuqqdmbyk.feishu.cn/wiki/GyZVwpRf6ir89qkKtFqc0HlTnhg

# 工程搭建

1. 创建 Spring Boot 工程，并引入 web 开发起步依赖、mybatis、mysql 驱动、lombok。
2. 创建数据库表 `dept`，并在 `application.yml` 中配置数据库的基本信息。
3. 准备基础代码结构，并引入实体类 `Dept` 及统一的响应结果封装类 `Result`。

## 操作步骤

1. new 一个新的空 project。

2. 检查 JDK 版本，检查 Maven 路径，检查所有编码为 UTF-8。

3. 创建 Spring Boot 工程，开发语言 Java，构建工具 Maven，打包方式 Jar。

4. 选择 Spring Boot 版本，添加相关依赖（Lombok，Spring Web，MyBatis Framework，MySQL Driver）。

5. 删除不需要的文件，保留 `pom.xml`，`iml` 文件和必要的 `src` 目录（`resources` 目录下的所有文件都不需要）。

6. 在 `resources` 目录下重新创建 `application.yml` 文件。

7. 创建 DB `tlias`，new 一个 console，创建 `dept` 表，并导入数据

   ```sql
   CREATE TABLE dept (
     id int unsigned PRIMARY KEY AUTO_INCREMENT COMMENT 'ID, 主键',
     name varchar(10) NOT NULL UNIQUE COMMENT '部门名称',
     create_time datetime DEFAULT NULL COMMENT '创建时间',
     update_time datetime DEFAULT NULL COMMENT '修改时间'
   ) COMMENT '部门表';
   
   INSERT INTO dept VALUES (1,'学工部','2024-09-25 09:47:40','2024-09-25 09:47:40'),
                         (2,'教研部','2024-09-25 09:47:40','2024-09-09 15:17:04'),
                         (3,'咨询部','2024-09-25 09:47:40','2024-09-30 21:26:24'),
                         (4,'就业部','2024-09-25 09:47:40','2024-09-25 09:47:40'),
                         (5,'人事部','2024-09-25 09:47:40','2024-09-25 09:47:40'),
                         (6,'行政部','2024-11-30 20:56:37','2024-09-30 20:56:37');
   ```

8. 配置 `yml` 文件。

   ```yml
   spring:
   	application:
   	name: tlias-web-management
   	
   #配置数据库的连接信息
   datasource:
   	url: jdbc:mysql://localhost:3306/tlias
   	driver-class-name: com.mysql.cj.jdbc.Driver
   	username: root
   	password: 你的密码
   	
   #Mybatis的相关配置
   mybatis:
   	configuration:
   		log-impl: org.apache.ibatis.logging.stdout.StdoutImpl
   ```

9. 创建基础包结构。

   1. `pojo` 存放实体类。
   2. `controller` 存放控制层代码。
   3. `service` 存放业务层代码。
   4. `mapper` 存放持久层（DAO）代码。

10. 引入 `dept` 实体类。

    ```java
    package com.itheima.pojo;
    
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    
    import java.time.LocalDateTime;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class Dept {
        private Integer id;
        private String name;
        private LocalDateTime createTime;
        private LocalDateTime updateTime;
    }
    ```

11. 引入 `Result` 实体类。

    ```java
    package com.itheima.pojo;
    
    import lombok.Data;
    
    import java.io.Serializable;
    
    /**
     * 后端统一返回结果
     */
    @Data
    public class Result {
    
        private Integer code; //编码：1成功，0为失败
        private String msg; //错误信息
        private Object data; //数据
    
        public static Result success() {
            Result result = new Result();
            result.code = 1;
            result.msg = "success";
            return result;
        }
    
        public static Result success(Object object) {
            Result result = new Result();
            result.data = object;
            result.code = 1;
            result.msg = "success";
            return result;
        }
    
        public static Result error(String msg) {
            Result result = new Result();
            result.msg = msg;
            result.code = 0;
            return result;
        }
    
    }
    ```

12. 准备 `mapper` 接口，即在 mapper 目录下创建 interface `DeptMapper` 并添加注解 `@Mapper`。

13. 准备 `service` 接口，即在 service  目录下创建 interface `DeptService`。

14. 在 service 目录下创建实现类（impl）目录，在目录下创建 `DeptService` 的实现类 `DeptServiceImpl` 并实现 DeptService interface 并添加注解 `@Service`。

15. 准备 `controller` 类，即在 controller  目录下创建类 `DeptController` 并添加注解 `@RestController`。

    ```java
    //
    // Source code recreated from a .class file by IntelliJ IDEA
    // (powered by FernFlower decompiler)
    //
    
    package org.springframework.web.bind.annotation;
    
    import java.lang.annotation.Documented;
    import java.lang.annotation.ElementType;
    import java.lang.annotation.Retention;
    import java.lang.annotation.RetentionPolicy;
    import java.lang.annotation.Target;
    import org.springframework.core.annotation.AliasFor;
    import org.springframework.stereotype.Controller;
    
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Controller
    @ResponseBody // 将方法的返回值直接作为相应数据相应给前端，如果数据是一个对象或一个集合，会先转 json，然后再响应
    public @interface RestController {
        @AliasFor(
            annotation = Controller.class
        )
        String value() default "";
    }
    ```

    添加注解 `@RestController` 就相当于也添加了 `@ResponseBody` 注解。

16. 关联 DB `tlias`。

# 部门管理

## 查询部门

### 根据需求文档

1. 不考虑分页展示。
2. 查询结果根据最后修改时间倒序排序。

### 根据接口文档

1. 请求路径为：`/depts`

2. 请求方式为：`GET`

3. 相应数据样例：

   ```json
   {
     "code": 1,
     "msg": "success",
     "data": [
       {
         "id": 1,
         "name": "学工部",
         "createTime": "2022-09-01T23:06:29",
         "updateTime": "2022-09-01T23:06:29"
       },
       {
         "id": 2,
         "name": "教研部",
         "createTime": "2022-09-01T23:06:29",
         "updateTime": "2022-09-01T23:06:29"
       }
     ]
   }
   ```

### 三层架构

Mapper：执行 SQL。

Service：调用 Mapper。

Controller：1. 接收请求。2. 调用 Service。3. 相应结果。

### 开发步骤

1. 编写 SQL，查询全部部门。

   ```sql
   select id, name,create_time, update_time from dept order by update_time desc;
   ```

2. 编写 Controller 层，编写 `public` 方法，返回值为 `Result`，方法名 `list`。

3. 给方法添加 `@RequestMapping` 注解，请求路径为 `/depts`。

4. 方法内用 sout 打印日志 “查询全部部门数据”。

5. 接下来要调用 Service。现在 Service 的实现已经交给 IOC 容器，所以首先需要注入 Service，即在类里面声明并添加 DI 的注解 `@Autowired`。

   ```java
   @Autowired
   private DeptService deptservice;
   ```

6. 调用 deptService 的方法查询全部部门，定义方法名为 `findAll`，没有参数，有返回值，返回值是一个个的 Dept 对象，所以返回结果封装在 `List` 集合中，集合泛型为 `Dept`。

   ```java
   List<Dept> deptList = deptService.findAll();
   ```

7. 返回结果，调用 `Result.success`，将 `deptList` 传给 `data`。

8. 在 `findAll`  上按 `Alt + Enter`，在 `DeptService` interface 创建 `findAll`。

9. 进入 interface 的 impl，在类上 `Alt + Enter`，实现 `findAll`。

10. ServiceImpl `return` 的地方要调用 Mapper 的方法，所以要先注入 Mapper。

    ```java
    @Autowired
    private DeptMapper deptMapper;
    ```

11. 接着 `return` Mapper 的方法，定义方法名为 `findAll`，`Alt + Enter` 在 Mapper 中创建 `findAll`。

12. 在 Mapper 中添加 `Select` 注解，执行 SQL。

### ApiFox

快捷请求：http://localhost:8080/depts

发现问题：

1. 无论采用哪种请求方式，都可以请求到数据。
2. 时间并没有成功封装在 `json` 中。

解决问题：

1. 将注解 `@RequestMapping` 改为 `@GetMapping`。
2. 开启驼峰命名：如果字段名与属性名符合驼峰命名规则，mybatis 会自动通过驼峰命名规则映射。

   ```yml
   mybatis:
   	configuration:
   		map-underscore-to-camel-case: true
   ```

## 删除部门

### 根据需求文档

删除的条件：主键 ID。

### 根据接口文档

1. 请求路径：`/depts`

2. 请求方式：`DELETE`

3. 请求参数样例：

   ```html
   /depts?id=1
   ```

4. 相应数据样例：

   ```json
   {
       "code":1,
       "msg":"success",
       "data":null
   }
   ```

### 三层架构

Mapper：执行 SQL。

Service：调用 Mapper。

Controller：1. 接收请求（/depts?id=1）。2. 调用 Service。3. 相应结果。

### 开发步骤

1. 编写 Controller 层，定义 `public` 方法，返回值为 `Result`，方法名叫 `delete`，接收参数 `Integer` 的 `id`。

2. 在方法上添加 `DeleteMapping` 注解，指定请求路径。

3. 用 sout 输出日志 “根据 ID 删除部门：id”。

4. 返回 `Result`。

5. 返回前调用 deptService 的方法，定义方法名为 `deleteById`，并把 `id` 作为参数传递。（增删改操作没有返回值）

6. 在方法名上 `Alt + Enter`，在 Service 层创建 `deleteById` 方法。

7. 进入 service 的实现类，在类上 `Alt + Enter` 实现 `deleteById` 方法。

8. 在 service 实现类的 `deleteById` 方法中调用 deleteMapper 的方法，定义方法名叫 `deleteById`，并传递 `id` 参数。

9. 在方法名上 `Alt + Enter`，在 Mapper 层创建 `deleteById` 方法。

10. 添加 `Delete` 注解，执行 SQL。

   ```sql
   delete from dept where id = #{id};
   ```

## 新增部门

### 根据需求文档

输入部门名称，往 `dept` 表插入一条数据。

### 根据接口文档

1. 请求路径：`/depts`

2. 请求方式：`POST`

3. 请求参数样例：

   ```json
   {
       "name": "教研部"
   }
   ```

4. 响应数据样例

   ```json
   {
       "code":1,
       "msg":"success",
       "data":null
   }
   ```

### 三层架构

Mapper：执行 SQL。

Service：1. 补全基础属性。2. 调用 Mapper。

Controller：1. 接收请求（/depts）。2. 调用 Service。3. 相应结果。

### 开发步骤

1. 编写 Controller 层，定义 `public` 方法，返回值为 `Result`，方法名叫 `add`，接收参数 `Dept` 的 `dept`，并添加注解 `RequestBody`。

2. 在方法上添加 `PostMapping` 注解，指定请求路径。

3. 用 sout 打印日志 “新增部门：dept”。

4. 返回 `Result`。

5. 返回前调用 Service 的方法，定义方法名为 `add`，并传递 `dept`。

6. 方法上 `Alt + Enter` 在 `DeptService` interface 创建方法。

7. 进入 Service 实现类，类上 `Alt + Enter` 实现方法。

8. 方法内先补全基本属性。（creatTime、updateTime）

   ```java
   dept.setCreatTime(LocalDateTime.now());
   dept.setUpdateTime(LocalDateTime.now());
   ```

9. 再调用 Mapper interface 的方法插入数据，定义方法名为 `add`，并传递 `dept`。

10. 方法上 `Alt + Enter` 实现方法，添加 `Insert` 注解，执行 SQL。

    ```sql
    insert into dept(name, create_time, update_time) values(#{name}, #{createTime}, #{updateTime});
    ```

## 修改部门（数据回显）

### 根据需求文档

1. 查询回显，根据 ID 查询部门。
2. 修改数据。

### 根据接口文档

1. 请求路径：`/depts/{id}`

2. 请求方式：`GET`

3. 请求参数样例

   ```http
   /depts/1
   ```

4. 响应数据样例

   ```java
   {
     "code": 1,
     "msg": "success",
     "data": {
       "id": 1,
       "name": "学工部",
       "createTime": "2022-09-01T23:06:29",
       "updateTime": "2022-09-01T23:06:29"
     }
   }
   ```

### 三层架构

Mapper：执行 SQL。

Service：调用 Mapper。

Controller：1. 接收请求（/depts/1）。2. 调用 Service。3. 相应结果。

### 开发步骤

1. 编写 Controller 层，定义 `public` 方法，返回值为 `Result`，方法名叫 `getInfo`，接收参数 `Integer` 的 `id`，并添加注解 `PathVariable`。

2. 在方法上添加 `GetMapping` 注解，指定请求路径，路径参数的参数名用大括号占位符。

3. 用 sout 输出日志 “根据 ID 查询部门：id”。

4. 返回 `Result`。

5. 返回前调用 `deptService` 的方法，定义方法名为 `getById`，并传递 `id`，得到对象后有返回值，所以用 `Dept` 类型的 `dept` 接收。

6. 返回的 `success` 结果内传递 `dept`。

7. 方法上 `Alt + Enter`，在 Service 实现方法，进入其实现类，在类上 `Alt + Enter` 实现方法。

8. 返回的位置直接调用 `deptMapper` 的方法 `getById`，并传递 `id`。

9. 方法上 `Alt + Enter`，在 Mapper 实现方法。

10. 添加 `Select` 注解，执行 SQL。

    ```sql
    select id, name, create_time, update_time from dept where id = #{id};
    ```


## 修改部门（数据修改）

### 根据需求文档

1. 查询回显，根据 ID 查询部门。
2. 修改数据，根据 ID 修改部门。

### 根据接口文档

1. 请求路径：`/depts`

2. 请求方式：`PUT`

3. 请求参数样例

   ```json
   {
       "id": 1,
       "name": "教研部"
   }
   ```

4. 响应数据样例

   ```java
   {
       "code":1,
       "msg":"success",
       "data":null
   }
   ```

### 三层架构

Mapper：执行 SQL。

Service：1. 补全基础属性。2. 调用 Mapper。

Controller：1. 接收请求（json）。2. 调用 Service。3. 相应结果。

### 开发步骤

1. 首先，编写更新的 SQL 语句。

   ```sql
   update dept set name = '', update_time = '' where id = 1;
   ```

2. 编写 Controller，定义 `public` 的方法，`update`，返回值为 `Result`，并添加注解 `PutMapping` 添加请求路径 `/depts`。

3. 在方法上接收 json 并封装到 `Dept` 的 `dept`，添加注解 `RequestBody`。

4. 用 sout 输出日志 “修改部门：dept”。

5. 调用 `deptService` 的方法，定义方法名为 `update` 并传递 `dept`。

6. 返回 `Result`。

7. 方法上 `Alt + Enter`，在 interface 中定义方法，进入实现类 `Alt + Enter` 实现方法。

8. 先补全基础属性，即定义 `updateTime` 为 `LocalDateTime.now()`。

   ```java
   dept.setUpdateTime(LocalDateTime.now());
   ```

9. 再调用 Mapper 的方法，定义方法名为 `update`，并传递 `dept`。

10. 方法名上 `Alt + Enter`，添加注解 `Insert` 执行 SQL。

    ```sql
    update dept set name = #{name}, update_time = #{updateTime} where id = #{id};
    ```

## 程序优化

### @RequestMapping

把所有的请求路径用注解 `@RequestMapping` 直接写在类上，而不需要在每个方法声明。

个完整的请求路径，应该是类上的的 `value` 属性 + 方法上的 `@RequestMapping` 的 `value` 属性。

```java
@RequdetMapping("/depts")
```

### log

在 Controller 添加注解 `Slf4j`。

这样后面所有 sout 输出的日志都可以采用 `log.` 的形式输出。

# 员工管理

## 准备工作

1. 建表：`emp`、`emp_expr`。
2. 准备实体类：`Emp`、`EmpExpr`。
3. 准备三层架构基本代码结构。`EmpController`、`EmpService / EmpServiceImpl`、`EmpMapper`。

### 建表

```sql
-- 员工表
create table emp(
    id int unsigned primary key auto_increment comment 'ID,主键',
    username varchar(20) not null unique comment '用户名',
    password varchar(32) default '123456' comment '密码',
    name varchar(10) not null comment '姓名',
    gender tinyint unsigned not null comment '性别, 1:男, 2:女',
    phone char(11) not null unique comment '手机号',
    job tinyint unsigned comment '职位, 1 班主任, 2 讲师 , 3 学工主管, 4 教研主管, 5 咨询师',
    salary int unsigned comment '薪资',
    image varchar(255) comment '头像',
    entry_date date comment '入职日期',
    dept_id int unsigned comment '部门ID',
    create_time datetime comment '创建时间',
    update_time datetime comment '修改时间'
) comment '员工表';


INSERT INTO emp VALUES 
(1,'shinaian','123456','施耐庵',1,'13309090001',4,15000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2000-01-01',2,'2023-10-20 16:35:33','2023-11-16 16:11:26'),
(2,'songjiang','123456','宋江',1,'13309090002',2,8600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:37'),
(3,'lujunyi','123456','卢俊义',1,'13309090003',2,8900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-05-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:39'),
(4,'wuyong','123456','吴用',1,'13309090004',2,9200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:41'),
(5,'gongsunsheng','123456','公孙胜',1,'13309090005',2,9500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-12-05',2,'2023-10-20 16:35:33','2023-10-20 16:35:43'),
(6,'huosanniang','123456','扈三娘',2,'13309090006',3,6500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2013-09-05',1,'2023-10-20 16:35:33','2023-10-20 16:35:45'),
(7,'chaijin','123456','柴进',1,'13309090007',1,4700,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2005-08-01',1,'2023-10-20 16:35:33','2023-10-20 16:35:47'),
(8,'likui','123456','李逵',1,'13309090008',1,4800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2014-11-09',1,'2023-10-20 16:35:33','2023-10-20 16:35:49'),
(9,'wusong','123456','武松',1,'13309090009',1,4900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-03-11',1,'2023-10-20 16:35:33','2023-10-20 16:35:51'),
(10,'linchong','123456','林冲',1,'13309090010',1,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2013-09-05',1,'2023-10-20 16:35:33','2023-10-20 16:35:53'),
(11,'huyanzhuo','123456','呼延灼',1,'13309090011',2,9700,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-02-01',2,'2023-10-20 16:35:33','2023-10-20 16:35:55'),
(12,'xiaoliguang','123456','小李广',1,'13309090012',2,10000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-08-18',2,'2023-10-20 16:35:33','2023-10-20 16:35:57'),
(13,'yangzhi','123456','杨志',1,'13309090013',1,5300,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-11-01',1,'2023-10-20 16:35:33','2023-10-20 16:35:59'),
(14,'shijin','123456','史进',1,'13309090014',2,10600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2002-08-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:01'),
(15,'sunerniang','123456','孙二娘',2,'13309090015',2,10900,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-05-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:03'),
(16,'luzhishen','123456','鲁智深',1,'13309090016',2,9600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2010-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:05'),
(17,'liying','12345678','李应',1,'13309090017',1,5800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-03-21',1,'2023-10-20 16:35:33','2023-10-20 16:36:07'),
(18,'shiqian','123456','时迁',1,'13309090018',2,10200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:09'),
(19,'gudasao','123456','顾大嫂',2,'13309090019',2,10500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2008-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:11'),
(20,'ruanxiaoer','123456','阮小二',1,'13309090020',2,10800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2018-01-01',2,'2023-10-20 16:35:33','2023-10-20 16:36:13'),
(21,'ruanxiaowu','123456','阮小五',1,'13309090021',5,5200,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2015-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:15'),
(22,'ruanxiaoqi','123456','阮小七',1,'13309090022',5,5500,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2016-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:17'),
(23,'ruanji','123456','阮籍',1,'13309090023',5,5800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2012-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:19'),
(24,'tongwei','123456','童威',1,'13309090024',5,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2006-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:21'),
(25,'tongmeng','123456','童猛',1,'13309090025',5,4800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2002-01-01',3,'2023-10-20 16:35:33','2023-10-20 16:36:23'),
(26,'yanshun','123456','燕顺',1,'13309090026',5,5400,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2011-01-01',3,'2023-10-20 16:35:33','2023-11-08 22:12:46'),
(27,'lijun','123456','李俊',1,'13309090027',2,6600,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2004-01-01',2,'2023-10-20 16:35:33','2023-11-16 17:56:59'),
(28,'lizhong','123456','李忠',1,'13309090028',5,5000,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2007-01-01',3,'2023-10-20 16:35:33','2023-11-17 16:34:22'),
(30,'liyun','123456','李云',1,'13309090030',NULL,NULL,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2020-03-01',NULL,'2023-10-20 16:35:33','2023-10-20 16:36:31'),
(36,'linghuchong','123456','令狐冲',1,'18809091212',2,6800,'https://web-framework.oss-cn-hangzhou.aliyuncs.com/2023/1.jpg','2023-10-19',2,'2023-10-20 20:44:54','2023-11-09 09:41:04');


-- 员工工作经历信息
create table emp_expr(
    id int unsigned primary key auto_increment comment 'ID, 主键',
    emp_id int unsigned comment '员工ID',
    begin date comment '开始时间',
    end  date comment '结束时间',
    company varchar(50) comment '公司名称',
    job varchar(50) comment '职位'
)comment '工作经历';
```

### 添加实体类

```java
package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class Emp {
    private Integer id; //ID,主键
    private String username; //用户名
    private String password; //密码
    private String name; //姓名
    private Integer gender; //性别, 1:男, 2:女
    private String phone; //手机号
    private Integer job; //职位, 1:班主任,2:讲师,3:学工主管,4:教研主管,5:咨询师
    private Integer salary; //薪资
    private String image; //头像
    private LocalDate entryDate; //入职日期
    private Integer deptId; //关联的部门ID
    private LocalDateTime createTime; //创建时间
    private LocalDateTime updateTime; //修改时间
}
```

```java
package com.itheima.pojo;

import lombok.Data;

import java.time.LocalDate;

/**
 * 工作经历
 */
@Data
public class EmpExpr {
    private Integer id; //ID
    private Integer empId; //员工ID
    private LocalDate begin; //开始时间
    private LocalDate end; //结束时间
    private String company; //公司名称
    private String job; //职位
}
```

### 三层架构基本框架

1. 创建 `EmpMapper` interface，添加 `Mapper` 注解。

2. 创建 `EmpExperMapper` interface，添加 `Mapper` 注解。

3. 创建 `EmpService` interface。

4. 创建 `EmpServiceImpl` 实现类，添加 `Service` 注解。

5. 创建 `EmpController` 类，添加 `RestController` 和 `Slf4j` 注解。

6. 准备 SQL，查询所有的员工信息，以及员工所属的部门名称。

   ```sql
   select e.*, d.name from emp e left join dept d on e.dept_id = d.id;
   ```

## 分页查询（原始方式）

### 根据需求文档

1. 前端传给后端的分页参数：

​	页码：page（默认1）

​	每页展示记录数：pagesize（默认10）

2. 后端给前端返回的数据：

​	数据列表：List rows

​	总记录数：Long total

3. 进行分页展示。
4. 根据修改时间倒序排序。

### 根据接口文档

1. 请求路径：`/emps`

2. 请求方式：`GET`

3. 请求数据样例：

   ```http
   /emps?name=张&gender=1&begin=2007-09-01&end=2022-09-01&page=1&pageSize=10
   ```

4. 相应数据样例：

   ```json
   {
     "code": 1,
     "msg": "success",
     "data": {
       "total": 2,
       "rows": [
          {
           "id": 1,
           "username": "jinyong",
           "name": "金庸",
           "gender": 1,
           "image": "https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-02-00-27-53B.jpg",
           "job": 2,
           "salary": 8000,
           "entryDate": "2015-01-01",
           "deptId": 2,
           "deptName": "教研部",
           "createTime": "2022-09-01T23:06:30",
           "updateTime": "2022-09-02T00:29:04"
         },
         {
           "id": 2,
           "username": "zhangwuji",
           "name": "张无忌",
           "gender": 1,
           "image": "https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-02-00-27-53B.jpg",
           "job": 2,
           "salary": 6000,
           "entryDate": "2015-01-01",
           "deptId": 2,
           "deptName": "教研部",
           "createTime": "2022-09-01T23:06:30",
           "updateTime": "2022-09-02T00:29:04"
         }
       ]
     }
   }
   ```

### 三层架构

Mapper：执行两条 SQL。

Service：1. 调用 Mapper，查询总记录数。2. 调用 Mapper，查询结果列表。3. 封装 `PageResult` 对象，返回。

Controller：1. 接收请求（分页）。2. 调用 Service，获取 `PageResult`。3. 相应结果。

### 开发步骤

1. 刷新数据库表。

2. 准备 SQL，查询所有的员工信息，每页展示五条。

   ```sql
   select e.*, d.name from emp e left join dept d on e.dept_id = d.id limit (页-1)*每页记录数, 5;
   ```

3. 准备 SQL，查询总记录数。

   ```sql
   select count(*) from emp e left join dept d on e.dept_id = d.id;
   ```

4. 创建实体类 `PageResult`，用来封装分页结果。有两个属性，一个是 `Long` 的 `total`，另一个是 `List` 的 `rows`，`List` 的泛型是 `T`，也就是说，在类上也需要指定泛型  `T`。

5. 给实体类添加 get、set 方法，可以直接使用注解 `Data` 完成。同时添加 `AllArgsConstructor` 和 `NoArgsConstructor` 注解。

6. 编写 `EmpMapper`，定义方法，方法名为 `count`，返回值为 `Long`，添加注解 `Select`，执行 SQL。

7. 定义方法，方法名为 `list`，返回值为 `List`，泛型为 `Emp` ，参数列表接收前端传来的起始索引（`Integer` 的 `start`）和每页记录数（`Integer` 的 `pageSize`），添加注解 `Select`，执行 SQL。

   ```sql
   select e.*, d.name from emp e left join dept d on e.dept_id = d.id order by e.update_time desc limit #{start}, #{pageSize};
   ```

8. `list` 方法执行的 SQL 返回的数据还有部门名称，所以要在 `Emp` 实体类中添加属性 `String` 的 `deptName`。同时修改 SQL 的 `d.name`，为其取别名为 `deptName`，使其可以被封装。

9. 编写 Controller，定义 `public` 的方法 `page`，返回为 `Result`，接收参数 `Integer` 的 `page` 和 `Integer` 的 `pageSize`，添加注解 `GetMapping`。同时在类添加 `RequestMapping` 注解，指定路径为 `/emps`。

10. 方法内用 `log.info` 记录日志 “分页查询：page, pageSize”。

11. 注入 `EmpService` 的 `empService`，调用 Service 的方法，定义方法名为 `page`，并传递 `page` 和 `pageSize`。把调用的结果封装在 `PageResule<Emp>` 的 `pageResult`。

12. 将 `pageResult` 封装在 `Result` 中并返回。

13. 方法上 `Alt + Enter`，定义方法，进入实现类，实现方法。

14. 业务层中首先注入 `EmpMapper` 的 `empMapper`，调用 Mapper 的 `count` 方法，查询记录数，结果用 `Long` 的 `total` 接收，调用 `list` 方法，并传递 `start` 和 `pageSize`，`start` 的值为 `(page-1)*pageSize`，查询结果列表，结果用 `List<Emp>` 的 `rows` 封装。

15. 最后封装结果 `PageResult`。

    ```java
    return new PageResult<Emp>(total, rows);
    ```

16. 至此已经完成了查询，但是还没有设置 `page` 和 `pageSize` 的默认值。

17. 在 Controller 中给两个参数添加 `@RequestParam` 注解，并指定 `defaultValue`。

    ```java
    public Result page(@RequestParam(defaultValue = "1") Integer page, @RequestParam(defaultValue = "10") Integer pageSize)
    ```

## 分页查询（PageHelper）

修改步骤：

1. 引入相关依赖。

2. 删除原 SQL 中的分页操作，删除方法中的参数。

   ```sql
   @Select（"selecte.*, d.name deptName from emp e left join dept d on e.dept_id = d.id oder by e.update_time desc")
   public List<Emp>list();
   ```

3. Service 中重新编写方法体。

   1. 先设置分页参数，直接调用 `PageHelper` 的 `startPage` 方法，传入 `page` 和 `pageSize` 参数。

      ```java
      PageHelper.startPage(page, pageSize);
      ```

   2. 执行查询，也就是调用 `empMapper` 的 `list` 方法，接收参数由 `List<Emp>` 的 `empList` 接收。

      ```java
      List<Emp> empList = empMapper.list();
      ```

   3. 最后解析查询结果并封装。

      ```java
      return new PageResult<Emp>();
      ```
   
   4. 参数需要两个，第二个参数 `rows` 已经有了，但第一个 `total` 没有，`PageHelper` 会自动帮我们完成分页操作，将结果封装在一个对象中，这个对象叫 `Page`，所以在这里声明一个 `Page<Emp> p`，然后将查询到的 `empList` 强转成 `Page`，然后在 `Page` 里就有查询到的所有信息。
   
      ```java
      Page<Emp> p = (Page<Emp>) empList;
      return new PageResult<Emp>(p.getTotal(), p.getResult); 
      ```

## 条件分页查询

### 根据需求文档

1. 输入员工名称进行查询。
2. 选择员工性别进行精确查询。
3. 选择入职时间的开始时间和结束时间，可进行范围查询。

### 根据接口文档

1. 请求路径：`/emps`

2. 请求方式：`GET`

3. 请求数据样例：

   ```html
   /emps?name=张&gender=1&begin=2007-09-01&end=2022-09-01&page=1&pageSize=10
   ```

4. 相应数据样例：

   ```json
   {
     "code": 1,
     "msg": "success",
     "data": {
       "total": 2,
       "rows": [
          {
           "id": 1,
           "username": "jinyong",
           "name": "金庸",
           "gender": 1,
           "image": "https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-02-00-27-53B.jpg",
           "job": 2,
           "salary": 8000,
           "entryDate": "2015-01-01",
           "deptId": 2,
           "deptName": "教研部",
           "createTime": "2022-09-01T23:06:30",
           "updateTime": "2022-09-02T00:29:04"
         },
         {
           "id": 2,
           "username": "zhangwuji",
           "name": "张无忌",
           "gender": 1,
           "image": "https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-02-00-27-53B.jpg",
           "job": 2,
           "salary": 6000,
           "entryDate": "2015-01-01",
           "deptId": 2,
           "deptName": "教研部",
           "createTime": "2022-09-01T23:06:30",
           "updateTime": "2022-09-02T00:29:04"
         }
       ]
     }
   }
   ```

### 三层架构

Mapper：执行 SQL。

Service：1. 使用 PageHelper 完成分页条件查询。2. 封装 `PageResult` 对象，返回。

Controller：1. 接收请求（分页、条件）。2. 调用 Service，获取 `PageResult`。3. 相应结果。

### 开发步骤

1. 准备 SQL

   ```sql
   select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id 
   where e.name like '%阮%' and e.gender = 1 and e.entry_date between '2010-01-01' and '2020-01-01'
   order by e.update_time desc;
   ```

2. 在 Controller 中添加参数，`String` 的 `name`，`Integer` 的 `gender`，`LocalDate` 的 `begin`，`LocalDate` 的 `end`。

3. 指定日期时间类型传递的格式，使用 `DateTimeFormat` 注解，注解内使用 `pattern` 指定格式。

   ```java
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   ```

4. 用 `log.info` 打印日志 “分页查询：page, pageSize, name, gender, begin, end”。

5. 在使用 `page` 的时候添加传递参数。同时在定义方法的地方还有实现类中增加参数。

6. 在 `list` 方法定义的地方添加条件对应的参数，然后在 Service 中传递参数。

7. 这个 SQL 相对复杂，所以我们在 xml 下定义 SQL，在 `resources` 目录下创建包 `com.itheima.mapper`，然后在该目录下创建文件 `EmpMapper.xml`。

8. 从 MyBatis 的文档中复制 XML 的框架，然后删除不需要的内容，再此基础上进行更改。

9. 将映射文件的 `namespace` 属性设为 mapper interface 的全类名。

10. 删除原 SQL，在方法上 `Alt + Enter`，在 xml 生成 SQL。直接在标签中编写 SQL 就可以了，将原 SQL 复制到 xml 中，然后更改固定的内容为占位符形式。

### 程序优化（参数过多）

将多个请求参数封装成一个对象，这样我们在 Controller 中只需要调用对象就可以了，然后在对象中就可以获取到请求参数。

开发步骤：

1. 在 pojo 下定义实体类 `EmpQueryParam`，并添加 `Data` 注解，然后定义属性。

   ```java
   @Data 
   public class EmpQueryParam {
       private Integer page = 1;
       private Integer pageSize = 10;
       private String name;
       private Integer gender;
       @DateTimeFormat(pattern = "yyyy-MM-dd")
       private LocalDate begin;
       @DateTimeFormat(pattern = "yyyy-MM-dd")
       private LocalDate end;
   }
   ```

2. 更改 Controller，参数直接调用实体类就行。

   ```java
   @GetMapping
   public Result page(EmpQueryParam empQueryParam){
       log.info("分页查询：{}", empQueryParam);
       PageResult<Emp> pageResult = empService.page(empQueryParam);
       return Result.success(pageResult);
   }
   ```

3. 重新在 Service 中定义 `page` 方法。

4. 更改 Service 实现类中的 `page`，将参数更改为实体类。

5. 方法体的参数都改为实体类调用的方式。

6. `list` 的参数比较多，可以直接传实体类，再把形参的部分也更改为实体类对象就可以了。

### 程序优化（SQL 条件被写死）

目前在 xml 文件中的 SQL 是被写死的，但实际使用的时候查询条件会随着用户输入的条件变化而变化，所以应该把 SQL 改为动态 SQL。

开发步骤：

1. 直接更改 xml 里面的 SQL 就可以。

   ```sql
   <select id="list" resultType="com.gyqstd.pojo.Emp">
           select e.*, d.name deptName from emp e left join dept d on e.dept_id = d.id
           <where>
               <if test="name != null and name != ''">
                   e.name like concat('%', #{name}, '%')
               </if>
               <if test="gender != null">
                   and e.gender = #{gender}
               </if>
               <if test="begin != null and end != null">
                   and e.entry_date between #{begin} and #{end}
               </if>
           </where>
           order by e.update_time desc
       </select>
   ```


## 新增员工

### 根据需求文档

1. 员工的基本信息存储在 `emp` 表。
2. 员工工作经历信息存储在 `emp_expr` 表。

### 根据接口文档

1. 请求路径：`/emps`

2. 请求方式：`POST`

3. 请求数据样例：

   ```json
   {
     "image": "https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-03-07-37-38222.jpg",
     "username": "linpingzhi",
     "name": "林平之",
     "gender": 1,
     "job": 1,
     "entryDate": "2022-09-18",
     "deptId": 1,
     "phone": "18809091234",
     "salary": 8000,
     "exprList": [
         {
            "company": "百度科技股份有限公司",
            "job": "java开发",
            "begin": "2012-07-01",
            "end": "2019-03-03"
         },
         {
            "company": "阿里巴巴科技股份有限公司",
            "job": "架构师",
            "begin": "2019-03-15",
            "end": "2023-03-01"
         }
      ]
   }
   ```

4. 相应数据样例：

   ```json
   {
       "code":1,
       "msg":"success",
       "data":null
   }
   ```

### 三层架构

Mapper：执行两条 SQL。

Service：1. 保存员工基本信息。2. 批量保存员工的工作经历信息

Controller：1. 接收请求（json）。2. 调用 Service。3. 相应结果。

### 开发步骤

1. 在实体类 `Emp` 中封装工作经历信息。即定义 `private` 的 `List` 属性 `exprList`。`List` 的泛型是 `EmpExpr`。

2. 编写 Controller，定义 `public` 的方法 `save`，返回为 `Result`。添加注解 `PostMappping`。参数要接收前端传递的 json，也就是刚才封装好的 `Emp`。并添加注解 `RequestBody`。

3. 用 `log.info` 打印日志 “新增员工：emp”。

4. 调用 Service 的方法，并传递 `emp`，定义方法名为 `save`。

5. 返回 `Result`。

6. 方法上 `Alt + Enter`，在 Service interface 中定义方法，进入实现类，实现方法。

7. 先来完成保存员工的基本信息，先给 `creatTime` 和 `updateTime` 赋值为 `LocalDateTime.now()`，然后直接调用 Mapper 的方法，并传递 `emp`，定义方法名 `insert`。

8. 方法上 `Alt + Enter`，在 Mapper 中定义方法，添加 `Insert` 注解，定义 SQL。

   ```java
   @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time) values (#{username}, #{name}, #{gender}, #{phone}, #{job}, #{salary}, #{image}, #{entryDate}, #{deptId}, #{createTime})")
   ```

9. 至此，完成了保存员工的基本信息。接下来，保存员工工作经历信息。

10. 定义 `List` 的 `exprList` 来获取 `emp` 中 `ExprList` 的内容，`List` 泛型为 `EmpExpr。

    ```java
    List<EmpExpr> exprList = emp.getExprList();
    ```

11. 如果工作经历不为空，再保存工作经历，所以要先判断，直接调用 `CollectionUtils` 这个工具类的 `isEmpty` 来判断是否为空。

    ```java
    if(!CollectionUtils.isEmpty(exproList))
    ```

12. 如果不为空，就调用 `EmpExprMapper`，所以要先注入 `EmpExprMapper`，然后调用其方法，定义方法名为 `insertBatch`，并传递 `exprList`。

13. 方法上 `Alt + Enter`，定义方法，然后通过 xml 定义 SQL。

14. 先创建映射文件，命名为 `EmpExprMapper.xml` 并修改文件。

15. Mapper 的方法上 `Alt + Enter` 定义 SQL。

    ```xml
    insert into emp_expr(emp_id, begin, end, company, job)
    <foreach collection = "exprList" item = "expr" separator = ",">
    	(#{expr.empId}, #{expr.begin}, #{expr.end}, #{expr.company}, #{expr.job})
    </foreach>
    ```

16. 在前端接收的数据中，工作经历是没有 ID 的，所以我们要获取刚刚添加的员工基本信息的 ID 值，可以在 Mapper 使用 `Options` 注解，将 `useGeneratedKeys` 设为 `true`，这样就可以获取到主键，然后使用 `keyProperty` 属性指定封装到对象的哪个属性。

    ```java
    @Options(useGeneratedKeys = true, keyProperty = "id")
    ```

17. 在 Service 的实现类中，调用 `insertBatch` 之前还需要遍历集合，为 `empId` 复制

    ```java
    exprList.forEach(empExpr -> {
        empExpr.setEmpId(emp.getId());
    });
    ```

## 文件上传

### 根据需求文档

将文件存储在阿里云 OSS。

### 根据接口文档

1. 请求路径：`/upload`

2. 请求方式：`POST`

3. 请求参数：文件。

4. 相应数据样例：

   ```json
   {
       "code": 1,
       "msg": "success",
       "data": "https://web-framework.oss-cn-hangzhou.aliyuncs.com/2022-09-02-00-27-0400.jpg"
   }
   ```

### 开发步骤

1. 单独创建 `utils` 目录，目录下引入阿里云 OSS 文件上传工具类。

2. 编写 Controller，定义 `public` 的 `upload`，返回值为 `Result`。添加注解 `PostMapping`。传递 `MultipartFile` 的 `file`。

3. 用 `log.info` 打印日志 “文件上传：file.getOriginalFilename”。

4. 将文件交给 OSS，所以先注入其工具类。调用其 `upload` 方法。传递其字节数组和原始文件名。 这里如果有异常直接抛出就可以。返回其 url，使用 `String` 接收。

   ```java
   String url = aliyunOSSOperator.upload(file.getBytes(), file.getOriginalFilename());
   ```

5. 用 `log.info` 打印日志 “文件上传 OSS，url：url”。

6. 返回 `Result` 并传递 url。

### 程序优化

目前 endpoint、bucketName 和 region 的信息直接写在工具类中，不便于后期维护，可以将一些需要灵活变化的参数，配置在配置文件中，然后通过 `@Value` 注解来注入外部配置的属性。

```yml
aliyun:
	OSS:
		endpoint: https://oss-cn-beijing.aliyuncs.com
		bucketName: tlias-web-management-heima
		region: cn-beijing
```

```java
@Component
public class Aliyunossoperator{
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;
    @value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.region}")
    private String region;
}
```

使用 `@Value` 注解注入配置文件的配置项，如果配置项多，注入繁琐，不便于维护管理和复用。

可以直接使用 `@ConfigurationProperties` 注解将整个配置注入并封装在一个 Java Bean 中，在其他类中如果需要使用就直接将 bean 对象注入，然后调用其 get set 方法就可以了。

```java
@Data
@Component
@ConfigurationProperties(prefix = "aliyun.oss")
public class AliyunosSProperties {
    private String endpoint;
    private String bucketName;
    private String region;
}
```

```java
@Component
public class Aliyunossoperator {
    @Autowired
    private AliyunosSProperties aliyunosSProperties;
    public String upload(byte[] content, String originalFilename) throws Exception {
        String endpoint = aliyunosSProperties.getEndpoint();
        String bucketName = aliyunOSSProperties.getBucketName();
```

## 删除员工

### 根据需求文档

删除员工数据。

### 根据接口文档

1. 请求路径：`/emps`

2. 请求方式：`DELETE`

3. 请求参数样例：`/emps?ids=1,2,3`

4. 响应数据样例：

   ```json
   {
       "code":1,
       "msg":"success",
       "data":null
   }
   ```

### 三层架构

Mapper：执行两条 SQL。

Service：1. 批量删除员工基本信息。2. 批量删除员工的工作经历信息。

Controller：1. 接收请求（ID）。2. 调用 Service。3. 相应结果。

### 开发步骤

1. 准备 SQL。

   ```sql
   delete from emp where id in(1, 2, 3);
   ```

   ```sql
   delete from emp_expr where emp_id in (1, 2, 3);
   ```

2. 编写 Controller，这里使用 List 集合接收前端传递的信息。定义 `public` 的 `delete`，返回值为 `Result`，参数用 `List` 接收，属性名为 `ids`，泛型为 `Integer`。添加 `RequestParam` 注解。

3. 使用 `log.info` 打印日志“删除员工：ids”。

4. 调用 Service 的方法，定义方法名为 `delete`，并传递 `ids`。

5. 返回 `Result`。

6. 方法上 `Alt + Enter` 定义方法，进入实现类，实现方法。

7. 分别调用 `emp` 和 `empExpr` Mapper 的方法，`emp` 的方法定义为 `deleteByIds`，并传递 `ids`， `empExpr` 的方法定义为 `deleteByEmpIds`，并传递 `ids`。

8. 方法上添加事务控制，即添加 `Transactional` 注解，注解内添加属性 `rollbackFor = {Exception.class}`，表示出现异常都需要回滚数据。

9. 方法上 `Alt + Enter` 创建方法。

10. `deleteByIds` 的 SQL 定义在 xml 中，具体 SQL 如下：

    ```xml
    <delete id = "deleteByIds">
        delete from emp where id in
        <foreach collection = "ids" item = "id" separator = "," open = "(" close = ")">
        	#{id}
        </foreach>
    </delete>
    ```

11. `deleteByEmpIds` 的 SQL 也定义在 xml 中，在定义 SQL 前我们更改 Mapper 中的参数名为 `empIds`，和 `Ids` 做区分，具体 SQL 如下：

    ```xml
    <delete id = "deleteByEmpIds">
        delete from emp_expr where emp_id in
        <foreach collection = "empIds" item = "empId" separator = "," open = "(" close = ")">
        	#{empId}
        </foreach>
    </delete>
    ```

    
