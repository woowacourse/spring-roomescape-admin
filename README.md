# 요구사항

- [x] localhost:8080/admin 요청 시 `templates/admin/index.html`가 응답한다.
- [x] /admin/reservation 요청 시 아래 templates/admin/reservation-legacy.html 가 응답한다.

## API 명세

### 예약 조회 API

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

### 예약 추가 API
```
Request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

Response
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

### 예약 취소 API
```
Request
DELETE /reservations/1 HTTP/1.1

Response
HTTP/1.1 200
```

## DB 스키마
### Reservation

```sql
CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time    VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```
