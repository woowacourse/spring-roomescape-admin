# 요구사항

- [x] /admin 요청 시 `templates/admin/index.html`가 응답한다.
- [x] /admin/reservation 요청 시 아래 templates/admin/reservation-legacy.html 가 응답한다.
- [x] /time 요청 시 `templates/admin/time.html`가 응답한다.
- [x] 저장된 예약 목록을 반환한다.
- [x] 예약을 생성한다.
    - [x] 저장된 예약 시간이 있을 때에만 생성할 수 있다.
    - [x] 예약을 위한 모든 정보(예약자, 예약날짜, 예약시간)가 존재해야 한다.
- [x] 예약을 삭제한다.
- [x] 예약 시간 목록을 반환한다.
- [x] 예약 시간을 생성한다.
    - [x] 예약 시간을 위한 모든 정보(예약 시작 시간)가 존재해야 한다.
- [x] 예약을 삭제한다.
    - [x] 예약이 있는 예약시간은 삭제할 수 없다.

## API 명세

### 예약 조회 API

```
Request
GET /reservations HTTP/1.1

Response
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
