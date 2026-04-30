

### 1단계 : 웹 요청-응답
필수 개념 : 웹 요청-응답
목표 : 예약 관리 API를 만들고 싶다.
내가 아는 것 : 웹은 API를 통해 요청을 보내고, 응답을 한다.

1. gradle에서 웹과 관련된 의존성은 무었인가?
   spring-boot-starter-web 라이브러리이다.

> Most web applications use the spring-boot-starter-web module to get up and running quickly.
> You can also choose to build reactive web applications by using the spring-boot-starter-webflux module.
> https://docs.spring.io/spring-boot/reference/web/index.html

2. annotaion이 무엇이며, 어떤것이 있는가?
   1)연결
   annotation은 Controller와 핸들러 메서드를 연결하기 위한 것으로, 요청 파라미터를 메서드 인수로 넣어주는 기능을 제공한다.
   @RequestParam : 메서드 매개변수가 웹 요청 파라미터와 바운딩되어있어야 함을 나타내는 어노테이션
   기본 설정이 true라, 요청에서 파라미터가 없으면 에러 발생한다. 만약, 파라미터가 필수로 필요한게 아니라면 required=false를 사용한다.
   @RequestHeader : 웹 요청 파라미터 헤더를 메서드 매개변수와 바인딩하는 어노테이션
   메서드 파라미터 타입이 String이 아닐 경우 자동으로 형변환된다.
   @ResponseBody : HttpMessageConverter를 통해 메서드를 응답 body로 직렬화하는 어노테이션
   @Controller + @ResponseBody = @RestController
   @PathVariable : 메서드 파라미터가 URI 경로의 일부를 바인딩하는 어노테이션
   null을 하고 싶다면, required=false한다.

> Annotations for binding requests to controllers and handler methods as well as for binding request parameters to method arguments.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/package-summary.html

> Annotation which indicates that a method parameter should be bound to a web request parameter.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestParam.html


2)메타 데이터
어노테이션은 연결뿐 아니라 기능을 명시하는 메타 데이터 역할도 수행한다.
아래는 메타데이터 관점에서 해당 어노테이션들이 어떤 정보를 제공하는지 정리해보았습니다.
@RequestBody : 메서드 파라미터를 body 정보에서 읽어오겠다.
@PathVariable : 메서드 파라미터를 URL 경로의 일부에서 읽어오겠다.
@ResponseBody : 메서드 반환값을 응답 body로 사용하겠다.


3. API는 어떻게 만드는가?
   Controller에서 어노테이션을 통해 Http 메서드와 매핑하면 된다.

@RequestMapping : 웹의 요청을 메서드와 매핑해주는 어노테이션
클래스와 메서드 레벵에서 사용할 수 있으나, 대부분 메서드 레벨에서 @GetMapping, @PostMapping, @PutMapping, @DeleteMapping, @PatchMapping.으로 사용
→ 하나의 메서드에서 @RequestMapping 중복 선언은 안되고, 여러개의 HTTP 메서드와 매핑하고 싶다면 아래처럼 한번에 명시
```java
@RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
```

@PostMapping은 @RequestMapping(method = RequestMethod.POST)과 같다.

> Annotation for mapping web requests onto methods in request-handling classes with flexible method signatures.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/RequestMapping.html

> Specifically, @PostMapping is a composed annotation that acts as a shortcut for @RequestMapping(method = RequestMethod.POST).
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/bind/annotation/PostMapping.html


4. Controller는 무엇을 받고, 무엇을 보내야하는가?
   json과 같이 직렬화한 데이터를 주고 받는다.
   이를 controller에서 객체로 바인딩해서 주로 사용하는데, 한가지 예시로 HttpEntity를 상속 받은 RequestEntity, ResponseEntity를 사용한다.

> HttpEntity: Represents an HTTP request or response entity, consisting of headers and body.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpEntity.html

- 항상 RequestEntity, ResponseEntity를 사용하나요?
  예를 들어, GET api의 경우 body가 필요하지 않을 수 있다.
  또 다른 예로, 헤더 정보(ex.토큰 등)가 필요하지 않으면 header가 필요하지 않을수도 있다.
  그래서, 보통 @RequestBody, @RequestHeader, @RequestParam, @PathVariable처럼 필요한 값만 골라서 사용한다.
- 그럼 웹통신에서 항상 보내는 값은 뭐야?
  Http 통신은 헤더를 기본적으로 보내고, body는 필요할때 덧붙여서 직렬화한다.
  (Http 통신에서 공통적으로 사용하는 부분이라 HttpEntity가 기본 속성으로 헤더와 바디를 가지는 구나!)

5. 요청과 응답은 어디서 정의하는가?
   데이터를 body로 보내고 싶다면, dto로 객체를 정의하고 @RequestBody로 매핑하고,
   쿼리 파라미터를 사용해서 개별적으로 값을 받는다면, @RequestParam로 하나씩 정의한다.

6. RequestEntity랑 @RequestBody랑 어떤 연관이 있는가?
   @RequestBody는 웹 요청의 body만 메서드와 매핑하고 싶을 때 사용하고, RequestEntity는 웹 요청 전체를 받고 싶을 때 사용한다.
- RequestEntity는 언제 사용하는가?
  Header에서 토큰을 꺼내온다거나 하는 body외의 정보가 필요할 때 사용한다.
- RequestEntity에는 어떤 정보를 포함하는가?
  HttpEntity의 Header와 body를 상속받고, RequestEntity에서는 HTTP method와 target URL을 포함한다.
> Extension of HttpEntity that also exposes the HTTP method and the target URL.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/http/RequestEntity.html
- ResponseEntity와 @ResponseBody의 관계도 이와 유사한가?
  이와 유사하다. HttpMessageConverter를 이용해서 직렬화하는데, HttpEntity가 가진 Header와 body를 상속받고 추가로 HttpStatusCode를 포함한다.


[진행중]
1. AtomicLong은 뭘까?
