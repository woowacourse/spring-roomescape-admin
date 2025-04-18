# 방탈출 예약 관리

# 기능 구현 목록

0. 기본 주소는 localhost:8080이다.

1. 홈 화면
    - GET /admin 요청 시 어드민 메인 페이지 응답
        - 어드민 페이지는 templates/admin/index.html

2. 예약 화면
    - GET /admin/reservation 요청 시 아래 화면과 같이 예약 관리 페이지가 응답
        - 예약 페이지는 templates/admin/reservation-legacy.html
      
3. 예약 목록 조회 API
   - GET /reservations 요청 시 아래 응답 반환
   - Response
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
         
4. 예약 추가 API
   - POST /reservations 로 Resquest와 함께 요청 시 Response 반환
   - Request
     ```
     {
        "date": "2023-08-05",
        "name": "브라운",
        "time": "15:40"
     }
     ```
   - Response
     ```
     {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": "15:40"
     }
     ```

5. 예약 취소 API
   - DELETE /reservations/{id} 로 요청 시 성공 여부 반환
