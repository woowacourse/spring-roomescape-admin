---
필수 개념 : 계층 분리
목표 : 현재 controller에 모여있는 책임을 계층으로 분리한다.
내가 아는 것 : Spring이 관리하는 객체가 빈이다.
레이어드 계층에서는 controller - service - repository - domain 으로 나뉜다.

1. 스프링은 왜 ReservationController, Service, Repository를 직접 new로 만들지 않고 빈으로 관리하게 할까?
스프링은 객체의 생명주기를 IoC container에서 관리하게 하려고, Bean로 등록한다.
의존성 주입으로 객체와 강결합하지 않고, 필요한 의존성을 주입 받을 수 있다.
예를 들어, ReservationController는 Service의 구현 방법에 대해 몰라도 기능은 사용할 수 있다.

2. @Controller, @Service, @Repository는 단순한 이름표일까, 아니면 스프링이 객체를 관리하는 방식과 연결될까?
@Component를 meta-annotated된 애노테이션으로 구체화시킨 것들이다.
이 어노테이션들은 스프링이 클래스 경로를 스캔할 때 자동으로 탐지되도록 하기 때문에, 스프링이 객체를 관리하는 방식이다.

> Any annotation meta-annotated with @Component is considered a stereotype annotation which makes the annotated class eligible for classpath scanning.
> For example, @Service, @Controller, and @Repository are stereotype annotations.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Component.html

> @Controller serves as a specialization of @Component, allowing for implementation classes to be autodetected through classpath scanning.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/stereotype/Controller.html#:~:text=Indicates%20that%20an%20annotated%20class%20is%20a,annotation%20serves%20as%20a%20specialization%20of%20@Component.

3. 컨트롤러에서 JdbcTemplate이 사라진다는 것은, 단순히 필드 하나를 옮기는 걸까, 아니면 “의존하는 빈의 방향”이 바뀌는 걸까?
JdbcTemplate은  SQL 실행/커넥션 처리 같은 데이터 접근 중복을 줄여주는 기능을 하기 때문에, Repository에서 호출하는 것이 적절하다.
따라서, DB 접근의 책임을 Repository 게층으로 분리하는 것이 중요하다.
과제로 낸 이유는 JdbcTemplate을 하위의 책임으로 내려서 계층간의 의존성을 분명히 하려는 목적을 가진다.
```text
Controller -> Service -> Repository
Repository -> JdbcTemplate
```