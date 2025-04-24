# 방탈출 예약 관리 미션

## 📚 API 명세서

### 📅 예약 관련 API

**✅ 예약 전체 조회 API**

- Request

    ```json
    GET /reservations HTTP/1.1
    ```

- Response

    ```json
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


**✅ 예약 추가 API**

- Request

    ```json
    POST /reservations HTTP/1.1
    content-type: application/json
    
    {
        "date": "2023-08-05",
        "name": "브라운",
        "time": "15:40"
    }
    ```

- Response

    ```json
    HTTP/1.1 200 
    Content-Type: application/json
    
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-08-05",
        "time": "15:40"
    }
    ```


**✅ 예약 취소 API**

- Request

    ```json
    DELETE /reservations/1 HTTP/1.1
    ```

- Response

    ```json
    HTTP/1.1 200
    ```


---

### 🕒 예약 시간 관련 API

**✅ 예약 시간 조회 API**

- Request

    ```json
    GET /times HTTP/1.1
    ```

- Response

    ```json
    HTTP/1.1 200 
    Content-Type: application/json
    
    [
       {
            "id": 1,
            "startAt": "10:00"
        }
    ]
    ```


**✅ 예약 시간 추가 API**

- Request

    ```json
    POST /times HTTP/1.1
    content-type: application/json
    
    {
        "startAt": "10:00"
    }
    ```

- Response

    ```json
    HTTP/1.1 200
    Content-Type: application/json
    
    {
        "id": 1,
        "startAt": "10:00"
    }
    ```


**✅ 예약 시간 삭제 API**

- Request

    ```json
    DELETE /times/1 HTTP/1.1
    ```

- Response

    ```json
    HTTP/1.1 200
    ```
