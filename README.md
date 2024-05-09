# 기능 명세
- 방탈출 예약을 관리할 수 있는 웹 애플리케이션의 어드민 페이지를 만든다.
- `/admin` 요청 시 어드민 페이지를 응답한다.
- `/admin/reservation` 요청 시 예약 관리 페이지를 응답한다.
- `/admin/time` 요청 시 시간 관리 페이지를 응답한다.
- 어드민은 예약을 추가, 조회, 삭제할 수 있다.
  - 어드민이 지정한 시간만을 이용하여 예약할 수 있다.
- 어드민은 시간을 추가, 조회, 삭제할 수 있다.

# 콘솔 애플리케이션
- 콘솔로도 같은 기능을 하는 애플리케이션을 실행할 수 있다.
- 데이터베이스는 메모리에 저장되며, 애플리케이션이 종료되면 데이터가 모두 초기화된다.

### 명령어
- `help`: 모든 명령어 목록을 조회한다.
- `show reservation`: 전체 예약을 조회한다.
- `show time`: 전체 시간을 조회한다.
- `create reservation 이름 날짜 시간번호`: 예약을 생성한다..
  - 예시) `create reservation Jay 2025-01-01 1` 
- `create time 시간`: 예약 가능한 시간을 추가한다..
  - 예시) `create time 23:00`
- `delete reservation 예약번호`: 예약을 삭제한다..
- `delete time 시간번호`: 시간을 삭제한다.

# API 명세
## 예약 조회 API
### Request
```http
GET /reservations HTTP/1.1
```

### Response
```http
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
```http
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

### Response
```http
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
```http
DELETE /reservations/1 HTTP/1.1
```
### Response
```http
HTTP/1.1 200
```

## 시간 조회 API
### Request
```http
GET /times HTTP/1.1

```
### Response
```http
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

## 시간 추가 API
### Request
```http
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```
### Response
```http
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

## 시간 삭제 API
### Request
```http
DELETE /times/1 HTTP/1.1
```
### Response
```http
HTTP/1.1 200
```
