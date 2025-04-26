## room-escape

---

### 프로그램 개요

- 방탈출 게임과 관련하여 예약 관리 시스템을 구현한다.

---

### 기능 목록

* 어드민 메인 화면 출력 기능
    - `localhost:8080/admin` 요청 시, 어드민 메인 페이지 `index.html`가 응답하게 한다.
    - 템플릿 엔진을 활용한 SSR 방식을 활용해 대응하는 페이지가 출력되게 한다.

* 어드민 예약 내역 화면 출력 기능
    - `localhost:8080/admin/reservation` 요청 시, 어드민 예약 내역 관리 페이지 `reservation.html`가 응답하게 한다.
    - 예약 내역 조회 API를 구현해 예약 내역이 출력되게 한다.

* 어드민 시간 관리 화면 출력 기능
    - `localhost:8080/admin/time` 요청 시, 어드민 시간 관리 페이지 `time.html`이 응답하게 한다.
    - 시간 관리 조회 API를 구현해 시간 관리 내역이 출력되게 한다.

* 어드민 예약 내역 추가 및 삭제 기능
    - 예약 내역 추가 및 삭제 API를 구현해 예약 내역에 반영되도록 한다.

> 예약 내역 조회, 추가 및 삭제 API

* 예약 조회 요청

```http request
GET /reservations HTTP/1.1
```

* 예약 조회 응답

```http request
HTTP/1.1 200
Content-Type: application/json

[
    {
            "id": 1,
            "name": "브라운", 
            "date": "2023-08-05",
            "time": {
                "id": 1,
                "startAt": "10:00"
            }
    }
]
```

* 예약 추가 요청

```http request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

* 예약 추가 응답

```http request
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time" : {
        "id": 1,
        "startAt" : "10:00"
    }
}
```

* 예약 삭제 요청

```http request
DELETE /reservations/1 HTTP/1.1
```

* 예약 삭제 응답

```http request
HTTP/1.1 200
```

* 어드민 시간 관리 내역 추가 및 삭제 기능
    - 시간 관리 내역 추가 및 삭제 API를 구현해 예약 내역에 반영되도록 한다.

> 시간 내역 조회, 추가 및 삭제 API

* 시간 조회 요청

```http request
GET /times HTTP/1.1
```

* 시간 조회 응답

```http request
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 1,
        "startAt": "10:00"
    }
]
```

* 시간 추가 요청

```http request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

* 시간 추가 응답

```http request
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

* 시간 삭제 요청

```http request
DELETE /times/1 HTTP/1.1
```

* 시간 삭제 응답

```http request
HTTP/1.1 200
```

---

### 프로그래밍 요구 사항

* h2 데이터베이스를 적용하도록 한다.

> gradle 의존성 추가 (build.gradle)

```groovy
dependencies {
    //...
    implementation 'org.springframework.boot:spring-boot-starter-jdbc'
    runtimeOnly 'com.h2database:h2'
}
```

> h2 데이터베이스 설정 (application.properties)

```properties
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:database
```

> 예약 내역과 시간 관리 관련 DB 스키마 설정 (schema.sql)

```h2
CREATE TABLE reservation_time
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,                                        -- 컬럼 수정
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id) -- 외래키 추가
);

```

* 예약 내역을 조회하는 기능을 구현함에 있어 데이터베이스를 적용하도록 한다.
    - `JdbcTemplate`를 바탕으로 select 쿼리를 사용해 예약 내역을 조회하도록 한다.

* 예약 내역을 추가 및 삭제하는 기능을 구현함에 있어 데이터베이스를 적용하도록 한다.
    - `JdbcTemplate`를 바탕으로 simple jdbc insert 쿼리를 사용해 예약 내역을 조회하도록 한다.
    - `JdbcTemplate`를 바탕으로 delete 쿼리를 사용해 예약 내역을 삭제하도록 한다.

* 어드민 예약 내역과 시간 관리 테이블 연결
  - 예약 내역 페이지에서 시간 테이블에 저장된 값만 선택될 수 있게 수정한다.
  - 예약 클래스의 시간 타입을 String에서 ReservationTime 객체로 수정하고, 예약 조회, 추가 쿼리를 알맞게 변경한다.

* 계층화 리팩터링
    - 컨트롤러는 웹 요청 - 응답 책임만 가지도록 수정한다.
    - 데이터베이스 접근 책임은 리포지토리가 가지도록 위임한다.
    - 비즈니스 플로우 책임은 서비스가 가지도록 위임한다.
    - 비즈니스 규칙 책임은 도메인이 가지도록 위임한다.

---
