# 방탈출 예약 관리 프로그램
> 방탈출 예약 사이트의 미완성 버전입니다.  
> 관리자 페이지만 구현 완료되었습니다. (`admin/reservation 및 admin/time`)

## 주요 기능
- 관리자는 예약 현황을 사이트에서 조회 가능합니다. (`http://localhost:8080/admin/reservation`)
  - 예약 현황을 생성할 수 있습니다.
  - 예약 현황을 삭제할 수 있습니다.


- 관리자는 예약 가능 시간을 정의할 수 있습니다. 사용자는 임의 시간이 아닌, 정의되어 있는 시간에만 예약 가능합니다. (`http://localhost:8080/admin/time`)
  - 예약 가능 시간을 추가할 수 있습니다.
  - 예약 가능 시간을 삭제할 수 있습니다.


- 예약은 아래 정보를 포함합니다.
  - 예약 번호
  - 예약자 이름
  - 예약 날짜
  - 예약 시간


## 📖 API 명세
## [ 예약 관련 API ]
### 1. 예약 추가 API
**Request**
```
POST /reservations HTTP/1.1
Content-Type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

**Response**
```
HTTP/1.1 200 OK
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": {
        "id": 1,
        "startAt": "10:00"
    }
}
```

---

### 2. 예약 조회 API
**Request**
```
GET /reservations HTTP/1.1
```

**Response**
```json
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

---

### 3. 예약 삭제 API
**Request**
```
DELETE /reservations/1 HTTP/1.1
```

**Response**
```
HTTP/1.1 200 OK
```

---

## [ 시간 관련 API ]

### 1. 시간 추가 API
**Request**
```
POST /times HTTP/1.1
Content-Type: application/json

{
    "startAt": "10:00"
}
```
**Response**
```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 2. 시간 조회 API
**Request**
```
GET /times HTTP/1.1
```

**Response**
```
HTTP/1.1 200 OK
Content-Type: application/json

[
    {
        "id": 1,
        "startAt": "10:00"
    }
]
```

---

### 3. 시간 삭제 API
**Request**
```
DELETE /times/1 HTTP/1.1
```

**Response**
```
HTTP/1.1 200 OK
```

---


# 📄 페이지 목록
- 웰컴 페이지 ``(/)``
- 관리자 화면 ``(/admin)``
- 예약 조회 화면 ``(/admin/reservation)``
- 예약 시간 추가 화면 ``(/admin/time)``
