# 방탈출 예약 관리

## 1단계 - 홈 화면

- [x] /admin 요청 시 접속시 어드민 메인 페이지가 응답한다.

## 2단계 - 예약 조회

- [x] /admin/reservation 요청 시 예약 관리 페이지가 응답한다.

### 예약

- [x] 예약에는 사용자의 이름, 예약 날짜, 예약 시간이 있다.
- [x] 예약을 모두 조회할 수 있다.

## 3단계 - 예약 추가 / 취소

- [x] 예약을 추가할 수 있다.
- [x] 예약을 취소할 수 있다.

# API 명세

### 예약 목록 조회

```
Request
GET /reservations HTTP/1.1

Response
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

### 예약 추가

```
Request
Content-Type: application/json
POST /reservations
{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

Response
Content-Type: application/json
HTTP/1.1 200 
{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}

```

### 예약 취소

```
Request
DELETE /reservations/1 HTTP/1.1

Response
HTTP/1.1 200
```
