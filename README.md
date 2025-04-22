# spring-roomescape-admin

## 화면

- 어드민 메인 페이지: localhost:8080/admin
- 예약 관리 페이지: localhost:8080/admin/reservation
- 시간 관리 페이지: localhost:8080/admin/time

## API 명세

### 시간 추가 API

#### request

```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

#### response

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 조회 API

#### request

```
GET /times HTTP/1.1
```

#### response

```
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

#### request

```
DELETE /times/1 HTTP/1.1
```

#### response

```
HTTP/1.1 200
```

### 예약 추가 API

#### request

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

#### response

```
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

### 예약 조회 API

#### request

```
GET /reservations HTTP/1.1
```

#### response

```
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

### 예약 취소 API

#### request

```
DELETE /reservations/1 HTTP/1.1
```

#### response

```
HTTP/1.1 200
```

