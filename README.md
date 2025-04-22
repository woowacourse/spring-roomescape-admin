# 방 탈출 예약 관리

## 요구 사항

- [X] 프로젝트 관련 gradle 의존성 추가
- [X] 어드민 메인 페이지 `templates/admin/index.html` 파일을 이용하여 `localhost:8080/admin` 요청 시 메인 페이지가 응답
- [X] 예약 관리 페이지 `templates/admin/reservation-legacy.html`파일을 이용하여 `/admin/reservation` 요청 시 예약 관리 페이지가 응답 
- [X] 예약 목록 조회 API 구현
- [X] 예약 추가 API 구현
- [X] 예약 삭제 API 구현
- [X] H2 데이터베이스 연결
  - [X] 데이터 조회 기능 구현
  - [X] 데이터 추가 기능 구현
  - [X] 데이터 삭제 기능 구현
- [ ] 방탈출 시간을 사용자가 선택하는 방식으로 수정
  - [ ] `templates/admin/time.html` 파일 사용
- [ ] 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정
  - [ ] `templates/admin/reservation-legacy.html` 대신 `templates/admin/reservation.html` 파일로 변경
- [ ] 레이어드 아키텍처를 적용하여 레이어별 책임과 역할 분리
---
# API 명세

## 예약 조회 API
### Request

```
GET /reservations HTTP/1.1
```
### Response

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

## 예약 추가 API
### Request
```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

### Response
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

## 예약 취소 API

### Request
```
DELETE /reservations/1 HTTP/1.1
```

### Response
```
HTTP/1.1 200
```

## 시간 추가 API 

### Request

```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

### Response

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

## 시간 조회 API

### Request

```
GET /times HTTP/1.1
```

### Response

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

## 시간 삭제 API

### Request

```
DELETE /times/1 HTTP/1.1
```

### Response

```
HTTP/1.1 200
```


