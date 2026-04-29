# Recall 01

## Jdbc template

기존 Jdbc에서 connection을 활용할 때 boilerplate가 고정되어 있다.

```java
try (PreparedStatement ps = ...;
    //...
    ResultSet rs = ...;
) {
    ...
} catch (SQLException e) {
    ...
}
```

이런 고정된 코드의 반복을 줄이기 위해 jdbcTemplate를 쓴다.

## query / queryForObject / update 차이

1. `query()`: select한 **여러** 튜플을 가져와야 하는 경우 사용한다.
2. `queryForObject()`: 단건 조회를 할 때 사용한다.
3. `update()`: 튜플을 가져오지 않는 `DELETE`, `UPDATE`, `INSERT`에 사용한다.
   1. insert문의 경우 prepared statement에서 어떤 것을 id로 받을지 지정이 필요하다.

## KeyHolder는 왜 필요할까?

auto increment primary key는 insert를 하는 경우 id가 삽입 후 결정된다. 삽입 성공 후 할당된 key를 받아오기 위해 key holder가 필요하다.

## DataSource 자동등록 원리

Datasource는 사용법만 익힌 상태다. jdbcTemplate를 bean에 주입하면 `application.properties`의 db 정보를 읽어와 자동으로 DB 연결 정보를 가져온다.