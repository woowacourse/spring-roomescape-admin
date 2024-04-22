# 방탈출 예약 관리

## 페어와 지킨 컨벤션

- 클래스를 정의한 뒤 다음 줄은 공백으로 한다.

## 도메인 분석

- 과거의 시간을 예약할 수 없다.
- 시간을 중복하여 등록할 수 없다.

## 1단계 홈 화면

- [x] Gradle 의존성 추가
- [x] 홈 화면 응답
    - `/admin`으로 요청하면 `admin/index.html`을 응답한다.

## 2단계 예약 조회

- [x] 예약 관리 페이지 응답
    - `/admin/reservation`으로 요청하면 `/admin/reservation.html`을 응답한다.
- [x] 전체 예약 조회 API를 구현한다.
    - GET `/reservations`
    - Response
      ```json
        [
          {
            "id": 1,
            "name": "홍길동",
            "date": "2021-07-01",
            "timeSlot": {
                "id": 1,
                "startAt": "10:00"
            }
          }
        ]
        ```
- [x] 예약 도메인 추가
    - 이름, 날짜, 시간을 가진다.

## 3단계 예약 추가 / 취소

- [x] 예약 추가 API를 구현한다.
    - POST `/reservations`
    - Request
      ```json
        {
          "name": "웨지",
          "date": "2024-07-01",
          "timeSlotId": 1
        }
        ```
    - Response
      ```json
        {
          "id": 1,
          "name": "웨지",
          "date": "2024-07-01",
          "timeSlot": {
              "id": 1,
              "startAt": "10:00"
          }
        }
        ```
- [x] 예약 삭제 API를 구현한다.
    - DELETE `/reservations/{id}`
    - Response `HTTP/1.1 204 No Content`

## 7단계 시간 관리 기능

- [x] `/admin/time`으로 요청하면 `/admin/time.html`을 응답한다.
- [x] 시간 추가 API를 구현한다.
    - POST `/times`
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
- [x] 전체 시간 조회 API를 구현한다.
    - GET `/times`
    - Response
      ```json
        [
          {
            "id": 1,
            "startAt": "10:00"
          }
        ]
        ```
- [x] 시간 삭제 API를 구현한다.
    - DELETE `/times/{id}`
    - Response `HTTP/1.1 204 No Content`

## 8단계 예약과 시간 관리

- [x] 예약과 시간 간의 연관관계 추가
- [x] 추가된 시간으로만 예약할 수 있도록 수정
