# 기능 요구 사항

### 도메인 기능 요구 사항

- [x] 예약을 생성한다.
    - id, 예약자 이름, 예약 날짜와 시간은 `null`일 수 없다.
    - 예약자 이름은 공백(empty string 포함)일 수 없다.
- [ ] 예약 시간을 생성한다

### 데이터베이스 기능 요구 사항
- [x] DB에 `reservation` 테이블을 추가한다.
- [x] 모든 예약을 조회한다.
- [x] 예약을 저장한다.
- [x] 예약번호로 예약을 삭제한다.
- [x] 예약번호로 예약을 조회한다.
- [ ] 예약 시간을 저장한다.
- [ ] 예약 시간을 모두 조회한다.
- [ ] 예약 시간 번호로 예약 시간을 삭제한다.

### API 기능 요구 사항

- [x] 페이지 접속 시 welcome page를 응답한다.
    - `localhost:8080/`: welcome page
- [x] admin 요청 시 메인 페이지를 응답한다.
    - `localhost:8080/admin`: admin/index page
- [x] 예약 관리 페이지가 응답한다.
    - `/admin/reservation`: admin/reservation-legacy page
- [ ] 시간 관리 페이지를 응답한다.
  - `/admin/time`: templates/admin/time page
- [x] 예약 관리 페이지 로드 시 예약 목록을 조회한다.
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
- [x] 예약을 추가한다.
    - request
        ``` http request
        POST /reservations HTTP/1.1
        content-type: application/json
        
        {
            "date": "2023-08-05",
            "name": "브라운",
            "time": "15:40"
        }
        ```
    - response
        ```http request
        HTTP/1.1 201
        Content-Type: application/json
        
        {
            "id": 1,
            "name": "브라운",
            "date": "2023-08-05",
            "time": "15:40"
        }
        ```
    - 예약 정보가 존재하지 않으면 예약을 추가할 수 없다
        - request
            ```http request
            POST /reservations HTTP/1.1
            content-type: application/json
            {
                "date": "",
                "name": "브라운",
                "time": "15:40"
            }
            ```
        - response
            ```http request
            HTTP/1.1 400
            ```
- [x] 예약을 삭제한다.
    - request
        ```http request
        DELETE /reservations/1 HTTP/1.1
        ```
    - response
        ```http request
        HTTP/1.1 200
        ```
    - 존재하지 않는 예약을 삭제할 수 없다
        - request
            ```http request
            DELETE /reservations/100 HTTP/1.1
            ```
        - response
            ```http request
            HTTP/1.1 404
            ```
    - 잘못된 예약번호를 삭제할 수 없다
        - request
            ```http request
            DELETE /reservations/one HTTP/1.1
            ```
        - response
            ```http request
            HTTP/1.1 400
            ```
- [ ] 시간을 추가한다.
  - request
    ```http request
    POST /times HTTP/1.1
    content-type: application/json

    {
        startAt": "10:00"
    }
    ```
  - response
    ```http request
    HTTP/1.1 200
    Content-Type: application/json
    
    {
        "id": 1,
        "startAt": "10:00"
    }
    ```
- [ ] 모든 시간을 조회한다.
    - request
      ```http request
      GET /times HTTP/1.1
      ```
    - response
      ```http request
      HTTP/1.1 200
      Content-Type: application/json
      
      [
          {
              "id": 1,
              "startAt": "10:00"
          }
      ]
      ```
- [ ] 시간을 삭제한다.
    - request
      ```http request
      DELETE /times/1 HTTP/1.1
      ```
    - response
      ```http request
      HTTP/1.1 200
      ```
