# 🗝️방탈출 예약 관리🗝️

## 기능 명세서

### 관리자 메인 페이지

- `/admin` 으로 접속할 수 있다.
- 관리자 페이지를 볼 수 있다.
- 네비게이션 바의 Reservation을 누르면 관리자 예약 페이지로 이동한다.

### 예약 관리 페이지

- `/admin/reservation` 으로 접속할 수 있다.
- 예약 목록을 볼 수 있다.
    - 예약 번호, 이름, 날짜, 시간을 볼 수 있다.
- 예약을 추가할 수 있다.
    - 이름, 날짜, 시간을 입력하여 추가한다.
- 예약을 삭제할 수 있다.

## API 명세서

| HTTP Method | URI                  | Description |
|-------------|----------------------|-------------|
| GET         | `/admin`             | 관리자 메인 페이지  | 
| GET         | `/admin/reservation` | 예약 관리 페이지   |
| GET         | `/reservations`      | 예약 목록 조회    |
| POST        | `/reservations`      | 예약 추가       | 
| DELETE      | `/reservations/{id}` | 예약 삭제       | 

## DB 연동

### 스키마

```mysql
CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```
