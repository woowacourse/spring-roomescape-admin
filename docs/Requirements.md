## 1단계: 웹 요청-응답

### 예약 CRUD API

| 기능 | 메서드 / URL | 요청 본문 | 응답 |
|------|--------------|----------|------|
| 예약 조회 | GET /reservations | - | `[{id, name, date, time}, ...]` |
| 예약 추가 | POST /reservations | `{name, date, time}` | `{id, name, date, time}` |
| 예약 삭제 | DELETE /reservations/{id} | - | `200 OK` |

### 예약 추가 요청·응답 예시
```json
POST /reservations HTTP/1.1
Content-Type: application/json

{
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

```json
HTTP/1.1 200
Content-Type: application/json

{
  "id": 1,
  "name": "브라운",
  "date": "2023-08-05",
  "time": "15:40"
}
```

## 2단계: 데이터베이스 연동
- 1단계에서 만든 조회 · 추가 · 삭제 API를 모두 JdbcTemplate 기반으로 전환한다
- 기존의 List<Reservation>, AtomicLong은 제거한다
- 예약 추가 시 DB가 생성한 id를 응답에 담는다

## 3단계: 시간 관리

### 시간 CRUD API
| 기능    | 메서드 / URL          | 요청 본문       | 응답                     |
| ----- | ------------------ | ----------- | ---------------------- |
| 시간 추가 | POST /times        | `{startAt}` | `{id, startAt}`        |
| 시간 조회 | GET /times         | -           | `[{id, startAt}, ...]` |
| 시간 삭제 | DELETE /times/{id} | -           | `200 OK`               |

## 4단계: 계층 분리
레이어별 책임과 역할에 따라 클래스를 분리하고, 분리한 클래스를 Spring Bean으로 등록한다.

| 레이어            | 책임            |
|------------------|-----------------|
| Controller       | 웹 요청·응답     |
| Service          | 비즈니스 플로우  |
| DAO (Repository) | DB 접근          |
| Domain           | 비즈니스 규칙    |
