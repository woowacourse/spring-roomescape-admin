# 방탈출 예약 관리 프로그램

## 1단계

- [ ] localhost:8080/admin 요청 시 어드민 메인 페이지(templates/admin/index.htm)를 응답한다.

## 2단계

- [ ] /admin/reservation 요청 시 예약 관리 페이지(templates/admin/reservation-legacy.html)가 응답한다.
- [ ]  API 명세에 따라 아래 기능 구현
- [ ] 예약 확인 API 구현
    - [ ] Request
    ```
    GET /reservations HTTP/1.1
    ```

    - [ ] Response
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

## 3단계

- [ ] API 명세에 따라 아래 기능 구현
    - [ ] 예약 추가 API 구현
        - [ ] Request
        ```
        POST /reservations HTTP/1.1
        content-type: application/json
      
        {
        "date": "2023-08-05",
        "name": "브라운",
        "time": "15:40"
        }
        ```

        - [ ] Response
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

    - [ ] 예약 삭제 API 구현
        - Request
        ```
        DELETE /reservations/1 HTTP/1.1
        Response
        HTTP/1.1 200
        ```
