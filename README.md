# 방탈출 예약 관리 프로그램

## 화면 렌더링
- [x] 예약 메인 홈페이지 반환 기능 구현
- [x] 예약 페이지 반환 기능 구현

## API 명세
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
      HTTP/1.1 201
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
       HTTP/1.1 204
       ``` 

## 데이터베이스 설정
- [x] JdbcTemplate 및 H2 의존성 추가
- [x] reservation 테이블 스키마 정의
- [x] h2-console 기능 활성화

## 데이터베이스 연동
- [x] 애플리케이션에 H2 데이터베이스 연동
