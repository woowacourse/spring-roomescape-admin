# API 명세

## 화면 출력

### 메인 페이지

#### Request

```http
GET /admin HTTP/1.1
```

#### Response

```http
HTTP/1.1 200
Content-Type : text/html

index.html
```
---

### 예약 관리 페이지

#### Request

```http
GET /admin/reservation HTTP/1.1
```

#### Response

```http
HTTP/1.1 200
Content-Type : text/html

reservation-legacy.html
```

---

### 시간 관리 페이지

#### Request

```http
GET /admin/time HTTP/1.1
```

#### Response

```http
HTTP/1.1 200
Content-Type : text/html

templates/admin/time.html
```

---

## 데이터 전달

### 예약 조회(전체)

#### Request

```http
GET /reservations HTTP/1.1
```

#### Response

```http
HTTP/1.1 200
Content-Type: application/json

[
    {
        "id": 1,
        "name": "ted",
        "date": "2024-04-01",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
    },
    {
        "id": 2,
        "name": "ted",
        "date": "2024-04-01",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
    }
]
```

---

### 예약 조회(단일)

#### Request

```http
GET /reservation/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "name": "tesd",
    "date": "2023-04-01",
    "time" : {
        "id": 1,
        "startAt" : "10:00"
    }
}
```

---

### 예약 등록

#### Request

```http
POST /reservations HTTP/1.1
Content-type: application/json

{
    "name": "ted",
    "date": "2024-04-01",
    "time_id": 1
}
```

#### Response

```http
HTTP/1.1 201
Content-Type: application/json
Location: /reservations/1
```

---

### 예약 취소

#### Request

```http
DELETE /reservations/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 204
```

---

### 예약 시간 추가

#### Request

```http
POST /times HTTP/1.1
Content-type: application/json

{
    "startAt": "10:00"
}
```

#### Response

```http
HTTP/1.1 201
Content-Type: application/json
Location: /times/1
```

---

### 예약 시간 조회(전체)

#### Request

```http
GET /times HTTP/1.1
```

#### Response

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

---

### 예약 시간 조회(단일)

#### Request

```http
GET /times/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```
---

### 예약 시간 삭제

#### Request

```http
DELETE /times/1 HTTP/1.1
```

#### Response

```http
HTTP/1.1 204
```


