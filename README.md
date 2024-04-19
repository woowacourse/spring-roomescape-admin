## API 명세서

### 어드민 메인 페이지
- http method: GET
- uri: /admin
- file path: templates/admin/index.html

### 어드민 예약 페이지 접근
- http method: GET
- uri: /admin/reservation
- file path: templates/admin/reservation-legacy.html

### 모든 예약 조회
- http method: GET
- uri: /reservations
- response
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
- http method: POST
- uri: /reservations
- request
```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```
- response
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

### 예약 삭제
- http method: DELETE
- uri: /reservations/{id}
  - path variable
    - id: 예약 정보 식별자
- response
  - 존재하는 id로 삭제 요청
  ```
  HTTP/1.1 200
  ```
  - 존재하지 않는 id로 삭제 요청
  ```
  HTTP/1.1 404
  ```

## 기능 명세서

### 예약
- [ ] 예약 정보는 식별자, 이름, 일정으로 이뤄져있다.
  - [ ] 이름은 1자 이상, 5자 이하여야 한다.
  - [ ] 일정은 현재 이후여야 한다.
