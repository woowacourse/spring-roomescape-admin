# 방탈출 예약 관리

## 🚀 1단계 - 홈 화면

- `localhost:8080` 요청 시 welcome 페이지가 응답할 수 있도록 구현한다.
    - welcome 페이지는 `static/index.html` 파일을 이용한다.


- `localhost:8080/admin`요청 시 어드민 메인 페이지가 응답할 수 있도록 구현한다.
    - 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용한다.

## 🚀 2단계 - 예약 조회

- `/admin/reservation`요청 시 예약 관리 페이지가 응답할 수 있도록 구현한다.
    - 예약 관리 페이지는 `templates/admin/reservation-legacy.html`파일을 이용한다.


- API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API를 구현한다.

**예약 조회 API**

```http request
// Request
GET /reservations HTTP/1.1

// Response
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

## 🚀 3단계 - 예약 추가 / 취소

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현한다.

**예약 추가 API**

```http request
// Request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

// Response
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

**예약 삭제 API**

```http request
// Request
DELETE /reservations/1 HTTP/1.1

// Response
HTTP/1.1 200
```