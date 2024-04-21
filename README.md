# 방탈출 예약 관리 시스템

## 요구사항 분석

- [x] 메인 페이지 화면 구현 
  - 어드민 메인 페이지(`templates/admin/index.html`)를 응답한다.
- [x] 예약 조회 화면 구현
  - 예약 조회 페이지(`templates/admin/reservation-legacy.html`)를 응답한다.
- [x] 예약 목록 조회
- [x] 예약 추가
- [x] 예약 취소

## API 명세

### 메인 페이지 조회

#### Request

```http request
GET /admin HTTP/1.1
```

#### Response

```
HTTP/1.1 200 
```

### 예약 페이지 조회

#### Request

```http request
GET /admin/reservation HTTP/1.1
```

#### Response

```
HTTP/1.1 200 
```

### 예약 조회

#### Request

```http request
GET /reservations HTTP/1.1
```

#### Response

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

### 예약 추가

#### Request

```http request
POST /reservations HTTP/1.1
content-type: application/json

{
"date": "2023-08-05",
"name": "브라운",
"time": "15:40"
}
```

#### Response

```
HTTP/1.1 201 
Content-Type: application/json
Location : /reservations/${created-reservation-id}
{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

### 예약 취소

#### Request

```http request
DELETE /reservations/1 HTTP/1.1
```

#### Response

```
HTTP/1.1 204
```
