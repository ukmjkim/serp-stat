# SERP STAT RESTAPI


## serp-stat-restapi : Spring 4 MVC REST API

## calling rest api by curl
```
Mijungs-MBP-2:serp-stat-restapi MijungKimMacPro$ curl -i -H "Accept: application/json" -H "Content-Type: application/json" "http://localhost:8080/serp-stat-restapi/user/"
HTTP/1.1 200 OK
Server: Apache-Coyote/1.1
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Mon, 16 Oct 2017 00:47:45 GMT

[{"id":1,"login":"mjkim","password":"mjkim","niceName":"","email":"mjkim@gmail.com","createdAt":null,"updatedAt":null,"deletedAt":null,"deleted":0,"userAPIs":[{"id":1,"apiKey":"abc","ips":"","count":0,"apiLimit":1000,"createdAt":null,"updatedAt":null,"deleted":0}],"sites":[{"id":1,"title":"title1","url":"url1","tracking":1,"dropWWWPrefix":1,"dropDirectories":0,"contactEmail":null,"treatNonRankingAs":null,"nonRankingValue":null,"createdAt":1508050800000,"updatedAt":1508050800000,"deleted":0}]}]Mijungs-MBP-2:serp-stat-restapi MijungKimMacPro$
```



## Hibernate ManyToOne Infinite Loop
User (1) -> (M) UserAPI
1. Don't use user entity in toString, equals, and hashcode
2. Remove the getter of User in UserAPI (owning class)
    JSON format returns like {"user":1 ... userapi: ["id":1, ... "user" [...] ==> infinite loop.

## Exception Handling in Spring REST
1. RestResponseEntityExceptionHandler controller package
2. ExceptionInfo model
3. create customized exception class in exception package
    * UserNotFoundException
4. Throw exception in your controller
```java
public ResponseEntity<User> getUser(@PathVariable("id") long id) throws UserNotFoundException {
    ....
    throw new UserNotFoundException("User with id not found");
    ....
```
5. Add handler with @ExceptionHandler
```java
  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ExceptionInfo> handleException(Exception ex) {
    ExceptionInfo error = new ExceptionInfo();
    error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
    error.setMessage(ex.getMessage());
    return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
  }
```

## CORS
https://www.concretepage.com/spring-4/spring-4-rest-cors-integration-using-crossorigin-annotation-xml-filter-example


## File Upload
1. pom.xml to solve tomcat loading error: org/apache/commons/fileupload/FileItemFactory
```java
<dependency>
   <groupId>commons-fileupload</groupId>
   <artifactId>commons-fileupload</artifactId>
   <version>1.3.3</version>
   <scope>runtime</scope>
</dependency>
```
2. multipartResolver in App Configuration
```java
@Bean(name="multipartResolver")
public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
    resolver.setMaxUploadSize(5242880); // set the size limit
    return resolver;
}
```
3. Controller (refer to KeywordRestController)
4. testing
```bash
$ curl -F file=@"//Users//MijungKimMacPro//keyword.xml" http://localhost:8080/serp-stat-restapi/keyword/upload/
```


## Bulk Insertion Reference Chain Issue

