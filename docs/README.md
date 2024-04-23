## 🏃 방탈출 예약 관리

방탈출을 예약하고 조회 및 삭제하는 웹 어플리케이션 입니다.

`Spring Boot` 프레임워크와 `H2` 인메모리 데이터베이스를 사용하여 개발되었습니다.

## 💡 기능 요구사항

## 🎫 API Docs

### `Page`

| Endpoint             | Method | Description | File Path                          |
|----------------------|:-------|-------------|------------------------------------|
| `/admin`             | `GET`  | 어드민 메인      | `templates/admin/index.html`       |
| `/admin/reservation` | `GET`  | 예약 관리       | `templates/admin/reservation.html` |
| `/admin/time`        | `GET`  | 예약 시간 관리    | `templates/admin/time.html`        |

### `HTTP`

| Endpoint             | Method   | Description | Response Status Code |
|----------------------|:---------|-------------|----------------------|
| `/reservations`      | `GET`    | 예약 정보 요청    | `200`                |
| `/reservations`      | `POST`   | 예약 추가       | `200`                |
| `/reservations/{id}` | `DELETE` | 예약 취소       | `204` `404`          |
| `/times`             | `GET`    | 예약 시간 정보 요청 | `200`                |
| `/times`             | `POST`   | 예약 시간 추가    | `200`                |
| `/times/{id}`        | `DELETE` | 예약 시간 삭제    | `204` `404`          |

## 🗃️ Database

``` mermaid
erDiagram
    reservation o|--|| reservation_time : "time_id:id"
    reservation {
        id BIGINT
        name VARCHAR(255)
        date VARCHAR(255)
        time_id BIGINT
    }
    reservation_time {
        id BIGINT
        start_at VARCHAR(255)
    }
```

## 🧐 고민했던 부분

### `entity` `repository` `service` Layer

### Get generated id at inserting data to db

https://docs.spring.io/spring-framework/docs/3.0.x/reference/jdbc.html#jdbc-auto-genereted-keys
