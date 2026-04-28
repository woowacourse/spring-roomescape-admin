스프링 프레임워크에서 타입 컨버터(Type Converter)는 한 타입의 객체를 다른 타입으로 변환할 때 사용되는 메커니즘이다. 스프링 MVC에서는 클라이언트로부터 받은 문자열 데이터를 컨트롤러의 파라미터나 필드에 지정된 타입의 객체로 변환할 필요가 자주 있다.

예를 들어, URL 경로에서 가져온 문자열을 날짜 객체나 열거형, 심지어는 사용자 정의 타입으로 변환해야 할 때가 그러하다. 이럴 때 스프링의 타입 컨버터 기능이 유용하게 사용된다.

```java
@GetMapping("/hello-v2")
 public String helloV2(@RequestParam Integer data) {
     System.out.println("data = " + data);
     return "ok";
}
```

HTTP 쿼리 스트링으로 전달하는 data=10 부분에서 10은 숫자 10이 아니라 문자 10이다.
스프링이 제공하는 @RequestParam 을 사용하면 이 문자 10을 Integer 타입의 숫자 10으로 편리하게 받을 수 있다.

이것은 스프링이 중간에서 타입을 변환해주었기 때문이다.
이러한 예는 @ModelAttribute , @PathVariable 에서도 확인할 수 있다.

**@ModelAttribute 타입 변환 예시**
```java
@ModelAttribute UserData data
 class UserData {
   Integer data;
}
```

**@PathVariable 타입 변환 예시**
```java
// /users/{userId}
@PathVariable("userId") Integer data
```

URL 경로는 문자다. /users/10 → 여기서 10도 숫자 10이 아니라 그냥 문자 "10"이다. data를 Integer 타입으로 받을 수 있는 것도 스프링이 타입 변환을 해주기 때문이다.

### **스프링의 타입 변환 적용 예**

- 스프링 MVC 요청 파라미터 (@RequestParam , @ModelAttribute , @PathVariable)
- @Value 등으로 YML 정보 읽기
- XML에 넣은 스프링 빈 정보를 변환
- 뷰를 렌더링 할 때
  
> 그냥 자동으로 변환해주는건가?
> 등록된 규칙이 있을 때만 자동 변환해주는 매핑 도구임/
> 
> @RequestBody (JSON -> 객체)
> - Jackson이 처리
>   기본적으로 날짜/시간 변환 지원
>   
>   @RequestParam / @PathVariable
>   - Spring Converter가 처리