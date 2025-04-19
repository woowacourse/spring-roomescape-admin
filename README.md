# 방탈출 예약 관리

## 방탈출 예약을 관리할 수 있는 웹 애플리케이션을 만든다.

## 기능 목록

### 관리 페이지

- [x] localhost:8080/admin 요청 시 어드민 메인 페이지가 응답한다.
  - [x] templates/admin/index.html 파일을 이용한다.

### 예약 관리 페이지

- [x] /admin/reservation 요청 시 예약 관리 페이지가 응답한다.
  - [x] templates/admin/reservation-legacy.html 파일을 이용한다.

### 예약 조회

- [x] 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API를 구현한다.
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

- [x] 예약 추가 API를 구현한다.
  - Request
    ``` 
      POST /reservations HTTP/1.1
      content-type: application/json
      
      {
        "date": "2023-08-05",
        "name": "브라운",
        "time": "15:40"
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
      "time": "15:40"
    }
    ```

### 예약 취소

- [x] 예약 취소 API를 구현한다.
  - Request
    ``` 
    DELETE /reservations/1 HTTP/1.1
    ```
  - Response
    ```
    HTTP/1.1 200
    ```
