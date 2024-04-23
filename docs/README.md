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
        BIGINT id PK
        VARCHAR(255) name
        VARCHAR(255) date
        BIGINT time_id FK
    }
    reservation_time {
        BIGINT id PK
        VARCHAR(255) start_at
    }
```

## 🧐 고민했던 부분

### 1. `entity` `repository` `service` Layer

데이터 베이스 접근이 추가되며 `DAO` 역할을 하는 `Repository` 들을 만들고 `Entity` 객체로 데이터를 매핑하도록 하였습니다.

미션 요구사항은 `Controller` 에서 `JdbcTemplate` 을 분리하라는 것이 있었고, `Repository` 로 분리하여 해결하였지만 현재 상황에서 `Service` 영역까지 추가할 필요가 있을지
궁금했습니다.

학습 자료에 있던 계층 분리를 위해 `Service` 레이어를 만들어 보았지만 단순히 `Repository` 에서 데이터를 가져와 `DTO` 로 변환하여 `Controller` 에 넘기는 역할 밖에 하지 않았습니다.

결국 만들었다 다시 지우며 `Client - (DTO) - Repository - (Entity) - DB` 로 동작하게 되었습니다.

따라서 현재 상황에서는 컨트롤러에서 리포지토리를 바로 호출하는 것은 괜찮다고 판단하였으며 이것보다 더 중요한 것은 사실 **의존 관계가 한 방향으로만 흐르게 하는 것** 을 집중하려고 합니다.

### 2.~~~~ Get generated id at inserting data to db

https://docs.spring.io/spring-framework/docs/3.0.x/reference/jdbc.html#jdbc-auto-genereted-keys

위 문서를 보기 전에는 `AUTO_INCREMENT` 된 키 값을 어떻게 가져와서 클라이언트 까지 전달해줄지 고민하는 과정에서 `INSERT` 이후 `SELECT` 구문으로 `id` 가 가장 큰 값 하나를 가져올까
생각했었습니다.

이렇게 되면 데이터 추가에 대한 쿼리문에 2번 발생하게 된다는 단점이 있었고 `Spring Jdbc` 의 기능을 찾다가 `KeyHolder`, `SimpleJdbcInsert`
와 `SqlParameterSource` 를 알게되었습니다.

처음엔 `Spring` 공식 문서 내용을 보고 `KeyHolder` 를 사용하였으며 이후 `SimpleJdbcInsert` 로 변경도 해보았습니다. (지금 하는중)
