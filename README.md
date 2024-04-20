# 방탈출 예약 관리

## API 명세서

### 페이지 조회

| Path               | 페이지 설명           |
|--------------------|------------------|
| /admin             | 어드민 메인 페이지       |
| /admin/reservation | 어드민 예약 관리 페이지    |
| /admin/time        | 어드민 예약 시간 관리 페이지 |

### REST API

#### 예약 관리

- 예약 전체 조회
  - 요청
    ```text
    GET /reservations
    ```
  - 응답
    ```text
    200 OK
    
    [
      {
        "id": 1,
        "name": "2023-08-05",
        "date": "브라운",
        "time": {
          "id": 1,
          "startAt": "15:00"
        }
      }, 
      {
        "id": 2,
        "name": "2023-08-20",
        "date": "브리",
        "time": {
          "id": 1,
          "startAt": "15:00"
        }
      }
    ]
    ```
- 예약 생성
  - 요청
    ```text
    POST /reservations
    
    {
      "date" : "2023-08-20",
      "name" : "브리",
      "timeId" : 1
    }
    ```
  - 응답
    ```text
    201 Created
    
    {
      "id": 2,
      "name": "2023-08-20",
      "date": "브리",
      "time": {
        "id": 1,
        "startAt": "15:00"
      }
    }
    ```
- 예약 삭제
  - 요청
    ```text
    DELETE /reservations/2
    ```
  - 응답
    ```text
    204 No Content
    ```


#### 예약 시간 관리

- 예약 시간 전체 조회
  - 요청
    ```text
    GET /times
    ```
  - 응답
    ```text
    200 OK
    [
      {
        "id" : 1,
        "startAt" : "15:00"
      },
      {
        "id" : 2,
        "startAt" : "16:00"
      }
    ]
    ```
- 예약 시간 생성
  - 요청
    ```text
    POST /reservations
    
    {
      "startAt" : "16:00"
    }
    ```
  - 응답
    ```text
    201 Created
    
    {
      "id" : 2
      "startAt" : "16:00"
    }
    ```
- 예약 시간 삭제
  - 요청
    ```text
    DELETE /reservations/2
    ```
  - 응답
    ```text
    204 No Content
    ```
