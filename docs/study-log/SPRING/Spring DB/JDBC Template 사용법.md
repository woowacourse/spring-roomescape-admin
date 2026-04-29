## 사용법
### 1. JdbcTemplate 설정

스프링에서 JdbcTemplate을 사용하기 위해서는 먼저 jdbc 라이브러리를 프로젝트에 추가해야 한다.
Gradle을 기준으로 설명하자면 다음과 같다.

```
//JdbcTemplate 추가
implementation 'org.springframework.boot:spring-boot-starter-jdbc'
```

build.gradle 파일에 위와 같은 코드를 작성해주면 된다.
JdbcTemplate은 spring-jdbc에 포함되어 있다.

### 2. DataSource 주입

```
private final JdbcTemplate jdbcTemplate;

public JdbcTemplateItemRepository(DataSource dataSource) {
	this.jdbcTemplate = new JdbcTemplate(dataSource);
}
```

JdbcTemplate은 DataSource를 필요로 한다. DataSource는 스프링 빈으로 등록되어 있어야 한다. 

이렇게 JdbcTemplate을 사용할 때 DataSource를 의존 관계 주입받는 방법과, JdbcTemplate을 스프링 빈으로 직접 등록하고 주입받는 방법이 있지만 전자를 관례상 많이 사용한다.

참고)

[DataSource에 대한 이해](https://code-lab1.tistory.com/274)
[[Spring] 의존관계 주입(Dependency Injection), 의존성 주입, DI란?](https://code-lab1.tistory.com/122)
[[Spring] IoC,DI, 스프링 컨테이너(Container), 스프링 빈(Bean)이란?](https://code-lab1.tistory.com/127)

### 3. 쿼리 작성 및 실행

스프링 JdbcTemplate 사용 방법 공식 매뉴얼이 제공하는 몇 가지 예제를 살펴보자.

**1) queryForObject()**

queryForObject() 인터페이스의 정의는 다음과 같다.

```
<T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) throws DataAccessException;
```

- sql : 실행할 sql문
- rowMapper : 조회대상 
- args : 파라미터들

이러한 queryForObject() 메서드를 사용하면 단건 조회를 할 수 있다. 이때 조회 대상이 객체가 아닌 단순 데이터라면 타입을 아래와 같이 지정해줄 수 있다.

```
int rowCount = jdbcTemplate.queryForObject("select count(*) from t_actor",Integer.class);
```

preparedStatement ("?")를 이용해 파라미터 바인딩도 할 수 있다.? 에 순서대로 파라미터가 들어가게 된다.

```
int countOfActorsNamedJoe = jdbcTemplate.queryForObject(
"select count(*) from t_actor where first_name = ?", Integer.class, "Joe");
```

String을 조회할 때는 아래와 같이 사용할 수도 있다.

```
String lastName = jdbcTemplate.queryForObject(
"select last_name from t_actor where id = ?", String.class, 1212L);
```

이때 단순 데이터 타입이 아닌 객체를 조회하려면 어떻게 할까?

**여기서 RowMapper를 활용해 객체를 조회할 수 있다.**

#### RowMapper란?

RowMapper는 데이터베이스의 반환 결과인 ResultSet을 객체로 변환해주는 클래스이다. 

순수 JDBC를 사용할 때는 객체를 조회할 때 다음과 같이 했다.

```
// 쿼리 날리기
ResultSet rs = stat.excuteQuery("SELECT * FROM Item");

// 결과값 가져오기
while(rs.next()) {
     // item 객체에 값 저장
     item = new Item();
     item.setId(rs.getInt(1));
     item.setItemName(rs.getString(2));
     item.setPrice(rs.getInt(3));
     
     // 리스트에 추가
     itemList.add(item);
}
```

ResultSet의 결과를 개발자가 직접 꺼내 객체에 담아 저장하였다. 

하지만 RowMapper를 사용하면 이러한 반복 작업을 자동화해준다.

RowMapper의 mapRow()라는 메서드는 아래와 같이 정의되어 있다.

```
T mapRow(ResultSet rs, int rowNum) throws SQLException;
```

ResultSet rs에 결괏값을 담아와 사용자가 원하는 객체에 담는다. rowNum은 반복되는 루프 중 현재 행의 번호를 나타낸다.

즉 아래와 같이 동작한다.

```
public Item mapRow(ResultSet rs, int rowNum) throws SQLException {
    // ResultSet 값을 Item 객체에 저장
    Item item = new Item();
    item.setId(rs.getLong("id"));
    item.setItemName(rs.getString("item_name"));
    item.setPrice(rs.getInt("price"));
    
    // item 반환
    return item;
};
```

이것을 람다식을 이용해 더 간단하게 만들고, queryForObject() 메서드에서 객체를 조회하도록 할 수 있다.

```
String sql = "select id, item_name, price from item where id= ?";

Item item = jdbcTemplate.queryForObject(sql, itemRowMapper(), id);

private RowMapper<Item> itemRowMapper() {
    return (rs, rowNum) -> {
        Item item = new Item();
        item.setId(rs.getLong("id"));
        item.setItemName(rs.getString("item_name"));
        item.setPrice(rs.getInt("price"));
        item.setQuantity(rs.getInt("quantity"));
        return item;
    };
}
```

**2) query() 메서드**

query() 메서드를 사용하면 여러 건을 조회할 수 있다. 이때도 역시 RowMapper를 이용할 수 있다.

```
private final RowMapper<Actor> actorRowMapper = (resultSet, rowNum) -> {
    Actor actor = new Actor();
    actor.setFirstName(resultSet.getString("first_name"));
    actor.setLastName(resultSet.getString("last_name"));
    return actor;
};

public List<Actor> findAllActors() {
    return this.jdbcTemplate.query("select first_name, last_name from t_actor", actorRowMapper);
```

**3) update() 메서드**

INSERT, UPDATE, DELETE 등 데이터를 변경하고 싶을 때는 update() 메서드를 사용할 수 있다. SQL 실행 결과에 영향받은 로우 수를 int로 반환한다.

**등록**

```
jdbcTemplate.update("insert into t_actor (first_name, last_name) values (?, ?)","Leonor", "Watling");
```

**수정**

```
jdbcTemplate.update("update t_actor set last_name = ? where id = ?", "Banjo", 5276L);
```

**삭제**

```
jdbcTemplate.update("delete from t_actor where id = ?", Long.valueOf(actorId));
```

4) execute() 메서드

이외에 임의의 SQL을 실행할 때는 execute() 메서드를 사용할 수 있다. 테이블을 생성하는 DDL 등에 사용할 수 있다.

```
jdbcTemplate.execute("create table mytable (id integer, name varchar(100))");
```

## JdbcTemplate 메서드

| 구분      | 메서드                | 반환 타입     | 의미         |
| ------- | ------------------ | --------- | ---------- |
| 데이터 변경  | `update()`         | `int`     | 영향받은 row 수 |
| 단건 조회   | `queryForObject()` | `T`       | 객체 1개      |
| 다건 조회   | `query()`          | `List<T>` | 객체 리스트     |
| 단일 값 조회 | `queryForObject()` | `T`       | 컬럼 1개 값    |

| 구분             | 입력                       | 반환                               | 설명                   |
| -------------- | ------------------------ | -------------------------------- | -------------------- |
| query          | SQL + RowMapper          | List                             | 여러 row 조회            |
| queryForObject | SQL + RowMapper or Class | T                                | 단일 row 조회            |
| queryForList   | SQL                      | List<Map<String,Object>> or List | 간단 조회 (DTO 없이)       |
| queryForMap    | SQL                      | Map<String,Object>               | 단일 row Map           |
| update         | SQL + 파라미터               | int                              | INSERT/UPDATE/DELETE |
| batchUpdate    | SQL + List               | int[]                            | 여러 건 한번에             |
| execute        | Callback                 | T or void                        | JDBC 직접 제어           |
| queryForRowSet | SQL                      | SqlRowSet                        | ResultSet 대체         |

## 궁금증

### 각 메서드의 사용 방법
#### query
```java
List<User> users = jdbcTemplate.query(
    "SELECT * FROM user",
    (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name"))
);
```
>반환 결과  
>`[User{id=1, name='A'}, User{id=2, name='B'}]`

> 얘는 그냥 하나 실행시키고 결과값을 받는건가?
```
DB 결과:
id | name
1  | A
2  | B

→ RowMapper 2번 실행
→ User 2개 생성
→ List<User> 반환
```
> SQL을 실행 시키고 여러 row 객체로 변환해서 List로 반환하는 메서드임


#### quertForObject
```java
User user = jdbcTemplate.queryForObject(
    "SELECT * FROM user WHERE id=?",
    (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name")),
    1L
);
```
>반환 결과  
>`User{id=1, name='A'}`

```java
int count = jdbcTemplate.queryForObject(
    "SELECT COUNT(*) FROM user",
    Integer.class
);
```
>반환 결과  
>`2`

#### queryForList
```java
List<Map<String, Object>> list =
    jdbcTemplate.queryForList("SELECT * FROM user");
```
>반환 결과  
>`[ {id=1, name='A'}, {id=2, name='B'} ]`

#### queryForMap
```java
Map<String, Object> map =
    jdbcTemplate.queryForMap("SELECT * FROM user WHERE id=1");
```
>반환 결과  
>`{id=1, name='A'}`

> Object로 받는데 왜 name = 'A'라는게 나오는거야?
> - key → 컬럼명 (String)
> - value → 컬럼 값 (Object로 업캐스팅)
>   
>   컬럼이 여러개면 어떻게 나옴?
>   - `queryForMap`은 **컬럼 개수만큼 key-value로 담긴 Map 1개를 반환**한다.

#### update
```java
int result = jdbcTemplate.update(
    "INSERT INTO user(name) VALUES (?)",
    "홍길동"
);
```
>반환 결과  
>`1` (영향 받은 row 수)

#### batchUpdate
```java
int[] result = jdbcTemplate.batchUpdate(
    "INSERT INTO user(name) VALUES (?)",
    List.of(new Object[]{"A"}, new Object[]{"B"})
);
```
>반환 결과  
>`[1, 1]`

#### execute
```java
jdbcTemplate.execute((Connection con) -> {
    return null;
});
```
>반환 결과  
>`null (또는 직접 정의한 값)`

#### queryForRowSet
```java
SqlRowSet rs = jdbcTemplate.queryForRowSet("SELECT * FROM user");
```
>반환 결과  
>`SqlRowSet (cursor 기반 조회 객체)`

**결국 query는 SELECT용, update는 INSERT / UPDATE / DELETE 전용**

### 개발하다 궁금증
