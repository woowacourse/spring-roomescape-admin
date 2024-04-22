## 구현 기능 목록

- [x] `localhost:8080/admin` 요청 시 어드민 메인 페이지가 응답한다.
    - 어드민 메인 페이지 - `templates/admin/index.html`

- [x] `localhost:8080/admin/reservation` 요청 시 예약 관리 페이지가 응답한다.
    - 예약 관리 페이지 - `templates/admin/reservation.html`

- [x] `localhost:8080/admin/time` 요청 시 시간 관리 페이지가 응답한다.
    - 시간 관리 페이지 - `templates/admin/time.html`


- [x] DB에서 예약 목록을 읽어 올 수 있다. (예약 조회)
    - request
      ```
       GET /reservations HTTP/1.1
      ```
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

- [x] data, name, time을 받아서 DB에 예약을 추가 할 수 있다.
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
        HTTP/1.1 201
        Content-Type: application/json
        
        {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": "15:40"
        }
      ```

- [x] 삭제할 id를 받아서 DB에서 해당 예약을 삭제 할 수 있다.
    - request
      ```
      DELETE /reservations/1 HTTP/1.1
      ```
    - response
      ```
      HTTP/1.1 204
      ```

- [x] DB에서 시간 목록을 읽어 올 수 있다. (예약 조회)
    - request
      ```
       GET /times HTTP/1.1
      ```
    - response
      ```
      HTTP/1.1 200
      Content-Type: application/json
      [{
      "id": 1, 
      "startAt": "10:00" 
      }]
      ```

- [x] 시작 시간을 받아서 DB에 시간을 추가 할 수 있다.
    - request
      ```
      POST /times HTTP/1.1
      content-type: application/json
      {
      "startAt": "10:00"
      }
      ```
    - response
      ```
      HTTP/1.1 200
      Content-Type: application/json
      { 
      "id": 1, 
      "startAt": "10:00" 
      }
      ```

- [x] 삭제할 id를 받아서 DB에서 해당 시간을 삭제 할 수 있다.
    - request
      ```
      DELETE /times/1 HTTP/1.1
      ```
    - response
      ```
      HTTP/1.1 200
      ```

