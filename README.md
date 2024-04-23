# 방탈출 예약 관리 

## API 명세

### 예약 조회 API

**Request**

```http request
GET /reservations HTTP/1.1
```

<br>

**Response**

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
<br>

### 예약 추가 API

**Request**

```http request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

<br>

**Response**

```
HTTP/1.1 201 
Location: reservations/{id}
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}

```

<br>

### 예약 취소 API

**Request**

```http request
DELETE /reservations/1 HTTP/1.1
```

**Response**
```
HTTP/1.1 204
```
