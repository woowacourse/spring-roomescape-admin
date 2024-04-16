# 기능 요구 사항

## WelcomePage

- [x] root url 로 GET 요청 시 templates/admin/index.html 파일을 반환한다.

## adminPage

- [x] "localhost:8080/admin" 으로 GET 요청 시 templates/admin/index.html 파일을 반환한다.

## reservationPage

- [x] "/admin/reservation" 으로 GET 요청 시 templates/admin/reservation-legacy.html 파일을 반환한다.

# API 명세

## 예약 조회 API

```http
Request
GET /reservations HTTP/1.1
```

```http
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
