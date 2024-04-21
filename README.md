# 방탈출 예약 관리

## 프로그램 설명

- 방탈출 예약을 관리할 수 있는 웹 애플리케이션을 만든다.
- 방탈출 예약을 조회, 추가, 취소할 수 있다.
- 방탈출 시간을 추가, 조회, 삭제할 수 있다.

## 1단계 기능 요구사항

- [x] `GET /localhost:8080/admin` 요청 시, 어드민 메인 페이지를 응답한다.

## 2단계 기능 요구사항

- [x] `GET /admin/reservation` 요청 시, 예약 관리 페이지를 응답한다.
- [x] `GET /reservations` 요청 시, 예약을 조회한다.

## 3단계 기능 요구사항

- [x] `POST /reservations` 요청 시, 예약을 추가한다.
- [x] `DELETE /reservations/{id}` 요청 시, 예약을 취소한다.

## 4단계 기능 요구사항

- [x] h2 데이터베이스를 연동한다.

## 5단계 기능 요구사항

- [x] 예약 조회 API 처리 로직에서 데이터베이스를 활용하도록 수정한다.

## 6단계 기능 요구사항

- [x] 예약 추가 API 처리 로직에서 데이터베이스를 활용하도록 수정한다.
- [x] 예약 취소 API 처리 로직에서 데이터베이스를 활용하도록 수정한다.

## 7단계 기능 요구사항

- [ ] `GET /admin/time` 요청 시, 시간 관리 페이지를 응답한다.
- [ ] `POST /times` 요청 시, 시간을 추가한다.
- [ ] `GET /times` 요청 시, 시간을 조회한다.
- [ ] `DELETE /times/{id}` 요청 시, 시간을 삭제한다.

---

## API

### 예약 조회 API

- Request
```
GET /reservations HTTP/1.1
```

- Response
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

### 예약 추가 API

- Request
```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

- Response
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

### 예약 취소 API

- Request
```
DELETE /reservations/1 HTTP/1.1
```

- Response
```
HTTP/1.1 200
```

### 시간 추가 API

- Request
```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

- Response
```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 조회 API

- Request
```
GET /times HTTP/1.1
```

- Response
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

- Request
```
DELETE /times/1 HTTP/1.1
```

- Response
```
HTTP/1.1 200
```
