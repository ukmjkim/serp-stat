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
2. You can override endpoint in application.properties

```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.hateoas</groupId>
	<artifactId>spring-hateoas</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.data</groupId>
	<artifactId>spring-data-rest-hal-browser</artifactId>
</dependency>
```

