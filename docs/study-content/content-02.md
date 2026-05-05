필수 개념 : 데이터베이스 연동
목표 : API에서 사용되는 데이터를 리스트 기반(1 단계)에서 JdbcTemplate 기반으로 전환한다.
내가 아는 것 : 데이터베이스를 driver, user, password를 사용해서 DB를 연동하고, 객체를 쿼리에 바인딩해서 DB로 날리면 데이터가 저장된다.

```text
AI 추천 질문 및 순서
1번, 7번으로 전체 구조 파악
2번, 6번으로 환경과 도구 이해
3번, 4번, 5번으로 CRUD 전환 이해
```

1. 1단계에서는 List와 AtomicLong이 각각 어떤 역할을 했고, 2단계에서는 그 역할을 DB의 무엇이 대신하게 되는가?
   1단계에서 List는 Reservation 데이터를 누적하는 역할을 수행했고, AtomicLong은 자동 증가하는 id의 역할을 했다.
   2단계 DB에서는 테이블에 데이터를 누적하고, auto-increment가 AtomicLong을 대체한다.

2. JdbcTemplate은 순수 JDBC에 비해 무엇을 대신 처리해주고, 그래서 내가 직접 안 써도 되는 코드는 무엇인가?
   JdbcTemplate은 SQL 쿼리나 업데이트를 실행하고, ResultSet을 통한 데이터를 가져오는 반복 흐름을 제어하고,
   JDBC 예외를 잡아서 org.springframework.dao 예외 계층에게 전달해준다.
   JdbcTemplate은 반복되는 JDBC 핵심 워크 플로우를 대신 해주면서, 코드 반복을 줄이고 application이 SQL 작성과 반환값 추출에만 집중하도록 해준다.

> It executes core JDBC workflow, leaving application code to provide SQL and extract results.
> This class executes SQL queries or updates, initiating iteration over ResultSets and catching JDBC exceptions
> and translating them to the common org.springframework.dao exception hierarchy.
> 출처 : https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/JdbcTemplate.html
- JdbcTemplate는 어떻게 사용하나요?
  직접 new JdbcTemplate할 필요 없이, 스프링이 주입해주는 JdbcTemplate을 사용하면 된다.

- ResultSet이 무엇이고 어디에 사용되나요?
  ResultSet은 SQL 쿼리가 실행되고 나온 결과 테이블의 데이터 값을 가져올 수 있게 해주는 인터페이스이다. (객체 변환은 콜백 인터펭)
  처음에는 첫번째 row를 커서로 가리키고 있고, while을 사용해서 한칸씩 내려간다.(1-> 끝, 반대는 불가능)
  열의 index number나 열 이름으로 값을 가져올 수 있는 getter 함수를 제공하는데, index를 사용하는 것이 보통 효율적이다. (index는 1부터 시작)
- JDBC 핵심 워크 플로우가 뭔가요?
  DB 연결 - Statement 준비  + 객체를 Statement와 바인딩 - SQL 쿼리 실행 - 결과 읽기 + 객체로 변환 - 자원 정리
  그리고 SQLExecption을 잡는 예외처리도 반복적으로 실행된다.
- JdbcTemplate에서 가장 많이 사용되는 콜백 인터페이스가 뭔가요?
  PreparedStatementSetter, RowMapper

> The ResultSet interface provides getter methods (getBoolean, getLong, and so on) for retrieving column values from the current row.
> https://docs.oracle.com/javase/8/docs/api/java/sql/ResultSet.html

3. GET /reservations를 DB 기반으로 바꾼다면, SQL 결과의 한 행(row)을 Reservation 객체로 바꾸는 책임은 어디에 있어야 하는가?`
   RowMapper는 ResultSet가 현재 가리키는 행을 java 객체로 바꿔준다. 하지만, 예외처리는 jdbc 템플릿이 수행하기 때문에, 객체로 바꾸는 책임만 가진다.
> Implementations of this interface perform the actual work of mapping each row to a result object but don't need to worry about exception handling.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/RowMapper.html

- update에서 RowMapper가 필요할까요?
  RowMapper row 데이터 값을 객체로 변환해주는 도구이다. 하지만, update 쿼리의 경우 데이터가 아닌 영향받은 행 개수를 반환하기 때문에 RowMapper 필요하지 않다.


4. POST /reservations에서 id를 내가 직접 만들지 않고 DB가 만들게 되면, 생성된 id를 응답에 담기 위해 무엇을 추가로 알아야 하는가?`
   1단계에서는 controller 내에서 직접 id를 만들고 값을 리스트에 저장했지만, 2단계에서는 값을 DB에서 생성하기 때문에 값을 가져오는 과정이 필요하다.
   이 과정에서 KeyHolder나 SimpleJdbcInsert를 사용할 수 있다.
   KeyHolder : 자동 생성된 키를 담는 도구로, getKey()를 통해 키 값을 가져오는 인터페이스이다.
   SimpleJdbcInsert : executeAndReturnKey()를 통해 키 값을 제공하는 메서드가 있다

> Interface for retrieving keys, typically used for auto-generated keys as potentially returned by JDBC insert statements.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/support/KeyHolder.html

> A SimpleJdbcInsert is a multi-threaded, reusable object providing easy (batch) insert capabilities for a table.
> It provides meta-data processing to simplify the code needed to construct a basic insert statement.
> https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/jdbc/core/simple/SimpleJdbcInsert.html

5. DELETE /reservations/{id}를 DB 기반으로 바꿀 때, HTTP 경로 변수의 값이 SQL 실행까지 어떤 흐름으로 전달되는가?`
   HTTP 경로 변수의 값인 id는 메서드 파라미터로 전달되어서 java 값으로 사용되고, JdbcTemplate이 SQL 쿼리의 파라미터로 바인딩해서 전달한다.
- 문자열 이어붙이기와 파라미터 바인딩 차이는 무엇인가요?
  문자열 이어붙이기는 SQL과 값이 섞여서 복잡하고 SQL injection문제가 발생할 수 있을 것 같아요.
  파라미터 바인딩으로 하면 코드도 깔끔하고, SQL injection문제도 해결할 수 있겠네요.

6. schema.sql은 왜 필요한가? 그리고 이 파일이 없거나 테이블 구조가 코드와 다르면 어떤 종류의 문제가 생길까? (미완성)
   schema.sql은 DB의 테이블 구조를 정의하고 있으며, 이를 기반으로 애플리케이션 초기화 단계에서 테이블을 생성한다. (DDL)
   따라서, 이 파일이 없거나 구조가 다르면, 테이블 생성 확인/쿼리 실행 시점에 발생한다.

7. 이번 단계의 테스트들은 단순히 API 응답만 검증하는가, 아니면 DB 연결·테이블 생성·데이터 반영까지 함께 검증하는가? (미완성)
   API 응답뿐만 아니라 DB 연결·테이블 생성·데이터 반영까지 모두 검증한다.
