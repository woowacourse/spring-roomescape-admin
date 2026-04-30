# 요구사항

# 1단계

- 별도의 데이터베이스 없이 `메모리`로 예약 상태를 관리한다.
  - `List` + `AtomicLong`
- 서버를 재시작하면 데이터는 모두 사라진다.

<hr>

## API 명세

| 기능       | Http 메서드 | URL                | 요청 본문 | 응답 |
|----------|----------|--------------------|-------|----|
| 예약 전체 조회 | `GET`    | `/reservations`    | `-`     |  [{id, name, date, time}, ...]  |
| 예약 추가    | `POST`   | `/reservations`    |    {name, date, time}   |  {id, name, date, time}  |
| 예약 삭제    | `DELETE`   | `/reservations/{id}` | `-`     |  200 OK  |

<hr>

## API 명세 상세
### 1. 예약 조회
> 등록된 모든 예약 정보를 배열 형태로 반환합니다.

- URL: /reservations 
- Method: GET 
- Response Body:
```json
[
  {
    "id": 1,
    "name": "브라운",
    "date": "2026-04-28",
    "time": "14:00"
  },
  {
    "id": 2,
    "name": "제임스",
    "date": "2026-04-29",
    "time": "10:30"
  }
]
```

### 2. 예약 추가
> 새로운 예약 정보를 생성합니다. 성공 시 생성된 예약 정보와 함께 고유 ID를 반환합니다.

- URL: /reservations
- Method: POST
- Request Body:

```json
{
  "name": "브라운",
  "date": "2026-04-28",
  "time": "14:00"
}
```

- Response Body:
```json
{
  "id": 1,
  "name": "브라운",
  "date": "2026-04-28",
  "time": "14:00"
}
```

### 3. 예약 삭제
   지정한 ID의 예약 정보를 삭제합니다.

- URL: /reservations/{id} 
- Method: DELETE 
- Success Response:
  - Code: 200 OK


---

# 2단계

## 추가 요구사항

- [ ] InMemoryDatabase를 H2 Database로 변경
- [ ] 1단계에서 만든 조회, 추가, 삭제 API를 모두 JdbcTemplate 기반으로 전환한다 
- [ ] 기존의 List<Reservation>, AtomicLong은 제거한다 
- [ ] 예약 추가 시 DB가 생성한 id를 응답에 담는다

---

# 3단계

## 추가 요구사항

- 시간 관리 기능 추가
  - [ ] ReservationTime 테이블 추가
  - [ ] Reservation 테이블의 `time`컬럼을 `time_id`로 변경

## 추가 API
- 시간 관리 API

### 1. 시간 조회
> 예약 시간 목록을 조회합니다.

- URL: /times
- Method: GET
- Response Body:

```json
[
    {
        "id": 1,
        "startAt": "12:00:00"
    },
    {
        "id": 2,
        "startAt": "13:00:00"
    }
]
```

### 2. 시간 생성
> 새로운 예약 시간을 생성합니다. 성공 시 생성된 예약 시간 정보와 함께 고유 ID를 반환합니다.

- URL: /times
- Method: POST
- Request Body:

```json
{
  "startAt":"13:00:00"
}
```

- Response Body:
```json
{
  "id": 2,
  "startAt": "13:00:00"
}
```

### 3. 시간 삭제
> 새로운 예약 시간을 삭제합니다. 연관된 예약 시간이 아닐 경우에만 삭제합니다.
 
- URL: /times/{id}
- Method: DELETE



## 변경된 API

### 1. 예약 조회
> 등록된 모든 예약 정보를 배열 형태로 반환합니다.

- URL: /reservations
- Method: GET
- Response Body:
```json
[
  {
    "id": 1,
    "name": "송송",
    "date": "2026-04-30",
    "time": {
      "id": 1,
      "startAt": "12:00:00"
    }
  },
  {
    "id": 2,
    "name": "송송",
    "date": "2026-04-30",
    "time": {
      "id": 2,
      "startAt": "13:00:00"
    }
  }
]
```

### 2. 예약 추가
> 새로운 예약 정보를 생성합니다. 성공 시 생성된 예약 정보와 함께 고유 ID를 반환합니다.

- URL: /reservations
- Method: POST
- Request Body:

```json
{
  "name":"송송",
  "date":"2026-04-30",
  "timeId":2
}
```

- Response Body:
```json
{
  "id": 2,
  "name": "송송",
  "date": "2026-04-30",
  "time": {
    "id": 2,
    "startAt": "13:00:00"
  }
}
```
