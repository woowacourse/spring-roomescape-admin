### /admin

- **GET /**: `admin/index` 페이지 렌더링
- **GET /reservation**: `admin/reservation` 페이지 렌더링
- **GET /test**: `admin/time` 페이지 렌더링

### /reservations

- **GET /**: 모든 예약 정보 조회
- **POST /**: 예약 생성

    ```json
    {
      "date": "2025-04-02",
      "name": "미소",
      "timeId": 1
    }
    ```

- **DELETE /{id}**: 예약 삭제

### /times

- **GET /**: 모든 시간 정보 조회
- **POST /**: 시간 생성

    ```json
    {
      "startAt": "10:00"
    }
    ```

- **DELETE /{id}**: 시간 삭제  
