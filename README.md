# 방 탈출 예약 관리 프로그램

### 화면 렌더링
- [x] 예약 메인 홈페이지 반환 기능 구현
- [x] 예약 페이지 반환 기능 구현

### API 명세
- [x] 예약 조회 API
  - Request
    - ```
      GET /reservations HTTP/1.1
      ```
      
  - Response
    - ```
      HTTP/1.1 200
      Content-Type: application/json
      
      [
          {
              "id": 1,"name": "브라운",
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
      
- [x] 예약 추가 API 
  - Request
    - ```
      POST /reservations HTTP/1.1
      content-type: application/json 
    
      {
      "date": "2023-08-05",
      "name": "브라운",
      "time": "15:40"
      }
      ```
      
  - Response
    - ```
      HTTP/1.1 200
      Content-Type: application/json
      
      {
      "id": 1,
      "name": "브라운",
      "date": "2023-08-05",
      "time": "15:40"
      }
      ```
      
- [x] 예약 취소 API
   - Request 
     - ```
       DELETE /reservations/1 HTTP/1.1
       ```
       
   - Response
     - ```
       HTTP/1.1 200
       ``` 
