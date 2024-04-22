## API 명세서

### 어드민 메인 페이지
- http method: GET
- uri: /admin
- file path: templates/admin/index.html

### 어드민 예약 페이지 접근
- http method: GET
- uri: /admin/reservation
- file path: templates/admin/reservation-legacy.html

### 어드민 시간 페이지 접근
- http method: GET
- uri: /admin/time
- file path: templates/admin/time.html

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
  - 추가 성공
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
  - 추가 실패 : 이름 길이 오류
  ```
  HTTP/1.1 400 
  Content-Type: application/json
  
  {
      "message": "이름은 1자 이상, 5자 이하여야 합니다.";
  }
  ```
  - 추가 실패 : 일정 오류
  ```
  HTTP/1.1 400 
  Content-Type: application/json
  
  {
      "message": "현재보다 이전으로 일정을 설정할 수 없습니다.";
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
  - 존재하지 않는 id(0)로 삭제 요청
  ```
  HTTP/1.1 400
  
  "존재하지 않는 id입니다. id: 0"
  ```
  
### 시간 추가
- http method: POST
- uri: /times
- request
```
POST /times HTTP/1.1
Content-Type: application/json
 
{
    "startAt": "10:00"
}
```
- response
  - 추가 성공
  ```
  HTTP/1.1 200 
  Content-Type: application/json
  
  {
      "id": 1,
      "startAt": "10:00"
  }
  ```

### 시간 조회
- http method: GET
- uri: /times
- response
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

### 시간 삭제
- http method: DELETE
- uri: /times/{id}
  - path variable
    - id: 시간 정보 식별자
- response
  - 존재하는 id로 삭제 요청
  ```
  HTTP/1.1 200
  ```
  - 존재하지 않는 id(0)로 삭제 요청
  ```
  HTTP/1.1 400
  
  "존재하지 않는 id입니다. id: 0"
  ```

## 기능 명세서

### 예약
- [x] 예약 정보는 식별자, 이름, 일정으로 이뤄져있다.
  - [x] 이름은 1자 이상, 5자 이하여야 한다.
  - [x] 일정은 현재 이후여야 한다.

### 시간
- [ ] 시간 정보는 식별자, 시작하는 시간으로 이뤄져있다.
