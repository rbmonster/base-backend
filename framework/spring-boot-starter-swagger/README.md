# Swagger basic

- 访问所有暴露节点 K- V
http://localhost:8080/v2/api-docs

- 官方UI访问地址
http://localhost:8080/swagger-ui/


相关依赖
```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>3.0.0</version>
</dependency>
```

- 相关介绍：https://www.baeldung.com/swagger-2-documentation-for-spring-rest-api

## Knife4j 
Knife4j ：Swagger 生成 Api 文档的增强解决方案，前身是 swagger-bootstrap-ui 。官方文档：https://xiaoym.gitee.io/knife4j/documentation/ 。
- 本地访问地址： http://localhost:8080/doc.html

- 项目地址：https://xiaoym.gitee.io/knife4j/

## YApi 项目独立的增强方案
YApi ：YApi 是一个可本地部署的、打通前后端及 QA 的、可视化的接口管理平台。可以帮助我们让 swagger 页面的体验更加友好，目前很多大公司都在使用这个开源工具。
- 项目地址：https://github.com/YMFE/yapi 。

## 相关文章
- https://mp.weixin.qq.com/s/Su33QhBRKnzwuNgTdriaTA