# 방탈출 예약 관리

## 1️⃣ 개요

방탈출 카페 관리자가 전화와 현장 예약을 직접 등록하고 관리할 수 있는 예약 관리 API입니다.

저는 총 3단계에 걸쳐 구현했습니다.

1. 메모리 기반 예약 관리
2. H2 데이터베이스 연동
3. 예약 시간 관리와 예약-시간 연결

4단계 계층 분리는 처음부터 고려하고 구현하였습니다.

---

## 2️⃣ 구현 기능 목록

### 1단계: 웹 요청-응답

처음에는 별도의 데이터베이스 없이 예약 상태를 관리했습니다.  
예약 목록은 애플리케이션 메모리에 저장했습니다.  
예약 id는 애플리케이션 내부에서 증가시키는 방식으로 발급했습니다.

이 단계에서는 HTTP 요청과 응답 흐름을 확인했습니다.  
`GET /reservations`는 전체 예약 목록을 반환합니다.  
`POST /reservations`는 예약 정보를 받아 새 예약을 생성합니다.  
`DELETE /reservations/{id}`는 id에 해당하는 예약을 삭제합니다.

### 2단계: 데이터베이스 연동

메모리 저장 방식은 서버가 재시작되면 데이터가 사라집니다.  
이 한계를 확인한 뒤 H2 인메모리 데이터베이스로 저장 방식을 변경했습니다.

DB 접근은 `JdbcTemplate`으로 처리합니다.  
예약 조회, 추가, 삭제 API는 모두 SQL을 실행하는 방식으로 동작합니다.  
예약 추가 시에는 DB가 생성한 id를 응답에 담습니다.

현재 `reservation` 테이블은 예약 이름, 날짜, 예약 시간 id를 저장합니다.  
기존의 메모리 저장소와 id 발급 코드는 제거했습니다.

### 3단계: 시간 관리

예약 시간을 문자열로 직접 입력하면 같은 시간 값이 반복됩니다.  
입력 실수도 생길 수 있습니다.  
이를 줄이기 위해 예약 시간을 별도 자원으로 분리했습니다.

`POST /times`는 예약 시간을 생성합니다.  
`GET /times`는 등록된 예약 시간 목록을 반환합니다.  
`DELETE /times/{id}`는 예약 시간을 삭제합니다.

예약은 더 이상 시간 문자열을 직접 저장하지 않습니다.  
예약 생성 요청은 `timeId`를 받습니다.  
서비스는 `timeId`로 `ReservationTime`을 조회한 뒤 예약을 생성합니다.  
예약 조회 응답에는 시간 id와 시작 시간이 함께 포함됩니다.

예약 목록 조회는 `reservation`과 `reservation_time`을 `INNER JOIN`하여 처리합니다.

### 프로젝트 구조

컨트롤러에 요청 처리, 기능 흐름, DB 접근이 함께 있으면 변경 범위가 커집니다.  
현재 코드는 역할을 나누기 위해 Controller, Service, DAO, Domain, DTO로 계층을 분리했습니다.

| 레이어 | 책임 |
| :--- | :--- |
| Controller | 웹 요청과 응답 |
| Service | 비즈니스 흐름과 트랜잭션 |
| DAO | DB 접근 |
| Domain | 도메인 데이터 표현 |
| DTO | 외부 요청과 응답 형식 |

컨트롤러는 HTTP 요청을 DTO로 받고, 응답 DTO를 반환합니다.  
서비스는 예약 생성 과정에서 예약 시간을 먼저 조회하고 예약 생성을 위임합니다.  
DAO는 SQL 실행과 결과 매핑을 담당합니다.  
도메인은 예약과 예약 시간 데이터를 표현합니다.

Controller는 `@RestController`로 등록했습니다.  
Service는 `@Service`로 등록했습니다.  
DAO 구현체는 `@Repository`로 등록했습니다.

---

## API 명세

### 예약 관리

| 기능 | 메서드 / URL | 요청 본문 | 응답 |
| :--- | :--- | :--- | :--- |
| 예약 조회 | `GET /reservations` | - | `[{id, name, date, time}, ...]` |
| 예약 추가 | `POST /reservations` | `{name, date, timeId}` | `{id, name, date, time}` |
| 예약 삭제 | `DELETE /reservations/{id}` | - | `200 OK` |

### 예약 추가 요청

```http
POST /reservations HTTP/1.1
Content-Type: application/json
```

```json
{
  "name": "브라운",
  "date": "2023-08-05",
  "timeId": 1
}
```

### 예약 추가 응답

```http
HTTP/1.1 200
Content-Type: application/json
```

```json
{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": {
    "id": 1,
    "startAt": "10:00"
  }
}
```

### 시간 관리

| 기능 | 메서드 / URL | 요청 본문 | 응답 |
| :--- | :--- | :--- | :--- |
| 시간 조회 | `GET /times` | - | `[{id, startAt}, ...]` |
| 시간 추가 | `POST /times` | `{startAt}` | `{id, startAt}` |
| 시간 삭제 | `DELETE /times/{id}` | - | `200 OK` |

### 시간 추가 요청

```http
POST /times HTTP/1.1
Content-Type: application/json
```

```json
{
  "startAt": "10:00"
}
```

### 시간 추가 응답

```http
HTTP/1.1 200
Content-Type: application/json
```

```json
{
  "id": 1,
  "startAt": "10:00"
}
```

---

### 데이터베이스 스키마

```sql
CREATE TABLE reservation_time (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE reservation (
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);
```

---

## 4️⃣ 학습 로그

학습 과정은 `docs/study-log`에 기록했습니다.

- `docs/study-log/README.md`
- `docs/study-log/log-01.md`
- `docs/study-log/log-02.md`
- `docs/study-log/log-03.md`

---
