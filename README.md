# 방탈출 예약 관리 시스템

## 요구사항 분석

### 1. 홈 화면

- [x] `/admin`으로 요청시 메인 페이지 응답

### 2. 예약 조회

- [x] `/admin/reservation` 요청 시 예약 관리 페이지 응답
- [x] 예약 관리 페이지 로드 시 호출되는 예약 목록 조회

### 3. 예약 추가 / 취소

- [x] 예약 관리 페이지 내에서 예약 추가
- [x] 예약 관리 페이지 내에서 예약 삭제

## API 명세

### 1. 메인페이지 응답

```
# Request
GET /admin HTTP/1.1

# Response
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": "11:00"
    }
]
```

### 0. 페이지 응답

- 메인 페이지

```
# Request
GET /admin HTTP/1.1

# Response
templates/admin/index.html
```

- 예약 관리 페이지

```
# Request
GET /admin/reservation HTTP/1.1

# Response
templates/admin/reservation-legacy.html
```

### 1. 예약 목록 조회

```
# Request
GET /reservations HTTP/1.1

# Response
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": "11:00"
    }
]
```

### 2. 예약 저장

```
# Request
POST /reservations HTTP/1.1

# Response
HTTP/1.1 200
Content-Type: application/json
{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

### 3. 예약 삭제

```
# Request
DELETE /reservations/{reservationId} HTTP/1.1

# Response
HTTP/1.1 200
```
