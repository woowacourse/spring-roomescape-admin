# 방 탈출 예약 관리 프로그램

## 프로그램 설명

방 탈출 예약을 관리하는 프로그램으로 예약자, 날짜, 시간으로 예약을 추가 / 삭제 합니다

## View
- [x] welcome page 페이지
    ```markdown
    HTTP Method : GET
    URL : /
    설명 : welcome page 페이지 응답
    ```

- [x] admin 페이지
    ```markdown
    HTTP Method : GET
    URL : /admin
    설명 : 요청 시, admin 페이지 응답
    ``` 

- [x] reservation 페이지
    ```markdown
    HTTP Method : GET
    URL : /admin/reservation
    설명 : reservation 페이지 응답
    ``` 

## API

- [x] 예약 조회
    ```markdown
    HTTP Method : GET
    URL : /reservations
    설명 : 등록된 모든 예약 목록을 조회합니다.
    ```
    
    - Request 예시
    ```markdown
    GET /reservations HTTP/1.1
    ```
    
    - Response 예시
    ```markdown
    HTTP/1.1 200 OK  
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

- [x] 예약 추가
    ```markdown
    HTTP Method : POST
    URL : /reservations
    설명 : 예약 정보를 추가합니다.
    ```
    
    - Request 예시  
    ```markdown
    POST /reservations HTTP/1.1
    content-type: application/json
    
    {
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
    }
    ```

    - Response 예시
    ```markdown
    
    HTTP/1.1 200 OK
    Content-Type: application/json
    
    {
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
    }
    ```

- [x] 예약 삭제
    ```markdown
    HTTP Method : DELETE
    URL : /reservations/{id}
    설명 : 예약 번호에 해당하는 예약 정보를 삭제합니다.
    ```
    
    - Request 예시
    ```markdown
    DELETE /reservations/1 HTTP/1.1
    ```
    
    - Response 예시
    ```markdown
    HTTP/1.1 200 OK
    ```


