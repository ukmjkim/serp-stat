# SERP STAT RESTAPI
* serp-stat-restapi : Spring 4 MVC REST API

* Hibernate ManyToOne Infinite Loop
User (1) -> (M) UserAPI
1. Don't use user entity in toString, equals, and hashcode
2. Remove the getter of User in UserAPI (owning class)
    JSON format returns like {"user":1 ... userapi: ["id":1, ... "user" [...] ==> infinite loop.

* Exception Handling in Spring REST
1. RestResponseEntityExceptionHandler controller package
2. ExceptionInfo model
3. create customized exception class in excpetion package
    * UserNotFoundException
4. Throw exception in your controller

public ResponseEntity<User> getUser(@PathVariable("id") long id) throws UserNotFoundException {
    ....
    throw new UserNotFoundException("User with id not found");
    ....

5. Add handler with @ExceptionHandler

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<ExceptionInfo> handleException(Exception ex) {
    ExceptionInfo error = new ExceptionInfo();
    error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
    error.setMessage(ex.getMessage());
    return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
  }


*

