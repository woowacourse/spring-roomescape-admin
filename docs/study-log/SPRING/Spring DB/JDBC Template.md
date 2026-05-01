_JdbcTemplate은 **스프링 프레임워크에서 제공하는 JDBC(Java Database Connectivity)** 기반의 데이터베이스 액세스를 단순화하고 편리하게 처리하기 위한 **클래스**이다. JDBC는 자바에서 데이터베이스와의 연결을 관리하고 SQL 쿼리를 실행하는 데 사용되는 표준 API이며, JdbcTemplate은 이러한 JDBC 기능을 더 편리하게 사용할 수 있도록 스프링에서 제공하는 도구 중 하나이다._

_JdbcTemplate을 사용하면 일반적인 JDBC 코드에서 발생할 수 있는 반복적이고 지루한 작업들을 줄일 수 있으며, 예외 처리 및 리소스 관리 등을 자동으로 처리해준다._

### JDBCTemplate의 이점

- 간결한 코드  
    : JDBC 코드는 보통 많은 부분이 반복되고 번거로운데, JdbcTemplate을 사용하면 이러한 부분을 줄일 수 있습니다. 간결한 코드를 작성할 수 있도록 다양한 템플릿 메서드를 제공합니다.
    
- 예외 처리 자동화  
    : JdbcTemplate은 JDBC 연산 중 발생하는 일반적인 예외들을 처리해주는데, 이를 통해 명시적인 예외 처리를 줄일 수 있습니다.
    
- 콜백 패턴 활용  
    : JdbcTemplate은 JDBC 연산의 일부 또는 전체를 콜백 패턴을 통해 처리할 수 있도록 지원합니다. 이를 통해 사용자는 특정 작업을 수행하기 위해 필요한 로직을 제공할 수 있습니다(RowMapper와 PreparedStatementCallback).
    
- 편리한 ResultSet 처리  
    : JdbcTemplate은 RowMapper 인터페이스를 통해 ResultSet의 데이터를 자바 객체로 매핑할 수 있도록 지원합니다. 이는 반복적인 ResultSet 처리를 편리하게 해줍니다.
    
- 트랜잭션 관리  
    : JdbcTemplate은 스프링의 트랜잭션 매니저와 함께 사용될 수 있어서 데이터베이스 트랜잭션을 쉽게 관리할 수 있습니다.

---

JdbcTemplate은 JDBC 코어 패키지의 중앙 클래스로 JDBC의 사용을 단순화하고 일반적인 오류를 방지하는데 도움이 된다. 개발자가 JDBC를 직접 사용할 때 발생하는 다음과 같은 반복 작업을 대신 처리해준다.
- 커넥션 획득
- statement를 준비하고 실행
- 결과를 반복하도록 루프를 실행
- 커넥션 종료, statement 및 resultset 종료
- 트랜잭션을 다루기 위한 커넥션 동기화
- 예외 발생 시 스프링 예외 변환기 실행

쉽게 말해 JdbcTemplate은 개발자가 JDBC 기술을 쉽게 사용할 수 있도록 도와주는 클래스이다.

### JdbcTemplate 메서드


| 구분      | 메서드                | 반환 타입     | 의미         |
| ------- | ------------------ | --------- | ---------- |
| 데이터 변경  | `update()`         | `int`     | 영향받은 row 수 |
| 단건 조회   | `queryForObject()` | `T`       | 객체 1개      |
| 다건 조회   | `query()`          | `List<T>` | 객체 리스트     |
| 단일 값 조회 | `queryForObject()` | `T`       | 컬럼 1개 값    |

|메서드|반환 타입|설명|
|---|---|---|
|`queryForList()`|`List<Map<String, Object>>`|컬럼 기반 리스트|
|`queryForMap()`|`Map<String, Object>`|1행 결과|
|`batchUpdate()`|`int[]`|여러 쿼리 결과|
|`execute()`|`T` or void|직접 콜백 실행|
|`queryForRowSet()`|`SqlRowSet`|ResultSet 유사|

	
