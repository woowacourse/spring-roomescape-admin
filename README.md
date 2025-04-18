## 기능 요구사항

## View 반환

- [x] /admin 요청 시 어드민 메인 페이지()를 응답한다.
    - 페이지는 templates/admin/index.html 파일을 이용
- [x] /admin/reservation 요청 시 예약 관리 페이지를 응답한다.
    - 페이지는 templates/admin/reservation-legacy.html 파일을 이용

## API

### GET /reservations 요청시 등록된 예약 모두를 json으로 응답한다.

Response

```
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

### POST /reservations 요청시 예약을 등록한다.

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

### DELETE /reservations/1 요청시 예약을 삭제한다.

Request

```
DELETE /reservations/1 HTTP/1.1
```

Response

```text
HTTP/1.1 200
```