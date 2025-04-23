# 방탈출 예약 관리

# 기능 구현 목록

0. 기본 주소는 localhost:8080이다.

### 페이지

1. 홈 화면
    - `GET /admin` 요청 시 어드민 메인 페이지 응답
        - 어드민 페이지는 templates/admin/index.html

2. 예약 화면
    - `GET /admin/reservation` 요청 시 아래 화면과 같이 예약 관리 페이지 응답
        - 예약 페이지는 templates/admin/reservation.html

3. 시간 화면
    - `GET /admin/reservationTime` 요청 시 아래 화면과 같이 시간 관리 페이지 응답
        - 시간 페이지는 templates/admin/reservationTime.html

### 에약

1. 예약 목록 조회 API
   -`GET /reservations` 요청 시 아래 응답 반환
    - Response
        ```json
        [
            {
                "id": 1,
                "name": "브라운",
                "date": "2023-01-01",
                "reservationTime": "10:00"
            },
            {
                "id": 2,
                "name": "브라운",
                "date": "2023-01-02",
                "reservationTime": "11:00"
            }
        ]
        ```

2. 예약 추가 API
    - `POST /reservations` 로 Resquest와 함께 요청 시 Response 반환
    - Request
      ```json
      {
         "date": "2023-08-05",
         "name": "브라운",
         "reservationTime": "15:40"
      }
      ```
    - Response
      ```json
      {
         "id": 1,
         "name": "브라운",
         "date": "2023-08-05",
         "reservationTime": "15:40"
      }
      ```

3. 예약 취소 API
    - `DELETE /reservations/{id}` 요청 시 성공 여부 반환

### 시간

1. 시간 추가 API
    - `POST /reservationTimes` 요청 시 시간 추가 및 response 반환
    - Request
    ```json
    {
        "startAt": "10:00"
    }
    ```
    - Response
    ```json
    {
        "id": 1,
        "startAt": "10:00"
    }
    ```

2. 시간 삭제 API
    - `DELETE /reservationTimes/{id}` 요청 시 시간 삭제 및 response 반환
    - response
        - `204 NoContent`

3. 시간 조회 API
    - `GET /reservationTimes` 요청 시 전체 시간 response 반환
    - response
   ```json
   [
      {
        "id": 1,
        "startAt": "10:00"
      }
    ]
   ```
