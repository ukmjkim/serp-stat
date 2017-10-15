# SERP STAT RESTAPI
* serp-stat-restapi : Spring 4 MVC REST API

* Hibernate ManyToOne Infinite Loop
User (1) -> (M) UserAPI
1. Don't use user entity in toString, equals, and hashcode
2. Remove the getter of User in UserAPI (owning class)
    JSON format returns like {"user":1 ... userapi: ["id":1, ... "user" [...] ==> infinite loop.


