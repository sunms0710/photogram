# 포토그램

### 의존성

- Sring Boot DevTools
- Lombok
- Spring Data JPA
- MariaDB Driver
- Spring Security
- Spring Web
- oauth2-client

```xml
<!-- 시큐리티 태그 라이브러리 -->
implementation 'org.springframework.security:spring-security-taglibs'

<!-- JSP 템플릿 엔진 -->
implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'

<!-- JSTL -->
implementation 'javax.servlet:jstl'
```

### 데이터베이스

```sql
create user 'seon'@'%' identified by 'seon1234';
GRANT ALL PRIVILEGES ON *.* TO 'seon'@'%';
create database photogram;
```

### yml 설정

```yml
server.port=8080
server.servlet.context-path=/
server.servlet.encoding.charset=utf-8
server.servlet.encoding.enabled=true

spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
spring.datasource.url=jdbc:mariadb://localhost:3307/photogram?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false
spring.datasource.username=seon
spring.datasource.password=seon1234

spring.jpa.open-in-view=true
spring.jpa.hibernate.ddl-auto=create
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.show-sql=true

spring.servlet.multipart.enabled=true
spring.servlet.multipart.max-file-size=2MB

spring.security.user.name=test
spring.security.user.password=1234
```

### 태그라이브러리

```jsp
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
```
