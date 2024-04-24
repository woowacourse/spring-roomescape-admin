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
    - 빈 값이 있으면 예외를 던진다.
- 예약을 삭제할 수 있다.
    - 해당하는 id가 없으면 예외를 던진다.

### 시간 관리 페이지

- `/admin/time` 으로 접속할 수 있다.
- 시간 목록을 볼 수 있다.
    - 순서, 시간을 볼 수 있다.
- 시간을 추가할 수 있다.
    - 시간을 입력하여 추가한다.
    - 빈 값이 있으면 예외를 던진다.
- 시간을 삭제할 수 있다.
    - 해당하는 id가 없으면 예외를 던진다.

## API 명세서

| HTTP Method | URI                  | Description |
|-------------|----------------------|-------------|
| GET         | `/admin`             | 관리자 메인 페이지  | 
| GET         | `/admin/reservation` | 예약 관리 페이지   |
| GET         | `/admin/time`        | 시간 관리 페이지   |
| GET         | `/reservations`      | 예약 목록 조회    |
| POST        | `/reservations`      | 예약 추가       | 
| DELETE      | `/reservations/{id}` | 예약 삭제       |
| GET         | `/times`             | 시간 목록 조회    |
| POST        | `/times`             | 시간 추가       | 
| DELETE      | `/times/{id}`        | 시간 삭제       |

## DB 스키마

### 예약

```mysql
CREATE TABLE reservation
(
    id      BIGINT       NOT NULL AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL,
    date    VARCHAR(255) NOT NULL,
    time_id BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (time_id) REFERENCES reservation_time (id)
);
```

### 시간

```mysql
CREATE TABLE reservation_time
(
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    start_at VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);
```
