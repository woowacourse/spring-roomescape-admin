### 정의
Spring DI는 객체가 필요한 의존 객체를 직접 만들거나 찾지 않고, Spring IoC 컨테이너가 Bean을 생성할 때 의존성을 주입해주는 방식임.

| 방식     | 의미                      |
| ------ | ----------------------- |
| 생성자 주입 | 생성자 인자로 의존성을 받음         |
| 세터 주입  | 객체 생성 후 setter로 의존성을 받음 |
#### Dependency에 대한 개념
의존성이란 한 객체거 다른 객체를 사용할 때 의존관계(의존성)가 있음을 의미함.

1. 클래스 간 높은 결합도
2. 객체간의 관계가 아닌 클래스 간의 관계를 맺음
	- 올바른 OOP 설계라면 클래스가 앙닌 객체 간의 협력을 통한 설계를 해야 함. 관계가 맺어진 클래스는 구체 클래스를 아지 못하더라도 인터페이스를 통해 협력할 수 있어야 함.

### 방법
#### Constructor-based dependency injection
첫번째는 '생성자 주입'이다.
```java
public class SimpleMovieLister {

	// SimpleMovieLister는 MovieFinder에 의존성이 있음
	private final MovieFinder movieFinder;

    // 생성자: 스프링 컨테이너를 통해 MovieFinder를 주입 받음
	public SimpleMovieLister(MovieFinder movieFinder) {
		this.movieFinder = movieFinder;
	}

	// business logic that actually uses the injected MovieFinder is omitted...
}
```

생성자 주입은 생성자를 통해 의존 관계를 주입한다.
생성자의 호출 시점에 1회 호출되는 것이 보장됨으로써 주입받은 객체가 변하지 않거나, 객체의 주입을 강제할 수 있게 한다.

**생성자 주입 사용시 장점**
- 순환 참조 방지
    - 순환 참조는 A가 B를 참조하고, B가 A를 참조하는 경우에 발생하는 문제이다.
    - 생성자 주입은 먼저 빈을 생성하지 않고 주입하려는 빈을 찾는다. 그래서 어플리케이션 기동시에 에러가 발생하여 금방 문제를 찾을 수 있다.
- `final` 선언이 가능
    - 생성자 주입 시, 의존성 주입이 클래스 인스턴스화 중에 시작되므로 final을 선언할 수 있다. 따라서 객체를 변경이 불가능하게 할 수 있다.
- 단위 테스트 코드 작성 용이
    - 스프링 컨테이너 도움 없이 테스트 코드를 더 편리하게 작성할 수 있다.

#### Setter-based dependency injection
두번째는 '수정자 주입'이다.

```java
public class SimpleMovieLister {

	// SimpleMovieLister는 MovieFinder에 의존성이 있음
	private MovieFinder movieFinder;

	// 수정자 메서드: 스프링 컨테이너를 통해 MovieFinder를 주입 받음
	public void setMovieFinder(MovieFinder movieFinder) {
		this.movieFinder = movieFinder;
	}

	// business logic that actually uses the injected MovieFinder is omitted...
}
```

수정자 주입은 필드 값을 변경하는 setter를 통해 의존 관계를 주입하는 방법이다.

생성자 주입과는 다르게 주입받는 객체가 변경될 가능성이 있는 경우 사용하게 된다.  
대신 `@Autowired`를 통해 의존성 주입을 강제할 수 있다.

>수정자 주입 잘 이해가 안되긴함.
>객체를 먼저 만들고 나중에 의존성을 넣는 방식임.
```java
@Component
public class A {

    private B b;

    @Autowired
    public void setB(B b) {
        this.b = b;
    }
}
```
>Spring 동작:
> 1. A 객체 먼저 생성
> 2. B Bean 찾음
> 3. `setB()` 호출해서 주입

#### Field dependency injection
공식 문서에서는 소개하고 있지 않지만 '필드 주입'도 DI 대표적인 방법 중 하나이다.

```java
public class SimpleMovieLister {

	@Autowired
	private MovieFinder movieFinder;

}
```

필드 주입은 필드에 바로 의존 관계를 주입하는 방법이다.

코드가 간결해지는 장점이 있지만 외부에서 접근이 불가능하다는 단점이 존재하는데 테스트 코드 작성시 필드의 객체를 수정할 수 없기 때문이다.  
또 DI 프레임워크가 존재해야 필드 주입이 가능하기에 사용을 지양하고 있다.

**필드 주입 장점**
- 코드 간결

**필드 주입 단점**
- unit test가 어렵다.
- `final` 선언이 불가능하다.
- 순환 의존성이 발생한 경우 디텍트 하지 못한다. (순환 의존성: A -> B, B-> A)

#### ### 생성자 주입 vs 수정자 주입
스프링에서는 '생성자 주입'을 DI의 방법으로 추천한다고 많이 들었다.

의존성 주입을 강제해야 하는 필드는 '생성자 주입'을 사용하고 선택적으로 하고 싶다면 '수정자 주입'을 사용하면 된다. 대신 setter에 `@Autowired`를 통해 의존성 주입을 강제할 수 있다. 하지만 인수의 계획적인 검증을 포함하는 생성자 주입이 더 좋다.

스프링은 '생정자 주입'을 지지하는데, 애플리케이션 구성 요소를 불변 객체로 구현하고 의존성이 null이 아님을 보장하기 때문이다. 게다가 항상 완전히 초기화된 상태로 클라이언트 코드에 반환된다. 참고로 많은 수의 생성자 인수는 나쁜 code smell이며, 클래스에 너무 많은 책임이 있을 가능성이 있어 문제를 적절히 분리하기 위해 리팩토링 해야 한다.

'수정자 주입'은 합리적인 기본값을 할당할 수 있는 클래스에 선택적 의존성을 사용해야 한다. 그렇지 않으면 의존성을 사용하는 모든 코드에서 null 체크를 해야한다. 수정자 주입의 한 가지 장점은 setter method가 클래스의 객체를 재구성 혹은 재주입할 수 있도록 하는 것이다.

특정 클래스에 적합한 DI를 사용해라. 가끔 아무런 소스가 없는 제 3자의 클래스를 다뤄야할 때가 오면 선택은 너에게 달려있다. 레거시 클래스는 아마 setter에 대한 접근이 어려울 것이고, '생성자 주입'만이 DI를 할 수 있는 유일한 방법이다.

>적긴했지만 왜 생성자 주입을 추천하는지 간단하게 얘기해줘
>객체를 항상 정상 상태로 강제하기 때문임.
> 
> 그니까 나중에 세터방식으로 하면 나중에 주입하는 거기 때문에 잘못 들어갈 수도 있어서 그런거임?
> 나중에 주입이라서 잘못되거나 누락될 가능성이 있어서 그럼. 그니까 생성자 때 부터 하면 처음부터 강제하면서 안전하기 때문임.