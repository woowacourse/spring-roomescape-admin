현재 문제 상황 : @RequestBody를 쓰는데 @RequestBody Reservation request 이러면 Reservation 객체가 매핑이되서 생성되는건데 ID가 없는데도 왜 되고, RequestBody 어노테이션의 역할은 뭐지?

## 개요
클라이언트가 서버에게 보내는 메시지를 요청(Request)이라고 하고, 반대로 서버가 클라이언트에게 보내는 메시지를 응답(Response)이라고 부른다.

우리가 사용하는 많은 웹사이트는 페이지를 새로 고치지 않고도 데이터를 주고받으며 자연스럽게 동작한다.
이런 방식을 바로`비동기 통신`라고 한다.

비동기 통신을 하려면 클라이언트는 서버에게 요청을 보낼 때 데이터를 'Body'라는 부분에 담아서 보내야 한다.
서버도 클라이언트에게 응답할 때 마찬가지로 'Body'에 데이터를 담아서 보내준다.

이렇게 Body에 담아서 보내는 데이터를 각각 요청 Body, 응답 Body이라고 한다.  
'Body'에 담길 수 있는 데이터 형식은 다양하지만, 가장 널리 사용되는 것은 JSON이라는 형식이다.
즉, 비동기 방식에서는 클라이언트와 서버가 주로 JSON 형식의 데이터를 주고받는다.

스프링을 사용할 때도 이 방식이 자주 사용된다.
클라이언트가 JSON이나 XML 등의 데이터를 보내면, 스프링이 자동으로 이 데이터를 자바 객체로 변환해서 처리할 수 있도록 도와준다.

  
여기서 중요한 역할을 하는 것이 바로 `@RequestBody`와 `@ResponseBody`라는 어노테이션이다.
- `@RequestBody`는 클라이언트에서 보낸 HTTP 요청 Body(JSON 등)을 자바 객체로 바꿔준다.
- `@ResponseBody`는 자바 객체를 다시 HTTP 응답 Body(JSON 등)으로 바꿔서 클라이언트에 보내준다.

## 개념
@RequestBody는 **HttpMessageConverter**가 **HTTP Request Body** 내의 데이터를 객체로 변환(역직렬화)하도록 시키는 애노테이션이다.
@Valid를 붙이면 검증을 할 수 있고, 실패 시 **MethodArgumentNotValidException**을 던진다.  
**application/json** 타입의 경우 **MappingJackson2HttpMessageConverter**가 데이터를 객체로 역직렬화 해준다.  
MappingJackson2HttpMessageConverter는 **ObjectMapper**를 사용하는데, **프로퍼티**를 이용하기 때문에 **getter / setter 둘 중 하나**만 있으면 된다

### 흐름도
![[Pasted image 20260428161301.png]]

- 이전에 다뤘던 "**핸들러 어댑터가 컨트롤러로 요청을 위임함**" 과정에 집중

-> 바로 여기서 @RequestBody가 일을 시작함

#### 작동원리
`@RequsetBody`는 HTTP 요청으로 같이 넘어오는 Header의 Content-type을 보고 어떤 `Converter`를 사용할지 정하기에 Content-type을 반드시 명시해야 한다.   

(Content-type은 따로 default 값이 없다. 그래서 프론트엔드와 협업할 때 종종 요청에 Content-Type을 명시하지 않아 에러가 발생하기도 한다)

> Content-Type이 뭐지?
> HTTP 요청이나 응답이 주고받는 데이터가 어떤 형식인지 명시하는 헤더이다.
> 
> **📚 자주 사용하는 Content-type 종류**  
> - application/json : JSON 데이터 전송  
> - application/x-www-form-urlencoded : 폼 데이터 전송 (HTML 폼 형식)  
> - application/xml : XML 데이터 전송  
> - text/plain : 단순 문자열 데이터를 보낼 때 사용  
> - multipart/form-data : 파일 전송 (spring에서는 주로 @RequestPart와 함꼐 사용)  
> - text/html : HTML 전송

일단 자세히 알아보기 전에 요약하자면 스프링에서는 위에서 말한 작업을 **HttpMessageConverter**라는 친구가 자동으로 처리해준다.

기본적으로는 **Jackson**이라는 라이브러리를 사용해서 JSON -> 객체(역직렬화) / 객체 -> JSON(직렬화)로 변환을 해준다.

> 이제 이 친구의 역할을 알겠는데, 내가 도메인에서 Long id를 설정 했는데, POST 값에는 ID가 없이도 매핑이 되던거 같은데 왜 그런거야? 
> JSON에 `id`가 없어도 객체 생성은 가능하고, 없는 필드는 기본값인 `null`로 들어간다.
> 
> 그리고 이 친구는 객체를 생성하는 것이 아니라 매핑하는건가..?
> @RequestBody 는 JSON을 받아서 새 객체를 만들고 필드 값을 채움(역직렬화)까지 한 번에 수행 가능함.
> 
> 근데 객체를 생성하지말고 DTO로 매핑하는게 더 나은거 아닌가?
> 맞음. `@RequestBody`는 `Entity`가 아니라 **Request DTO로 받는 게 더 낫다.**
> 
> 근데 더 나은 이유는 뭐지?
> DTO를 쓰는 이유는 외부(API)와 내부(Domain)를 분리해서 변경 영향과 보안 리스크를 막기 위함임.
> DTO로 받으면 데이터 전달이라는 책임만 가짐
> 
> Entity가 API 스펙에 종속됨 → 설계 깨짐 이해가 안됨
> Entity를 그대로 API에 쓰면 외부 요구(JSON 구조)가 바뀔 때 내부 도메인 구조까지 강제로 바뀌는 상태가 됨.
> 
> 그니까 즉 외부(JSON)이 바뀌게 되면 Entity를 직접 생성해서 비교하기 때문에 외부가 바뀌면 내부도 바뀌어야해서 DTO를 사용하면 DTO만 수정하면 되서 괜찮은건가?
> **외부(JSON) 변화가 내부(Entity)에 전파되지 않도록 DTO로 차단하는 것**이다.
> 
> DTO가 변화되는건 상관없는거임?
> DTO는 변해도 되는 계층이고, 대신 API 계약(호환성)만 깨지지 않게 관리하면 됨.