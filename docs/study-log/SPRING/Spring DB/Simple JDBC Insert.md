`SimpleJdbcInsert` 는 테이블에 쉽게(batch) 삽입할 수 있는 다중 threaded 재사용 가능한 객체입니다. 기본 삽입문을 구성하는 데 필요한 코드를 간소화하기 위해 메타 데이터 처리를 제공합니다. 테이블 이름과 a만 제공하면 됩니다 `Map` 열 이름과 열 값을 포함합니다.

메타 데이터 처리는 다음을 기반으로 합니다 `DatabaseMetaData` JDBC 드라이버 제공. JDBC 드라이버가 지정된 테이블의 열 이름을 제공할 수 있는 한, 이 자동 감지 기능에 의존할 수 있습니다. 그렇지 않은 경우 열 이름을 명시적으로 지정해야 합니다.

`SimpleJdbcInsert`는 INSERT SQL을 직접 작성하지 않고 테이블 기준으로 자동 생성 + 실행해주는 JdbcTemplate 래퍼다.

`SimpleJdbcInsert`는 “PreparedStatement + KeyHolder 처리”를 전부 제거해주는 간소화 도구다.

```java
SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)
        .withTableName("reservation")
        .usingGeneratedKeyColumns("id");

Map<String, Object> params = new HashMap<>();
params.put("name", "홍길동");
params.put("date", "2026-04-29");
params.put("time", "15:00");

Number key = insert.executeAndReturnKey(params);
```
``
```
흐름
1. 테이블 지정
2. 컬럼 값 Map으로 전달
3. INSERT SQL 자동 생성
4. 실행 + 생성된 키 반환
```

#### 수정 전 코드 vs 수정 후 코드
```java
@Override  
public Reservation save(Reservation reservation) {  
    KeyHolder keyHolder = new GeneratedKeyHolder();  
  
    jdbcTemplate.update(connection -> {  
        PreparedStatement ps = connection.prepareStatement(  
                "INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",  
                Statement.RETURN_GENERATED_KEYS  
        );  
  
        ps.setString(1, reservation.getName());  
        ps.setString(2, reservation.getDate().toString());  
        ps.setString(3, reservation.getTime().toString());  
  
        return ps;  
    }, keyHolder);  
  
    Number key = keyHolder.getKey();  
    if (key == null) {  
        throw new IllegalStateException("ID 생성 실패");  
    }  
  
    Long id = key.longValue();  
  
    return Reservation.of(  
            id,  
            reservation.getName(),  
            reservation.getDate(),  
            reservation.getTime()  
    );  
}
```

```java
@Override  
public Reservation save(Reservation reservation) {  
    SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)  
            .withTableName("reservation")  
            .usingGeneratedKeyColumns("id");  
    Map<String, Object> params = Map.of(  
            "name", reservation.getName(),  
            "date", reservation.getDate().toString(),  
            "time", reservation.getTime().toString()  
    );  
    Long id = insert.executeAndReturnKey(params).longValue();  
    return Reservation.of(  
            id,  
            reservation.getName(),  
            reservation.getDate(),  
            reservation.getTime()  
    );  
}
```