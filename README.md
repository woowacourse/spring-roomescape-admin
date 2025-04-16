# 1. 메인페이지 응답

### Request

```
GET /admin HTTP/1.1
```

### Response

```
templates/admin/index.html
```

# 2. 예약페이지 응답

### Request

```
GET /admin/reservation HTTP/1.1
```

### Response

```
templates/admin/reservation-legacy.html
```

# 3. 예약 조회

### Request

```
GET /reservations HTTP/1.1

```

### Response

```
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

# 4. 예약 추가

### Request

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

```

### Response

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

# 5. 예약 취소

### Request

```
DELETE /reservations/{reservationId} HTTP/1.1
```

### Response

```
HTTP/1.1 200
```