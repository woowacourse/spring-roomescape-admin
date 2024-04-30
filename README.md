# 기능 구현 목록

## step1

- [X] : localhost:8080/admin 요청 시 어드민 메인 페이지가 응답해야한다.

## step2

- [X] : /admin/reservation 요청 시 예약 관리 페이지가 응답해야한다.
- [X] : API 명세에 따라 예약 목록 조회 API 를 구현한다.

## step3

- [X] : 예약 추가 API 구현한다.
- [X] : 예약 취소 API 구현한다.

## step7

- [x] : /admin/time 요청 시 시간 관리 페이지가 응답해야 한다.
- [x] : 시간 관리 페이지
    - [x] : 시간 조회 API 구현한다.
    - [x] : 시간 추가 API 구현한다.
    - [x] : 시간 삭제 API 구현한다.

***

# API 명세

## 예약 관리

### 예약 관리 페이지 호출

**Request<br>**

```http request
GET /admin/reservation HTTP/1.1
```

**Response<br>**

```
templates/admin/reservation-legacy.html
```

### 예약 조회 API

**Request<br>**

```
GET /reservations HTTP/1.1
```

**Response<br>**

```
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

**Request**<br>

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

Response<br>

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

### 예약 취소 API

Request<br>

```
DELETE /reservations/1 HTTP/1.1
```

Response<br>

```
HTTP/1.1 200
```

## 시간 관리

### 시간 조회 API

Request<br>

```
GET /times HTTP/1.1
```

Reponse<br>

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

### 시간 추가 API

Request<br>

```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

Response<br>

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 삭제 API

Request<br>

```http request
HTTP/1.1 200
```

Response<br>

```
CREATE TABLE reservation_time
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
)
```
