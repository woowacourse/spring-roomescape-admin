# 방탈출 예약 관리 시스템 🚪

## 📌 소개

- 방탈출 예약을 관리할 수 있는 웹 애플리케이션입니다.
- 예약 시간 관리 기능을 제공합니다.
- 사용자 친화적인 UI/UX를 제공합니다.

## 📌 기능

- 예약 전체 조회
- 예약 추가
- 예약 삭제

## 📌 프로젝트 구조

```
📦java
 ┗ 📂roomescape
 ┃ ┣ 📂controller
 ┃ ┃ ┣ 📜AdminViewController.java
 ┃ ┃ ┗ 📜ReservationApiController.java
 ┃ ┣ 📂domain
 ┃ ┃ ┣ 📜Reservation.java
 ┃ ┃ ┗ 📜Reservations.java
 ┃ ┣ 📂dto
 ┃ ┃ ┣ 📜ReservationRequest.java
 ┃ ┃ ┗ 📜ReservationResponse.java
 ┃ ┗ 📜RoomescapeApplication.java
```

## 📌 API 명세

| Method | URL                  | Description |
|--------|----------------------|-------------|
| GET    | api/reservation      | 예약 전체 조회    |
| POST   | api/reservation      | 예약 추가       |
| DELETE | api/reservation/{id} | 예약 삭제       |

## 📝 API 상세 설명

### 예약 전체 조회 (GET `api/reservation`)

- 응답 예시

```
[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
    }
    {
        "id": 2,
        "name": "제임스",
        "date": "2023-01-02",
        "time": "11:00"
    }
]
```

### 예약 추가 (POST `api/reservation`)

- 요청 예시

```
{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

- 응답 예시

```
{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

### 예약 삭제 (DELETE `api/reservation/{id}`)

- 요청 예시

```
  DELETE /reservations/1 HTTP/1.1
```

- 응답 예시
    - 성공
      ```
        HTTP/1.1 200
      ```
    - 실패
      ```
        HTTP/1.1 404
      ```
