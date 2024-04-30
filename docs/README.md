## 요구사항 정리

## 1단계

- [x] 웹 관련 의존성 추가
- [x] welcome page 응답

## 2단계

- [x] 예약 페이지 요청 처리하는 메서드 구현
  - [x] reservation-legacy 페이지 응답
- [x] 예약 목록 조회 요청을 처리하는 메서드를 구현

## 3단계

- [x] 예약 추가 API 구현
- [x] 예약 삭제 API 구현

## 4단계

- [x] h2 데이터베이스 연동
  - [x] gradle 의존성 추가
  - [x] 데이터베이스 설정

## 5-6단계

- [x] API 응답시 데이터베이스 기반으로 동작하도록 변경

## 7단계

- [x] 예약시간 관리 페이지 응답 API 구현
- [x] 예약시간 추가 API 구현
- [x] 예약시간 조회 API 구현
- [x] 예약시간 삭제 API 구현

## 8단계
- [x] 예약 페이지 파일 수정
- [x] 예약 추가 API 수정
- [x] 예약 조회 API 수정

## API 명세

### 예약 추가 API

**Request**
```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

**Response**
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

**Request**
```
GET /reservations HTTP/1.1
```

**Response**
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

### 예약 삭제 API
**request**
```
DELETE /reservations/1 HTTP/1.1
```

**response**
```
HTTP/1.1 200
```

### 시간 추가 API

**Request**
```
POST /times HTTP/1.1
content-type: application/json

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

### 시간 조회 API

**Request**
```
GET /times HTTP/1.1
```

**Response**
```
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
**request**
```
DELETE /times/1 HTTP/1.1
```

**response**
```
HTTP/1.1 200
```
