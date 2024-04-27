# 요구사항

- [x] localhost:8080/admin 요청 시 `templates/admin/index.html`가 응답한다.
- [x] /admin/reservation 요청 시 `templates/admin/reservation.html`가 응답한다.
- [x] 예약 API를 작성한다.
- [x] /admin/time 요청 시 `templates/admin/time.html`가 응답한다.
- [x] 시간 API를 작성한다.

# API 명세

## 예약 API
### 예약 조회 API

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
        "date": "2023-08-05",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
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
    "timeId": 1
}

Response
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time" : {
        "id": 1,
        "startAt" : "10:00"
    }
}
```

### 예약 취소 API
```
Request
DELETE /reservations/1 HTTP/1.1

Response
HTTP/1.1 200
```

## 시간 API
### 시간 추가 API
```
request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}

response
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 조회 API
```
request
GET /times HTTP/1.1

response
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

### 시간 삭제 API
```
request
DELETE /times/1 HTTP/1.1

response
HTTP/1.1 200
```

## DB 스키마
### Time
```sql
CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```

### Reservation
```sql
CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);
```

