# 🚀(미션) 방탈출 예약 관리
> 방탈출 카페 관리자가 전화, 현장 예약을 직접 등록, 관리하는 상황에 필요한 예약 관리 API를 만든다.

### 목표
- 마감기한 지키기!!

### 전체 요구사항 
- 단계 2·3에서는 레벨1에서 학습했던 JUnit만 활용한 단위 테스트를 작성한다.
  - 새로운 테스트 도구나 기법(Spring Boot Test, Mock, RestAssured 등)을 도입하지 않는다.
  - 요구사항 테스트에서 RestAssured가 주어진 경우 그대로 사용하되, 그 위에 새 테스트 기법을 쌓지 않는다.

---
## 🚀1단계: 웹 요청-응답

### 요구사항
- DB없이 메모리(`List`+`AtomicLong`)로 예약 상태를 관리한다.
- 서버를 재시작하면 데이터는 모두 사라진다.

### 예약 CRUD API
- [x] 예약 조회
  - 메서드/URL: `GET /step1/reservations`
  - 요청 본문: x
  - 응답: `[{id, name, date, time}, ...]`
- [x] 예약 추가
  - 메서드/URL: `POST /step1/reservations`
  - 요청 본문: `{name, date, time}`
  - 응답: `{id, name, date, time}`
- [x] 예약 삭제
  - 메서드/URL: `DELETE /step1/reservations/{id}`
  - 요청 본문: x
  - 응답: `200 OK`
- [x] 예약_조회 테스트를 통과했는가?
- [x] 예약_추가_및_삭제 테스트를 통과했는가?

---

## 2단계: 데이터베이스 연동

### 요구사항
1단계 메모리 저장은 서버 재시작 시 예약 데이터가 모두 사라진다. 예약 CRUD를 H2 데이터베이스로 전환한다.

- [x] 의존성 추가하기
```
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
spring.datasource.url=jdbc:h2:mem:database
```
- [x] 테이블 스키마 생성
- [x] JdbcTemplate 기반 구현 전환
  - 조회, 추가, 삭제 API를 모두 JdbcTemplate 기반으로 전환한다
  - 기존 `List<Reservation>`, `AtomicLong`은 제거한다

- [x] 데이터베이스_연동 테스트가 통과하는가?
- [x] DB_조회_API_전환 테스트가 통과하는가?
- [x] DB_추가_삭제_API_전환 테스트가 통과하는가?

---

## 🚀3단계: 시간 관리

### 요구사항

관리자가 매번 예약 시간을 텍스트로 직접 입력해 번거롭고 실수가 나는 상황이다.
정해진 시간 슬롯을 관리자가 선택해서 쓸 수 있도록 시간 관리 기능을 추가하고 예약과 시간을 연결한다.

### 시간 관리 기능 추가
- [x] reservation_time 테이블을 추가한다
- [x] 시간 추가
  - 메서드/URL: `POST /step3/times`
  - 요청 본문: `{startAt}`
  - 응답: `{id, startAt}`
- [x] 시간 조회
  - 메서드/URL: `GET /step3/times`
  - 요청 본문: -
  - 응답: `[{id, startAt}, ...]`
- [x] 시간 삭제
  - 메서드/URL: `DELETE /step3/times/{id}`
  - 요청 본문: -
  - 응답: `200 OK`

### 예약과 시간 연결
- [x] reservation 테이블의 `time` 컬럼을 `time_id`로 변경
  - 기존 테스트 깨지는 걸 방지하기 위해 reservation_v2로 생성
- [x] `Reservation` 클래스의 `time` 필드를 `ReservationTime` 객체로 변경
- [x] 예약 추가 본문: `time` -> `timeId`로 변경
- [x] 예약 조회 응답: `time`을 객체로 변경

---

## 4단계: 계층 분리

### 요구사항
- 레이어드 아키텍처로 레이어별 책임에 따라 코드를 분리한다.
- `ReservationController`에 `JdbcTemplate` 필드가 남아있지 않아야 한다.
- 레이어별 책임과 역할에 따라 클래스를 분리하고, 분리한 클래스를 Spring Bean으로 등록한다.

- [ ] Controller 레이어는 웹 요청, 응답 책임을 가지도록 한다.
- [ ] Service는 비즈니스 플로우 책임을 가지도록 한다
- [ ] Repository는 DB 접근 책임을 가지도록 한다.
- [ ] Domain은 비즈니스 규칙을 갖도록 한다.
