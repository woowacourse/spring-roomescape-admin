

### 요청과 응답에서의 Jackson 직렬화
<details>

- **`@RestController`**: `@Controller + @ResponseBody`가 합쳐진 거.
    - 메서드 리턴값이 그대로 HTTP 응답 body로 직렬화됌.
    - List를 반환하면 Jackson이 JSON 배열로 만들어줌.
- **`@RequestBody Reservation request`**:
    - JSON body가 `Reservation` 객체로 역직렬화됌.
        - Jackson이 값을 채우며 방식이 여러가지가 있다.
            - 세터방식 - 세터와 기본생성자 필요
            - 필드 직접 주입 - 기본생성자 필요, 세터 없이 리플렉션으로 private 접근
            - 생성자 주입 - 권장방법(불변객체 설계가능), 객체생성 시 생성자로 바로 넣음
            - Builder 방식 - Lombok `@Builder` + Jackson 연동.
            - Java Record 방식 - canonical constructor로 바로 생성.
        - 지금은 기본 생성자로 직접 필드에 접근한다 정도로만 인식.
    - 동작은 하는데, "어떻게 채워지는 거지?"라는 흥미가 돋았지만 우선 호기심 큐에 넣음
        - 아래와 같이 의미없는 키-벨류가 들어와도 필요한것만 사용함.

        ```
        {
            "sdfsdf":"3sdf",
            "id": 3,
            "name": "브라운1",
            "date": "2023-08-05",
            "time": "15:40"
        }
        ```
</details>



### @DirtiesContext
<details>

- `@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)`가 붙어 있어서 매 테스트 전에 스프링 컨텍스트를 새로 만들어진다.
- 그래서 `예약_조회` 테스트가 먼저 실행되고 `예약_추가_및_삭제`가 실행돼도, 두 번째 테스트의 첫 POST가 `id=1`로 시작할 수 있는 것임
- 컨텍스트가 재생성되면서 `ReservationController` 빈도 새로 만들어지니까 `index`도 0부터 다시 시작.
- *"@DirtiesContext가 정확히 뭘 하는가, 왜 BEFORE_EACH_TEST_METHOD인가, 이거 없으면 어떻게 되나"*.

</details>

### AtomicLong
<details>

- 주요 개념 : `AtomicLong`은 **멀티스레드 환경에서 long 값을 안전하게 증가/감소/교체할 수 있게 만든 원자적(atomic) 클래스**
- 멀티스레드라는 개념이 등장. 콘솔환경과 웹환경 차이 관점으로 학습해보기.

</details>


### runtimeOnly 쓴 이유
<details>

- H2 드라이버는 컴파일 타임엔 코드가 직접 부르지 않는다.(JdbcTemplate이 알아서 부름). 런타임에만 클래스패스에 있으면 된다.   
- *"implementation vs runtimeOnly vs testImplementation 차이"*
</details>



### main/resources/schema.sql 동작원리
<details>

- 이 파일이 클래스패스 루트에 있으면 스프링부트가 **앱 시작 시 자동으로 실행**   
- “동작 원리”

</details>


### DB 관련
<details>

- “아 메서드 시그니처 이렇게 생겼네” 정도로 사용법만 인지함.   
- `JdbcTemplate` 이 뭐고 어디서 어떻게 동작하는지, 무슨 필요성에 의해 개발되었는지.   
- `Connection` ,`DataSource` 의 관계와 어떻게 동작하는지, 무슨 필요성에 의해 개발되었고 최신 현재는 주로 어떤걸로 해당 기능을 구현하고있는지, 익숙한 JPA와 의 관계는 ?
- `RowMapper` ,`PreparedStatement` , `ResultSet`의 관계와 어떻게 동작하는지, 무슨 필요성에 의해 개발되었고 최신 현재는 주로 어떤걸로 해당 기능을 구현하고있는지. 익숙한 JPA와 의 관계는 ?
- `KeyHolder` ,`GeneratedKeyHolder` 의 관계와 어떻게 동작하는지, 무슨 필요성에 의해 개발되었고 최신 현재는 주로 어떤걸로 해당 기능을 구현하고있는지, 익숙한 JPA와 의 관계는 ?
    - **`prepareStatement(sql, new String[]{"id"})`**
        - 두 번째 인자가 *"이 INSERT 후에 어떤 컬럼의 생성된 값을 돌려받고 싶다"* 를 알린다.그리고 id 컬럼명을 명시적으로 넘겨서, KeyHolder가 그 키를 받는다.
        - `Statement.RETURN_GENERATED_KEYS` 상수를 써도 되는데, 컬럼명 배열로 주면 *어떤 컬럼인지 명시적*이라 안전하다.
        - *"PreparedStatementCreator 람다 → KeyHolder 채워짐 → getKey()"*
        - *"jdbcTemplate.update의 PreparedStatementCreator 오버로드는 정확히 어떻게 동작하나" 더해서 이런 부분까지 학습할지 고려. 과거의 기술이라서 굳이 현재 동작원리까지 알아야하는 생각이 들김함. 우선순위를 메겨도 좋을듯*
- `*KeyHolder` or `SimpleJdbcInsert`*
    - **`KeyHolder`**
        - JDBC 표준 방식 (`Statement.RETURN_GENERATED_KEYS`)에 가까움
        - 람다로 `Connection`에서 `PreparedStatement` 만들어서 `KeyHolder`에 키를 받는다.
        - **`keyHolder.getKey().longValue()`**
            - `getKey()`는 `Number`를 돌려즌다. DB나 드라이버에 따라 Long, Integer, BigInteger 등 다양해서 추상화한것.
    - **`SimpleJdbcInsert`**
        - Spring이 한 겹 더 감싼 헬퍼
        - *컬럼명이 자바 필드명과 다르면* Map을 손수 만들어 넘겨야 해
    - **`KeyHolder` 선택이유**
        - 추상화로 가려진 DB 기능들을 조금 더 살펴보기 위해 선택.
        - *"DB가 어떻게 키를 돌려주는가"* 가 좀 더 노출됨.
        - 계층 분리를 할때 DAO 개념도 같이 학습하기에 더 잘맞음.
- "H2/Spring Boot에서 schema.sql 실행 순서, FK 검사 시점, DROP/CREATE 정책”
    - `reservation`이 `reservation_time`을 참조(FK)한다. SQL 실행 시점에 참조 대상이 이미 존재해야한다. 그래서 **`schema.sql`** 작성시 참조 관계를 고려해야함. 고려 안하면 FK 제약 생성 실패함.
- 트랜잭션 필요성
  - Post에서 시간 정보를 select하는 부분 (하나의 일관된 작업에서 두번 데이터베이스에 접근한다.)
  - INSERT 후 응답에 ReservationTime 객체를 담아야 하는데, 클라이언트는 timeId만 보냈고 우리는 startAt을 모름. 그래서 별도 쿼리.

</details>


### 계층과 패키지
<details>

- 계층별 패키지 구조와 도메인별 패키지 구조(각 도메인 패키지 안에 컨트롤러, 서비스, 레포지토리다 들어감. 실제 프로젝트에서 종종 보이고 더 자주 쓴다고 하기는 함)의 차이점, 언제 무슨 상황에서 쓰는가?   
    - 왜 기본적으로 스프링과 NestJS같은 프레임 워크들의 공식문서 초반에는 계층형 패키지로 하는가?   
    - 기존에는 그저 관습처럼 썼었는데 우테코에서 순수 자바로 콘솔환경과 객체지향에 대하 학습하며 관습적으로 사용하던 계층들과 패키지 구조에 대한 의문점이 생김.   
    - 혹시 MSA라는 개념과 연관이 있는지도 학습   

</details>

### DTO
<details>

- DTO는 웹요청을 받는 컨트롤러의 도구라서 컨트롤러 패키지에 둠.   
    - DTO는 어느 계층의 것인가 ?   
    - DTO는 어느 계층까지 알아도 되는가, 어떤 기준으로 정하나   
    - 컨트롤러, 서비스, 리포지토리 별로 각각  DTO가 있는 경우도 있던데 이에 대해서도 학습   
    - 컨트롤러의 DTO를 그대로 서비스로 넘기는 방식 vs 컨트롤러에서 dto 다 풀어서 원시값만 넘기는 방식(계층간 결합을 줄일 수 있지만 코드량이 많아짐)   

</details>


### @Repository 어노테이션의 역할
<details>

- 스프링 컨테이너가 “이클래스를 빈으로 등록한다”는 @Component의 특수형.   
- 데이터 접근 계층임을 명시하고 AOP로 DataAccessException 변환 같은 부가 기능을 받는 등등 여러 기능이 있음.   
- 우선은 빈 등록 표시 정도로만 인지!   

</details>



### 리포지토리 계층의 반환 타입
<details>

- 기존 `queryForObject` 는 조회 시 없으면 예외를 던진다. 하지만 리포지토리 계층은 있으면 있는대로 없으면 없는대로 반환해주는게 자연스럽다고 우선 생각   
    - 예외를 리포지토리에서 던질지 혹은 Optional로 할지에 대해서도 생각해보기   
- 그리고 호출부(컨트롤러나 이후 서비스 계층)에서 “없으면 어떻게 할것인지”의 정책을 정함. → 계층 단에서의 책임분리   
</details>


### Controller에서 orElseThrow로 던지는 예외
<details>

- 지금은 클라이언트에서 500이 간다. 적절한 응답 코드를 어떻게 줄까에 대한 고찰해보기   
- `@ControllerAdvice`, `@ExceptionHandler`   

</details>



### 콘솔 UI
<details>

- 컨트롤러와 서비스 계층은 전혀 건드리지 않으며 기존 테스트 5개 모두 통과함.      
- 하지만 콘솔에서 웹의 DTO을 알아야 해서 분리가 완벽하게 되지는 않음을 발견.     
    - DTO 관련 부분에서 리팩토링으로 개선이 가능한것으로 인지!   
    - 현재와 같이 둘 이상의 환경(웹/콘솔)이 있을 경우 패키지 구성은? (ui 내부 web/console 그리고 web/컨트롤러, console/veiw,컨트롤러?)   
</details>



### 
<details>


</details>
