# 기능 요구 사항
localhost:8080/admin 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
어드민 메인 페이지는 templates/admin/index.html 파일을 이용하세요.
- [x] admin 요청 시 메인 페이지를 응답한다
  - 200 OK
- [x] `/admin/reservation` 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- [x] 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.
  - request `GET /reservations HTTP/1.1`
  - response
```http request
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
