# 방탈출 API

| Method | Endpoint             | Description  |
|--------|----------------------|--------------|
| GET    | `/admin`             | 어드민 페이지 요청   |
| GET    | `/admin/reservation` | 예약 관리 페이지 요청 |
| GET    | `/reservations`      | 예약 정보        |
| POST   | `/reservations`      | 예약 추가        |
| DELETE | `/reservations/{id}` | 예약 취소        |

# 방탈출 예약 도메인 명세

## Name

- [x] 예약자 이름은 null이거나 빈 문자열일 수 없다.
- [x] 예약자 이름은 1~5사이의 길이를 가져야 한다.

## ReservationTime

- [x] 예약하려는 시간에 다른 예약이 존재한다면 예약이 불가능하다.
- [x] 예약 시간은 예약 시작 시간으로부터 한 시간의 길이를 가진다.
