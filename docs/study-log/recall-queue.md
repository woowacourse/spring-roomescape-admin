

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


### 
<details>


</details>






