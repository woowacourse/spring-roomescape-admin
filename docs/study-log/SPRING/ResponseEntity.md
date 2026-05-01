## 개념
`ResponseEntity`는 Spring MVC에서 HTTP 요청에 대한 응답을 제어하는 데 사용하는 클래스이다. 이 클래스는 `Response Body`, `Header`, `Status Code`를 포함할 수 있어, 세밀한 응답 관리를 가능하게 한다. `ResponseEntity`는 `@RestController`와 함께 사용되어 JSON 또는 XML과 같은 RESTful 서비스를 제공하는데 이상적이다.

### 주요 특징 및 기능

- **Response Status Code 설정**: `ResponseEntity` 객체를 생성할 때, HTTP Status Code를 명시적으로 제공할 수 있다. 이는 API의 Response를 보다 명확하게 표현할 수 있게 해주며, 클라이언트에게 유용한 정보를 제공한다.
    
- **Response Header 조작**: Response에 필요한 HTTP Header를 추가하거나 변경할 수 있다. 이를 통해 캐싱 정책, 내용 유형, CORS Header 설정 등을 세밀하게 조정할 수 있다.
    
- **Response Body 관리**: `ResponseEntity`는 Response Body에 직접 객체를 설정할 수 있으며, Spring의 메시지 컨버터를 통해 객체를 JSON이나 XML 등의 형식으로 자동 변환할 수 있다.

### 사용 방법
```java
ResponseEntity<T> responseEntity = new ResponseEntity<>(body, headers, status);
```
- T : Response Body 타입
- body : Response Body 객체
- headers : 설정할 HTTP header
- status : HTTP Response Status Code

## 상세 내용
### ok()
- ok() 메서드는 HTTP 200 Status Code와 함께 Response를 생성
```java
@PostMapping("/reservations")  
public ResponseEntity<ReservationResponse> addReservation(  
        @RequestBody ReservationRequest request  
) {  
    Reservation reservation = new Reservation(  
            index.getAndIncrement(),  
            request.name(),  
            request.date(),  
            request.time()  
    );  
    reservations.add(reservation);  
    return ResponseEntity.ok(ReservationResponse.from(reservation));
```

### status()
- status() 메소드를 사용하면 특정 HTTP Status Code를 직접 설정 가능. 더 유연한 Response 처리가 필요할 때 유용함
```java
@PostMapping("/reservations")  
public ResponseEntity<ReservationResponse> addReservation(  
        @RequestBody ReservationRequest request  
) {  
    Reservation reservation = new Reservation(  
            index.getAndIncrement(),  
            request.name(),  
            request.date(),  
            request.time()  
    );  
    reservations.add(reservation);  
    return ResponseEntity.status(HttpStatus.OK).body(ReservationResponse.from(reservation));
```

### notFound()
- notFound() 메서드는 HTTP 404 Status Code를 설정할 때 사용함. 자원을 찾을 수 없을 때 클라이언트에게 명확하게 알려줄 수 있음
```java
@GetMapping("/users/{id}")
public ResponseEntity<User> getUserById(
	@PathVariable String id
) {
  User user = userService.findById(id);
  if (user == null) {
    return ResponseEntity.notFound().build();
  }
  return ResponseEntity.ok(user);
}
```

### badRequest()

- `badRequest()` 메서드는 HTTP 400 Status Code와 함께 Response를 생성할 때 사용된다. 입력 데이터에 오류가 있을 때 유용하게 사용할 수 있다.

```java
@PostMapping("/update")
public ResponseEntity<String> updateUser(@RequestBody User user) {
  if (user.getId() == null) {
    return ResponseEntity.badRequest().body("USer ID is required");
  }
  userService.update(user);
  return ResponseEntity.ok("User updated successfully");
}
```

### created()

- `created` 메서드는 HTTP 201 Status Code와 함께 Response를 생성할 때 사용된다. 새로운 resource가 성공적으로 생성되었을 때 이 메서드를 사용할 수 있다.

```java
@PostMapping("/users")
public ResponseEntity<User> addUser(@RequestBody User user) {
  User createdUser = userService.addUser(user);
  URI location = ServletUriComponentsBuilder.fromCurrentRequest()
    .path("/{id}")
    .buildAndExpand(createdUser.getId())
    .toUri();
  return ResponseEntity.created(location).body(createdUser);
}
```

### noContent()

- `noContent()` 메서드는 HTTP 204 Status Code와 함께 Response를 생성할 때 사용된다. 주로 삭제 요청이나 업데이트 후 body가 필요 없는 경우에 사용된다.

```
@DeleteMapping("/users/{id}")
public ResponseEntity<Void> deleteUser(@PathVariable String id) {
  userService.delete(id);
  return ResponseEntity.noContent().build();
}
```

### accepted()

- `accepted()` 메서드는 HTTP 202 Status Code와 함께 Response를 생성할 때 사용된다. 처리가 아직 완료되지 않았지만 시작되었음을 클라이언트에 알릴 때 사용할 수 있다.

```java
@PostMapping("/process")
public ResponseEntity<Void> processRequest(@RequestBody RequestData data) {
  processService.startProcess(data);
  return ResponseEntity.accepted().build();
}
```