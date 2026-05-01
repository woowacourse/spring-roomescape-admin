### AOP이란?
- AOP는 프로그램 구조에 대한 또 다른 사고 방시글 제공하여 OOP를 보완함.
- OOP에서 모듈화의 핵심 단위는 클래스인 반면, AOP에서 모듈화의 단위는 관점임.
- Spring 의 핵심 구성요소 중 하나임.
- Spring IoC 컨테이너가 AOP에 의존하지 않는 반면, AOP는 Spring Ioc를 보완하여 매우 유능한 미들웨어 솔루션을 제공함.

![[Pasted image 20260429201420.png]]

### 개념

![[Pasted image 20260429201437.png|880]]

- **Aspect**: 여러 클래스에 흩어진 관심사의 모듈화. 트랜잭션 관리는 엔터프라이즈 Java 애플리케이션에서 크로스 커팅 문제의 좋은 예입니다. Spring AOP에서 Aspect는 일반 클래스(스키마 기반 접근) 또는 `@Aspect` 어노테이션으로 주석이 달린 일반 클래스(@AspectJ 스타일)를 사용하여 구현됩니다.
- **Join point**: 메소드 실행이나 예외 처리와 같은 프로그램 실행 중 포인트. Spring AOP에서 조인 포인트는 항상 메소드 실행을 나타냅니다.
- **Advice**: 특정 조인 포인트에서 aspect에 의해 취해진 조치입니다. 다양한 유형의 advice에는 "around", "before" 및 "after" advice가 포함됩니다. Spring을 포함한 많은 AOP 프레임워크는 advice를 인터셉터로 모델링하고 조인 포인트 주변에 인터셉터 체인을 유지합니다.
- **Pointcut**: 조인 포인트와 일치하는 술어입니다. Advice는 pointcut 표현식과 연관되며 pointcut과 일치하는 모든 조인 포인트에서 실행됩니다(예: 특정 이름의 메소드 실행). pointcut 표현식에 의해 매칭되는 조인 포인트의 개념은 AOP의 핵심이고 Spring은 기본적으로 AspectJ pointcut 표현식 언어를 사용합니다.
- **Introduction**: 타입을 대신하여 추가적인 메소드나 필드를 선언합니다. Spring AOP를 사용하면 advice된 객체에 새로운 인터페이스(및 해당 구현)를 도입할 수 있습니다.
- **Target object**: 하나 이상의 aspect에 의해 advice되는 객체입니다. "advised object"라고도 합니다. Spring AOP는 런타임 프록시를 사용하여 구현되므로 이 객체는 항상 프록시 객체입니다.
- **AOP proxy**: aspect contracts(메소드 실행에 advice 등)를 구현하기 위해 AOP 프레임워크에 의해 생성된 객체입니다. Spring Framework에서 AOP 프록시는 JDK 동적 프록시 또는 CGLIB 프록시입니다.
- **Weaving**: aspect를 다른 애플리케이션 유형 또는 객체와 연결하여 advice된 객체를 생성합니다. 이것은 컴파일 시간, 로드 시간 또는 런타임에 수행할 수 있습니다. Spring AOP는 다른 순수 Java AOP 프레임워크와 마찬가지로 런타임에 weaving을 수행합니다.

Spring AOP에는 다음과 같은 advice가 포함됩니다.
- **Before advice**: 조인 포인트 이전에 실행되지만 조인 포인트로 진행하는 실행 흐름을 방지하는 기능이 없는 advice입니다(예외가 발생하지 않는 한).
- **After returning advice**: 조인 포인트가 정상적으로 완료된 후 실행할 advice입니다(예: 메서드가 예외를 throw 하지 않고 return 되는 경우).
- **After throwing advice**: 예외를 throw하여 메서드가 종료되는 경우 실행할 advice입니다.
- **After (finally) advice**: 조인 포인트가 종료되는 수단(정상 또는 예외 반환)에 관계없이 실행할 advice입니다.
- **Around advice**: 메소드 호출과 같은 조인 포인트를 둘러싸는 advice입니다. 이것은 가장 강력한 종류의 advice입니다. around advice는 메서드 호출 전후에 사용자 정의 동작을 수행할 수 있습니다. 또한 자체 return 값을 반환하거나 예외를 throw하여 조인 지점으로 진행할지 또는 권장된 메서드 실행을 단축할지 여부를 선택할 책임이 있습니다.

> Aspect 가 잘 모르겠음.
> 핵심 로직 실행 전/후에 공통 작업을 자동으로 수행하는 거임
> 
> @Aspect를 하면 @Transactional이랑 같은거임?
> `@Aspect`는 AOP 정의용이고, `@Transactional`은 “트랜잭션 Aspect를 적용하라”는 사용용 어노테이션임.
> 
> 내가 Aspect을 정의하거나 그러지는 않는건가 보통?
> 필요할 때 쓰긴 함.
> 
> 크로스 커팅 문제는 뭐지?
> 여러 계층/클래스에 공통 코드가 중복으로 퍼지는 문제임.
> 
> @Aspect 어노테이션이 뭐지?
> 추후 공부 예정
> 
> 약간 메서드 하나마다 로그를 딸 때 그 전에 시작된다는건가?
> 맞음, 메서드 실행 “직전/직후”에 자동으로 끼어들어서 로그 찍는 구조
> 
> Aspect안에 뭐가 있음 로그 찍는거 밖에 없는거임?
> **“메서드 실행 흐름에 끼어들어서 원하는 로직 수행”**
>넣을 수 있는 것:
>- 트랜잭션 처리 (`@Transactional`)
>- 권한 체크 (인증/인가)
>- 캐시 처리 (`@Cacheable`)
>- 성능 측정 (실행 시간)
>- 예외 변환/처리
>- API 호출 제한 (rate limit)
>  
>  내가 이해한 Aspect는 결국 함수가 실행되기 전에 실행해야하는 로직이 있으면 그걸 해준다는건가? 실행 되기 전마다?
>  거의 맞지만 반만 맞음 — “실행 전만”이 아니라 “전/후/전체 감싸기 모두 가능”
>  
>  왜 프록시로 구현하는지 보면 이해 된다는데 좀있다 할게

### Spring AOP 역량 및 목표
- Spring AOP는 **순수 자바로 구현**되었기 때문에 특별한 컴파일 과정이 필요하지 않습니다.
- Spring AOP는 클래스 로더 계층 구조를 제어할 필요가 없으므로 서블릿 컨테이너 또는 애플리케이션 서버에서 사용하기에 적합합니다.
- Spring AOP는 **현재 메소드 실행 조인 포인트만 지원**합니다(Spring Bean에서 메소드 실행 권장).
- 필드 액세스를 advice하고 조인 포인트를 업데이트해야 하는 경우 **AspectJ**와 같은 언어를 고려해야 합니다.
- AOP에 대한 Spring AOP의 접근 방식은 대부분의 다른 AOP 프레임워크의 접근 방식과 다릅니다.
- 목표는 가장 완전한 AOP 구현을 제공하는 것이 아니고 **AOP 구현과 Spring IoC 간의 긴밀한 통합을 제공**하여 엔터프라이즈 애플리케이션의 일반적인 문제를 해결하는 데 도움이 되는 것입니다.

### AOP 적용 방법
위빙은 대상 객체의 생애 중 수행되는 시점에 따라 아래 3가지로 나뉠 수 있다.  
**1. 컴파일 타임 위빙(Compile time weaving = CTW)**  
타겟 클래스가 컴파일될 때 aspect가 위빙되며 별도의 컴파일러가 필요하다. AspectJ의 위빙 컴파일러는 이러한 목적으로 사용된다.  

**2. 로드 타임 위빙(Load time weaving = LTW)**  
클래스가 JVM에 적재될 때 aspect가 위빙된다. 이를 위해서는 애플리케이션에서 사용되기 전에 타켓 클래스의 바이트코드를 enhance하는 특별한 ClassLoader가 필요하다. AspectJ의 로드시간 위빙 기능을 사용하면 클래스로드시간에 위빙된다.  

**3. 실행 시간 위빙(Runtime Weaving = RTW)**  
애플리케이션 실행 중에 aspect가 위빙된다. 보통 타겟 객체에 호출을 위임하는 구조의 프록시 객체를, 위빙 중에 AOP 컨테이너가 동적으로 만들어낸다. 스프링 자체 AOP의 aspect는 실시간 위빙을 사용한다.

### @AspectJ 어노테이션 활용

#### 1. @AspectJ 를 사용하기 위한 설정

@AsjpectJ의 지원을 허용하는 것이 가장 첫 번째 단계이다. 설정 파일에 다음과 같이 `@EnableAspectJAutoProxy` 어노테이션을 활용하여 허용할 수 있다.

```null
@Configuration
@EnableAspectJAutoProxy
public class AppConfig {
}
```

#### 2. Aspect 구현하기

Aspect는 앞서 용어 정리에서 언급한 듯이 Advice와 Pointcut으로 구성된다. 우선 Advice의 경우 아래처럼 5개의 어노테이션을 지원한다.

| 어노테이션           | 설명                         |
| --------------- | -------------------------- |
| @Before         | 비즈니스 로직 실행 전               |
| @AfterReturning | 비즈니스 로직 실행 결과가 정상적으로 반환된 후 |
| @AfterThrowing  | 비즈니스 로직 실행 시 예외가 발생된 후     |
| @After          | 비즈니스 로직 실행 후               |
| @Around         | 비즈니스 로직 실행 전과 후            |

`@Pointcut` 어노테이션은 반복적으로 사용되는 pointcut을 지정할 수 있다. 해당 어노테이션을 활용하지 않고, advice 어노테이션 안에 pointcut을 지정해도 된다.

아래는 `com.example.aop.annotation.service`에 있는 모든 메서드에 Before Advice와 After Advice를 적용하는 예시인데, @Pointcut 어노테이션을 활용해 반복되는 pointcut을 변수로 만들어 활용하고 있다.

```java
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Aspect
public class TimeLog {
    @Pointcut("within(com.example.aop.annotation.service..*)")
    private void annotationServicePointcut() {}

    @Before("annotationServicePointcut()")
    public void start() {
        log.info("시작: {}ms", System.currentTimeMillis());
    }

    @After("annotationServicePointcut()")
    public void end() {
        log.info("종료: {}ms", System.currentTimeMillis());
    }
}
```

### Spring AOP API 활용

Spring AOP에서는 Proxy를 이용하여 AOP를 구현하기 때문에, 위에서 AspectJ를 활용한 방식과는 조금 다르다. 우선 동작과정에 대해 간단하게라도 살펴보고 넘어가자.  
![](https://velog.velcdn.com/images/youjung/post/a88cc88e-f04c-45ef-872d-8dacf37ca513/image.png)

1. 스프링 빈 대상이 되는 객체를 생성한다.(@Bean, 콤포넌트 스캔 대상)
2. 생성된 객체를 빈 저장소에 등록하기 직전에 빈 후처리기에 전달한다.
3. 모든 Advisor 빈을 조회하고 Pointcut을 통해 클래스와 메서드 정보를 매칭해보면서 프록시를 적용할 대상인지 판단한다.
4. 객체의 모든 메서드를 포인트컷에 비교해보면서 조건이 하나라도 만족한다면 프록시를 생성하고 프록시를 빈 저장소로 판단한다.
5. 만약 프록시 생성 대상이 아니라면 들어온 빈 그대로 빈 저장소로 반환한다.  
    빈 저장소는 객체를 받아서 빈으로 등록합니다.

#### Aspect 구현

여기서도 Advice를 정의해줘야한다. Spring AOP에서는 Advice 유형에 따른 인터페이스를 아래와 같이 지원한다.

| 어드바이스 유형        | 인터페이스                                           |
| --------------- | ----------------------------------------------- |
| Before          | org.springframework.aop.MethodBeforeAdvice      |
| After-returning | org.springframework.aop.AfterReturningAdvice    |
| After-throwing  | org.springframework.aop.ThrowsAdvice            |
| Around          | org.aopalliance.intercept.MethodInterceptor     |
| Introduction    | org.springframework.aop.IntroductionInterceptor |

#### 예시

`MethodInterceptor` 인터페이스를 활용하여 Around Advice를 생성하고 Target에 적용했다.

```java
public class DynamicProxyTest {
    @Test
    public void proxyFactoryBean() {
        ProxyFactoryBean pfBean = new ProxyFactoryBean();
        // 타깃 설정
        pfBean.setTarget(new HelloTarget());
        // 부가기능을 담은 어드바이스 추가
        // 여러개 추가도 가능
        pfBean.addAdvice(new UppercaseAdvice());

        // FactoryBean 이므로 getObject()로 생성된 프록시를 가져온다.
        Hello proxiedHello = (Hello) pfBean.getObject();
        System.out.println(proxiedHello.sayHello("toby"));
    }

    static class UppercaseAdvice implements MethodInterceptor {
        @Override
        public Object invoke(MethodInvocation invocation) throws Throwable {
            System.out.println("시작");
            // 타깃 오브젝트의 메서드 실행
            String ret = (String)invocation.proceed();
            System.out.println("종료");
            return ret.toUpperCase();
        }
    }

    // 타깃과 프록시가 구현할 인터페이스
    static interface Hello {
        String sayHello(String name);
        String sayThankYou(String name);
    }

    // 타깃 클래스
    static class HelloTarget implements Hello {
        @Override
        public String sayHello(String name) {
            return "Hello " + name;
        }

        @Override
        public String sayThankYou(String name) {
            return "Thank You " + name;
        }
    }

}
```

아직 어떤 방법이 좋은 방법인지, 실무에서는 어떻게 활용하고 있는지 잘 감이 오지 않는다.
AOP가 뭔지는 알았는데, Proxy에 대해서는 온전히 이해하지 못한 것 같다. 
런타임시에 Aspect를 적용하기 위해 만들어지는 객체라는 정도만 이해했다.
트랜잭션의 처리 과정을 보면서 실제로 Spring에서 AOP는 어떤식으로 활용되었는지 살펴보면서 Proxy에 대한 부분도 추가로 공부하는 게 좋을 것 같다.