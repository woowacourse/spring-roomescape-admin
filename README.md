# 방탈출 예약 관리

# 기능 구현 목록

### 기본 정보
   - 기본 주소는 localhost, port는 8080이다.  
      > localhost:8080
   - h2 데이터베이스를 활용한다.
      > http://localhost:8080/h2-console
      - 접속 후 URL = `jdbc:h2:mem:database` USER NAME = `sa`를 입력하여 연결
   
### 화면
   1. 홈 화면
      - `GET` `/admin` 요청 시 어드민 메인 페이지 응답
      - 어드민 페이지는 `templates/admin/index.html`
   2. 예약 화면
       - `GET` `/admin/reservation` 요청 시 아래 화면과 같이 예약 관리 페이지가 응답
       - 예약 페이지는 `templates/admin/reservation.html`
     
### 예약 API
   1. 예약 목록 조회 API
      - `GET` `/reservations` 요청 시 아래 응답 반환
      - Response
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
         
   2. 예약 추가 API
      - `POST` `/reservations` 로 Resquest와 함께 요청 시 Response 반환
      - Request
        ```
        {
           "date": "2023-08-05",
           "name": "브라운",
           "timeId": 1
        }
        ```
      - Response
        ```
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

   3. 예약 취소 API
      - `DELETE` `/reservations/{id}` 로 요청 시 성공 여부 반환

### 시간 API
   1. 시간 추가 API
      - `POST` `/times` 로 Resquest와 함께 요청 시 Response 반환
      - Request
        ```
        {
           "startAt": "10:00"
        }
        ```
      - Response
        ```
        {
           "id": 1,
           "startAt": "10:00"
        }
        ```
   2. 시간 조회 API
      - `GET` `/times` 로 Resquest와 함께 요청 시 Response 반환
      - Response
        ```
        [
           {
              "id": 1,
              "startAt": "10:00"
           }
        ]
        ```
   3. 시간 삭제 API
      - `DELETE` `/times/{id}` 로 Resquest와 함께 요청 시 Response 반환
