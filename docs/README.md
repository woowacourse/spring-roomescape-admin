# API 명세

## 웹 어플리케이션 API 명세

### 시간 추가 API

#### Request

```http request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

#### Response

```http request
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 조회 API

#### Request

```http request
GET /times HTTP/1.1
```

#### Response

```http request
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

#### Request

```http request
DELETE /times/1 HTTP/1.1
```

#### Response

```http request
HTTP/1.1 204
```

### 예약 추가 API

#### Request

```http request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

#### Response

```http request
HTTP/1.1 204
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

### 예약 조회 API

#### Request

```http request
GET /reservations HTTP/1.1
```

#### Response

```http request
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

### 예약 삭제 API

#### Request

```http request
DELETE /reservations/1 HTTP/1.1
```

#### Response

```http request
HTTP/1.1 204
```

## 콘솔 어플리케이션 API 명세

### 시간 추가 API

#### Request

```text
post times 10:00
```

#### Response

```text
id - 1, startAt - 10:00
```

### 시간 조회 API

#### Request

```text
get times 
```

#### Response

```text
id - 1, startAt - 10:00
id - 2, startAt - 11:00
```

### 시간 삭제 API

#### Request

```text
delete times 1
```

#### Response

```text
```
