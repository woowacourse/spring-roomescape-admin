## 방탈출 예약관리

### 1단계 - 홈 화면

- localhost:8080/admin 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현.

### 2단계 - 예약 조회

- /admin/reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현
- 페이지 로드 시 호출되는 예약 목록 조회 API 구현

예약 조회 API

Request

```
GET /reservations HTTP/1.1
```

Response

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

### 3단계 - 예약 추가 / 취소

API 명세를 따라 예약 추가 API 와 삭제 API를 구현.

예약 추가 API

Request

```
POST /reservations HTTP/1.1

content-type: application/json

{
"date": "2023-08-05",
"name": "브라운",
"time": "15:40"
}
```

Response

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

예약 취소 API

Request

```
DELETE /reservations/1 HTTP/1.1
```

Response

```
HTTP/1.1 200
```

### 4단계 - 6단계

데이터를 저장하기 위해 h2 데이터베이스 연동
DB로부터 예약 조회
DB로부터 예약 추가
DB로부터 예약 삭제

### 7단계

/admin/time 요청 시 시간 관리 페이지를 응답한다.

API 명세에 따라 아래 기능 구현

시간 추가 API 구현

Request

```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

Response

```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

#### 시간 조회 API 구현

Request

```
GET /times HTTP/1.1
```

Response

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

#### 시간 삭제 API 구현

Request

```
DELETE /times/1 HTTP/1.1
```

Response

```
HTTP/1.1 200
```

8단계
방탈출 예약 시 시간 테이블에 저장된 시간만 선택할 수 있도록 수정

예약 추가/조회 API 명세 변경

예약 추가 API

Request

```
POST /reservations HTTP/1.1
content-type: application/json

{
"date": "2023-08-05",
"name": "브라운",
"timeId": 1
}
```

Response

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

예약 조회 API

Request

```
GET /reservations HTTP/1.1
```

Response

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

9단계

레이어드 아키텍쳐 적용
