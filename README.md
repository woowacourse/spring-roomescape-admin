# spring-roomescape

# 구현 요구사항

1. 홈 화면
    - [x] localhost:8080/admin 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답
    - [X] 필요한 의존성을 찾아서 추가
    -
2. 예약 조회
    - [x] /admin/reservation 요청 시 예약 관리 페이지가 응답
    - [x] Thymeleaf 템플릿엔진을 활용하여 페이지를 응답
    - [x] /admin/reservation 요청 시 templates/admin/reservation-legacy.html을 응답
    - [x] 예약 페이지 요청과 예약 목록 조회 요청을 처리하는 메서드를 구현
    - [x] 별도의 데이터베이스가 없기 때문에 예약 목록을 아래와 같이 처리

3. 예약 추가/취소
    - [x] API 명세를 따라 예약 추가 API 와 삭제 API를 구현
    - [x] 추가/취소 API 요청과 응답을 처리하는 컨트롤러 메서드 구현을 위해서는 Spring MVC가 제공하는 Annotation을 잘 활용

4. 데이터베이스 적용
    - [x] 예약 스키마 정의
    - [x] 조회, 추가, 취소에 대해서 DB 적용

5. 예약 시간 추가, 조회, 삭제 구현
    - [x] API 명세를 따라 예약 시간 추가, 조회, 삭제 API 구현
    - [x] 예약 시간에 대해 DB 적용

6. 예약시 시간 테이블에 저장된 값만 선택할 수 있도록 구현
    - [] 예약, 시간 관리 API 수정
    - [] 예약 페이지 파일 수정
    - [] 테이블 스키마 제장의
    - [] 예약 클래스 수정
    - [] 예약 추가 쿼리 수정
    - [] 예약 조회 쿼리 수정

### API 명세

1. 예약 조회
    - Request
   ```
   GET /reservations HTTP/1.1
   ```
    - Response
   ```
   HTTP/1.1 200
   Content-Type: application/json

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

2. 예약 추가
    - Request
   ```
   POST /reservations HTTP/1.1
   content-type: application/json 
   
   {
       "date": "2023-08-05",
       "name": "브라운",
       "timeId": 1
   }
   ```
    - Response
   ```
   HTTP/1.1 200 
   Content-Type: application/json
       
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
3. 예약 취소
    - Request
   ```
   DELETE /reservations/1 HTTP/1.1
   ```
    - Response
   ```
   HTTP/1.1 200
   ```

4. 예약 시간 추가
    - Request
   ```
   POST /times HTTP/1.1
   content-type: application/json

   {
       "startAt": "10:00"
   }
   ```
   
   - Response
   ```
   HTTP/1.1 200
   Content-Type: application/json

   {
       "id": 1,
       "startAt": "10:00"
   }
   ```

5. 예약 시간 조회
   - Request
   ```
   GET /times HTTP/1.1
   ```
   
   - Response
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
   
6. 예약 시간 삭제
   - Request
   ```
   DELETE /times/1 HTTP/1.1
   ```
   
   - Response
   ```
   HTTP/1.1 200
   ```
