# 구현할 기능 목록

- [x] `/admin` GET 요청 시 어드민 메인 페이지가 응답
- [x] `/admin/reservation` 요청 시 예약 관리 페이지가 응답
  - [x] `templates/admin/reservation-legacy.html` 파일 사용
- [x] 예약 조회 기능 구현
  ```
  GET /reservations HTTP/1.1
  
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
- [x] 예약 추가 기능 구현
    ```    
    POST /reservations HTTP/1.1
    
    content-type: application/json
    
    {
        "date": "2023-08-05",
        "name": "브라운",
        "time": "15:40"
    }
    
    HTTP/1.1 200 
    Content-Type: application/json
    
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": "15:40"
    }
    ```
- [x] 예약 취소 기능 구현
    ```
    DELETE /reservations/1 HTTP/1.1
    
    HTTP/1.1 200
    ```
- [x] h2 데이터베이스 연결
  - [x] console 기능 활성화
  - [x] datasource url을 `jdbc:h2:mem:database` 로 설정
