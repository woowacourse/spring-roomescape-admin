# spring-roomescape-admin

## 1. 기능 요구 사항

- localhost:8080/admin 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현한다.
- /admin/reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현한다.
- /admin/time 요청 시 시간 관리 페이지가 응답할 수 있도록 구현한다.
- API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현한다.
- API 명세를 따라 예약 추가 API와 삭제 API를 구현한다.
- API 명세를 따라 시간 관리 페이지 로드 시 호출되는 시간 목록 조회 API도 함께 구현한다.
- API 명세를 따라 시간 추가 API와 삭제 API를 구현한다. 

## 2. 요구 사항 목록

- [x] 어드민 페이지를 응답한다.
- [x] 예약 페이지를 응답한다.
- [x] 예약 목록을 조회한다.
- [x] 예약을 추가한다.
- [x] 예약을 삭제한다.
- [x] 예약 시간 목록을 조회한다.
- [x] 예약 시간을 추가한다.
- [x] 예약 시간을 삭제한다.

## 3. API 명세

### 예약 조회
- Request
```http request
GET /reservations HTTP/1.1
```
- Response
```http request
HTTP/1.1 200
Content-Type: application/json
```
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

### 예약 추가
- Request
```http request
POST /reservations HTTP/1.1
content-type: application/json
```
```json
{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```
- Response
```http request
HTTP/1.1 201
Content-Type: application/json
```
```json
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

### 예약 취소
- Request
```http request
DELETE /reservations/1 HTTP/1.1
```
- Response
```http request
HTTP/1.1 204
```

### 예약 시간 조회
- Request
```http request
GET /times HTTP/1.1
```
- Response
```http request
HTTP/1.1 200 
Content-Type: application/json
```
```json
[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

### 예약 시간 추가
- Request
```http request
POST /times HTTP/1.1
content-type: application/json
```
```json
{
    "startAt": "10:00"
}
```
- Response
```http request
HTTP/1.1 201
Content-Type: application/json
```
```json
{
    "id": 1,
    "startAt": "10:00"
}
```

### 예약 시간 삭제
- Request
```http request
DELETE /times/1 HTTP/1.1
```
- Response
```http request
HTTP/1.1 204
```