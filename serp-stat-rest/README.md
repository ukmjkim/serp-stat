# SERP-STAT-REST (Spring Boot 2.0.0 migration)

## mysql & hibernate auto config (converted from im-memory database H2)
```
$ cat application.properties

spring.datasource.url=jdbc:mysql://serpstat:3306/serpstat
spring.datasource.username=serpstat
spring.datasource.password=statserp
spring.datasource.tomcat.max-wait=20000
spring.datasource.tomcat.max-active=50
spring.datasource.tomcat.max-idle=20
spring.datasource.tomcat.min-idle=15

spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.properties.hibernate.id.new_generator_mappings = false
spring.jpa.properties.hibernate.show_sql = true
spring.jpa.properties.hibernate.format_sql = true
```

## JPA infinite loop (using lombok)
get rid of toString, equals, and hashCode

Before
```
@Entity
@Table(name = "USER_APIS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class UserAPI {
```
After
```
@Entity
@Table(name = "USER_APIS")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class UserAPI {
```

## Actuator 404 pageNotFound
1. Actuator endpoints are moved to /application/info
2. Properties are Deprecated: Endpoint identifier is no longer customizable.
3. While health is non sensitive in 1.x, this has changed in 2.x with the new status endpoint and the example above reflects those new defaults.
4. The HAL Browser (for Spring Data REST): http://localhost:8080/browser/index.html#/

https://github.com/spring-projects/spring-boot/wiki/Spring-Boot-2.0-Migration-Guide
https://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html
https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#production-ready-endpoints

```
endpoints.beans.id = springbeans  --> deprecated
endpoints.beans.sensitive = false  --> deprecated
endpoints.beans.enabled = true

management.port=8081
management.address=127.0.0.1
management.security.enabled=false  --> deprecated
```

I have to enable all endpoints for actuator
```
endpoints.info.enabled=true
endpoints.beans.enabled=true
endpoints.default.enabled=true
endpoints.env.enabled=true
endpoints.health.enabled=true
endpoints.metrics.enabled=true
endpoints.loggers.enabled=true
endpoints.mappings.enabled=true
endpoints.trace.enabled=true
endpoints.threaddump.enabled=true
endpoints.customhealth.enabled=true
```

```
http://localhost:8080/application
http://localhost:8080/application/beans
http://localhost:8080/application/autoconfig
http://localhost:8080/application/env
http://localhost:8080/application/mappings
http://localhost:8080/application/metrics
http://localhost:8080/application/trace
http://localhost:8080/application/health
http://localhost:8080/application/status
http://localhost:8080/application/info
http://localhost:8080/application/threaddump
```

## Actuator - Found two extensions for HealthEndpoint

http://localhost:8080/application/customhealth

```
Caused by: org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'webEndpointServletHandlerMapping' defined in class path resource [org/springframework/boot/actuate/autoconfigure/endpoint/web/servlet/WebMvcEndpointManagementContextConfiguration.class]: Bean instantiation via factory method failed; nested exception is org.springframework.beans.BeanInstantiationException: Failed to instantiate [org.springframework.boot.actuate.endpoint.web.servlet.WebMvcEndpointHandlerMapping]: Factory method 'webEndpointServletHandlerMapping' threw exception; nested exception is java.lang.IllegalStateException: Found two extensions for the same endpoint 'org.springframework.boot.actuate.health.HealthEndpoint': org.springframework.boot.actuate.health.HealthWebEndpointExtension and com.serpstat.rest.actuator.CustomHealthWebEndpointExtension
```

