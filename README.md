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

## 7, 8단계 - 시간 관리 기능

- [ ] 예약 시간을 시간표에서 선택할 수 있다.
- [ ] 예약을 조회할 수 있다.
- [ ] 예약을 삭제할 수 있다.

### 시간

- [ ] 시간에는 예약 시간이 있다.

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
        "id": "Long",
        "name": String,
        "date": LocalDate (YYYY-MM-DD),
        "time": LocalTime (HH:mm)
    },
    {
        "id": "Long",
        "name": String,
        "date": LocalDate (YYYY-MM-DD),
        "time": {
            "id": Long,
            "startAt" : LocalTime (HH:mm)
        }
    }
]
```

### 예약 추가

```
Request
Content-Type: application/json
POST /reservations
{
    "name": String,
    "date": LocalDate (YYYY-MM-DD),
    "timeId": Long
}

Response
Content-Type: application/json
HTTP/1.1 200 
{
    "id": Long,
    "name": String,
    "date": LocalDate (YYYY-MM-DD),
    "time": {
        "id": Long,
        "startAt" : LocalTime (HH:mm)
    }
}

```

### 예약 취소

```
Request
DELETE /reservations/1 HTTP/1.1

Response
HTTP/1.1 200
```

### 시간 추가

```
Request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": LocalTime (HH:mm)
}

Response
HTTP/1.1 200
Content-Type: application/json

{
    "id": Long,
    "startAt": LocalTime (HH:mm)
}
```

### 시간 조회

```
Request
GET /times HTTP/1.1

Response
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": Long,
        "startAt": LocalTime (HH:mm)
    }
]
```

### 시간 삭제

```
Request
DELETE /times/1 HTTP/1.1

Response
HTTP/1.1 200
```
