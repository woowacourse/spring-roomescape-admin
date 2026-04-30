# spring-roomescape-admin

방탈출 예약을 관리하는 Spring 웹 애플리케이션입니다.

## 📋 목차

- [프로젝트 구조](#-프로젝트-구조)
- [API 명세](#-api-명세)
- [테스트 케이스](#-테스트-케이스)

---

## 🗂 프로젝트 구조

### Domain

#### Reservation

예약 정보를 관리한다.

| 필드     | 타입          | 설명 |
|--------|-------------|-----|
| `id`   | `Long`      | 예약 ID |
| `user` | `User`      | 예약자 |
| `date` | `LocalDate` | 예약 날짜 |
| `time` | `LocalTime` | 예약 시간 |

#### User

사용자 정보를 관리한다.

| 필드 | 타입       | 설명 |
|------|----------|------|
| `id` | `Long`   | 사용자 ID |
| `userName` | `String` | 사용자 이름 |

#### ReservationTime

예약 시간 정보를 관리한다.

| 필드       | 타입        | 설명 |
|----------|-----------|------|
| `id`     | `Long`    | 시간 ID |
| `startAt` | `LocalTime` | 시작 시간 |

---

### Controller

#### ReservationController

예약 관련 HTTP 요청을 처리한다.

#### ReservationTimeController

시간 관련 HTTP 요청을 처리한다.

---

## API 명세

| 기능 | 메서드 / URL | 요청 본문 | 응답 |
|------|-------------|-----------|------|
| 예약 전체 조회 | `GET /reservations` | — | `[{id, name, date, time}, ...]` |
| 예약 추가 | `POST /reservations` | `{name, date, timeId}` | `{id, name, date, time}` |
| 예약 삭제 | `DELETE /reservations/{id}` | — | `204 No Content` |

---

### GET /reservations — 예약 전체 조회

**응답 예시**

```http
HTTP/1.1 200 OK
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

---

### POST /reservations — 예약 추가

**요청 예시**

```http
POST /reservations HTTP/1.1
Content-Type: application/json

{
    "name": "브라운",
    "date": "2023-08-05",
    "timeId": 1
}
```

**응답 예시**

```http
HTTP/1.1 201 Created
Content-Type: application/json

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

---

### DELETE /reservations/{id} — 예약 삭제

**응답 예시**

```http
HTTP/1.1 204 No Content
```

---

### GET /times — 시간 전체 조회

**응답 예시**

```http
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "startAt": "10:00"
    }
]
```

---

### POST /times — 시간 추가

**요청 예시**

```http
POST /times HTTP/1.1
Content-Type: application/json

{
    "startAt": "10:00"
}
```

**응답 예시**

```http
HTTP/1.1 201 Created
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

---

### DELETE /times/{id} — 시간 삭제

**응답 예시**

```http
HTTP/1.1 204 No Content
```

---

## ✅ 테스트 케이스

### ReservationTest

- [x] 예약자 이름, 날짜, 시간으로 예약을 생성한다.
- [x] 생성된 예약 정보를 조회한다.
- [x] 예약 ID로 예약을 삭제한다.

### ReservationTimeTest

- [x] 시간 추가, 조회, 삭제 API가 정상 동작한다.
- [x] 예약과 시간이 올바르게 연결된다.

### DBTest

- [x] H2 데이터베이스 연결 및 테이블 생성을 확인한다.
- [x] DB에서 예약 목록을 조회한다.
- [x] DB에 예약을 추가하고 삭제한다.

### LayerSeparationTest

- [x] 컨트롤러에 JdbcTemplate이 직접 주입되지 않는다.