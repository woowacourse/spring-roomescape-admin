# 기능 요구 사항

## WelcomePage

- [x] root url 로 GET 요청 시 templates/admin/index.html 파일을 반환한다.

## adminPage

- [x] "localhost:8080/admin" 으로 GET 요청 시 templates/admin/index.html 파일을 반환한다.

## reservationPage

- [x] "/admin/reservation" 으로 GET 요청 시 templates/admin/reservation-legacy.html 파일을 반환한다.
- [x] "/reservations" 으로 GET 요청 시 모든 예약 목록을 반환한다.
- [x] "/reservations" 으로 POST 요청 시 예약을 추가한다.
- [x] "/reservations/{id}" 으로 DELETE 요청 시 예약을 취소한다.

## timePage

- [ ] "/admin/time" 으로 GET 요청 시 templates/admin/time.html 파일을 반환한다.
- [ ] "/times" 으로 GET 요청 시 모든 시간을 반환한다.
- [ ] "/times" 으로 POST 요청 시 시간을 추가한다.
- [ ] "/times/{id}" 으로 DELETE 요청 시 시간을 삭제한다.

# API 명세

## 예약 조회 API

```http
Request
GET /reservations HTTP/1.1
```

### Response

```http
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

## 예약 추가 API

### Request

```http
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

### Response

```http
HTTP/1.1 200
Content-Type: application/json
Location: /reservations/1


```

## 예약 취소 API

### Request

```http
DELETE /reservations/1 HTTP/1.1
```

### Response

```http
HTTP/1.1 204
```

## 시간 추가 API

### request

```http
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

### response

```http
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

## 시간 조회 API

### request

```http
GET /times HTTP/1.1
```

### response

```http
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 1,
        "startAt": "10:00"
    }
]
```

## 시간 삭제 API

### request

```http
DELETE /times/1 HTTP/1.1
```

### response

```http
HTTP/1.1 200
```
