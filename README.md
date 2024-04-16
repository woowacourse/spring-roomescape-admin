# 1단계 요구사항

- [x] localhost:8080/admin 요청 시 `templates/admin/index.html`가 응답한다.

# 2단계 요구사항

- [ ] /admin/reservation 요청 시 아래 templates/admin/reservation-legacy.html 가 응답한다.

## API 명세

예약 조회 API

```
Request
GET /reservations HTTP/1.1
Response
HTTP/1.1 200
Content-Type: application/json

Response
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
