# 학습 로그 #02-03

**시간**: 05/03 14:00 ~ 16:00, 19:00~22:00 (약 300분)
**학습 범위**: 2단계 DB 연동 - 단위 테스트 작성

## 1. 막힌 것의 종류

이번에 막힌 것은 어떤 종류의 어려움이었는가? (해당하는 것에 체크)

- [ ] 개념 자체를 모르겠다 (예: "스프링 빈이 뭔지 모르겠다")
- [ ] 개념은 알겠는데 코드로 어떻게 쓰는지 모르겠다 (예: "JdbcTemplate 문법을 모르겠다")
- [ ] 코드는 돌아가는데 이게 맞는 건지 모르겠다 (예: "계층 분리를 이렇게 해도 되나?")
- [x] 기타: Repository, Service 단위테스트 방법

## 2. 이번 타임의 학습 전략

- 이전에 바꾸기로 한 전략은 무엇이었고, 실행했는가?
    1. 3단계에 걸친 사이클 -> 실행 (코드작성, 흐름, 구체적)
    2. 스스로 생각해볼 시간 -> 실행
    3. 의문점 기록 -> 실행
    4. 재방문 키워드 작성 -> 미실행
    5. 학습해볼 키워드 AI에게 요청 -> 실행 (Import)
    6. 한가지만 선택해서 이유까지 파보기 -> 실행
- 실제로 어떻게 학습했는지 디테일한 과정을 써보세요.

### Problem-driven

에코 리뷰어에게 테스트 코드가 부족하다는 리뷰를 받았다.
`테스트 코드가 많이 부족합니다. 미션 가이드와 힌트를 기반으로 비지니스 로직 잘 분리하시고 해당 로직을 테스트하시길 바랍니다.`

### Repository 단위테스트

나는 NamedParameterJdbcTemplate을 사용하고 있는데 스프링에서는 @JdbcTest를 제공하고 있다.
아래는 공식 문서에 쓰여있는 일부를 가져왔다.
`Annotation for a JDBC test that focuses only on JDBC-based components. 
...
@JdbcTest are transactional and roll back at the end of each test.
They also use an embedded in-memory database `
JDBC와 관련된 빈만 로드 하고, 각 테스트마다 transactional, 그리고 롤백을 해준다고 한다.
심지어 인메모리 DB를 사용해 DB 격리가 되지 않아도 자동으로 해준다!!

그래서 사용을 해보려 했는데, 문제점이 발생했다.

1. 생성자 주입이 되지 않는다. -> ArgumentResolverException 발생
   @AutoWired를 이용해서 해결했다.
2. JDBC 관련 컴포넌트만 로드해서 그런지, @Autowired를 사용해도 등록할 수 있는 Bean이 없다는 에러가 발생했다.
   공식 문서를 확인해보니 JdbcTemplate, DataSource, NamedParameterTemplate과 같은 컴포넌트만 로딩해준다는 것을 알게 되었다.
   내 컴포넌트를 Test에서 로딩하기 위해서 @Import({myRepositoryImplClass}) 를 사용하면 된다는 것을 알게 되었고, 문제를 해결할 수 있었다.

### Service 단위테스트

현재 Service 레이어에서는, 연관관계 위반 발생을 체크해서 사용자에게 의미있는 에러 메시지를 전달하고 있다.
따라서 ReservationService에서는 save에 대한 테스트, ReservationTimeService에서는 delete에 대한 테스트를 작성했다.
이때 단위테스트는 Repository에 영향을 받지 않아야 함으로 mock을 이용해서 단위테스트를 진행했다.

### 통합테스트?

브리가 제공해준 테스트 코드에서는 다음과 같은 어노테이션을 사용하고 있다.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)

아래는 @DirtiesContext 공식 문서의 일부를 가져왔다.
`or example, by modifying the state of a singleton bean, modifying the state of an embedded database, etc. Subsequent tests that request the same context will be supplied a new context.`
컨텍스트가 새로 제공된다고 한다. 즉 매 테스트 메서드가 실행될 때 마다 모든 컴포넌트(빈)를 다시 로드하는 아주 비효율적인 어노테이션이었다.
블로그를 검색해보면 비효율적이라고 말하는데, 공식문서를 확인해보니 왜 그렇게 말했는지 알 수 있었다.

나의 경우에는 BEAN이 오염되는 경우도 없고, 변경되는 경우도 없다. 그냥 DB데이터만 초기화해주면 되기 때문에 @BeforEach를 이용해서 데이터를 초기화 해줬다.
의문점은 다음과 같다.

1. 테이블에 데이터를 삽입할때마다 id가 변경되는데 어떡하지?
    - 웃기게도 나는 처음에는 id를 생성된 객체에서 받아오지 않고, 하드코딩을 통해서 해결하려고 했다. 하지만 ID를 생성하는건 Repository의 책임이 아니라는 생각이 들어, 코드를 변경하게 되었다. (
      DB의 책임이다.)
    - RestAssured에서는 body에 있는 값을 추출해 올 수 있다. body에서 id를 반환하는 메서드를 분리한 후에, 각 테스트에서 메서드를 호출하는 방식으로 해결했다.
2. 실제 사용하던 DB가 초기화 되면 어떡하지?
    - @JdbcTest에서는 실제 사용하는 DB환경과 Test에서 사용하는 DB를 분리했었다. 똑같이 인메모리 DB환경을 실제 환경과 테스트 환경을 분리해서 해결했다.

-> 언제 빈이 오염될까? 아직은 잘 모르겠다.

## 3. 전략 평가

- 효과적이었던 것과 그 이유
  이게 어떤 어노테이션이지? 라는 의문점으로부터 시작했다. 웹에서 정보를 수집하려 했으나, @DirtiesContext가 안좋다고 말만하지 정확한 동작은 몰랐다.
  공식문서를 확인해보니 정확한 이유를 확인할 수 있었다.
  정확한 정보를 이해하니, 내 코드에서는 어떤 부분을 변경해야 할지 알 수 있었다. 나의 경우에는 Bean이 오염되는 경우가 없었고, 변경되는 DB데이터 부분만 초기화해주면 됐다.
  공식 문서를 기반으로 정확한 정보를 수집하고, 내 코드에 적용해보면서 코드에 대한 근거가 생겼다.

- 비효과적이었던 것과 그 이유
- 막힌 것의 종류(1번)와 전략의 궁합은 어땠는가?

## 4. AI 피드백

- 자신의 학습 전략에 대해 AI 학습 전문가에게 피드백을 요청하고,
  유용했던 제안 1가지 이상 기록

## 5. 다음 타임에 바꿀 것

- 유지할 것과 그 이유
- 바꿀 것과 그 이유
