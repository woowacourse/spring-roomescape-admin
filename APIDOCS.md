# :dart: API 정보

- GET /
    - 설명 : 웰컴 페이지 응답 (메인 페이지로 리다이렉션)
    - 정상 응답 (308)

- GET /admin
    - 설명 : 메인페이지 응답
    - 정상 응답 (200)
      ```
      메인 페이지 HTML 파일
      ```

- GET /admin/reservation
    - 설명 : 예약페이지 응답
    - 정상 응답 (200)
      ```
      예약 페이지 HTML 문서
      ```

- GET /reservations
    - 설명 : 예약 조회
    - 정상 응답 (200)
      ```
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
          },
          ...
      ]
      ```
- POST /reservations
    - 설명 : 예약 추가
    - 요청 파라미터
      ```
      {
          "date": "2023-08-05",  // NotNull
          "name": "브라운",       // NotNull, NotBlank
          "time": "15:40"        // NotNull
      }
      ```
    - 정상 응답 (201)
      ```
      {
          "id": 1,
          "name": "브라운",
          "date": "2023-08-05",
          "time": "15:40"
      }
      ```
    - 예외 응답 (400)
      - 필수 요청 파라미터가 입력되지 않은 경우
      - 비어있거나 공백 이름이 입력된 경우
- DELETE /reservations/{reservationId}
    - 설명 : 예약 취소
    - 정상 응답 (200)