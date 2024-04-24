# 방탈출 예약 관리

## 페어와 지킨 컨벤션

- 클래스를 정의한 뒤 다음 줄은 공백으로 한다.

## 1단계 홈 화면

- [x] Gradle 의존성 추가
- [x] 홈 화면 응답
    - `/admin`으로 요청하면 `admin/index.html`을 응답한다.

## 2단계 예약 조회

- [x] 예약 관리 페이지 응답
    - `/admin/reservation`으로 요청하면 `/admin/reservation-legacy.html`을 응답한다.
- [x] 전체 예약 조회 API를 구현한다.
    - GET `/reservations`
- [x] 예약 도메인 추가
    - 이름, 날짜, 시간을 가진다.

## 3단계 예약 추가 / 취소

- [x] 예약 추가 API를 구현한다.
    - POST `/reservations`
- [x] 예약 삭제 API를 구현한다.
    - DELETE `/reservations/{id}`

## 4단계 데이터베이스 적용하기

- [x] Gradle 의존성 추가한다.
- [x] 테이블 스키마 정의한다.
  - `resources/schema.sql` 파일을 생성한다.
  - 예약 테이블 쿼리 작성한다.
- [x] 데이터 베이스 설정한다.
  - h2의 console 기능 활성화한다.
  - Database url을 `jdbc:h2:mem:database`로 지정한다.

## 5단계 데이터 조회하기

- [x] 예약 조회 API 동작시 데이터베이스를 활용하도록 리팩토링한다.
  - jdbcTemplate으로 예약 테이블에 접근하여 데이터를 조회한다.

## 6단계 데이터 추가/삭제하기

- [x] 예약 추가/취소 API 동작시 데이터베이스 활용하도록 리팩토링한다.
  - jdbcTemplate으로 예약 테이블에 접근하여 데이터를 추가/삭제한다.

## 7단계 시간 관리 기능

- [x] 시간 테이블 스키마를 추가한다.
- [x] 시간 테이블에 접근하는 DAO를 추가한다.
  - [x] 시간 테이블에 접근하여 데이터를 조회한다.
  - [x] 시간 테이블에 접근하여 데이터를 추가한다.
  - [x] 시간 테이블에 접근하여 데이터를 삭제한다.
- [x] 시간 관리 패이지 응답
  - `/admin/time`으로 요청하면 `/admin/time.html`을 응답한다.
- [x] 전체 시간 조회 API를 구현한다.
  - GET `/times`
- [x] 시간 추가 API를 구현한다.
  - POST `/times`
- [x] 시간 삭제 API를 구현한다.
  - DELETE `/times/{id}`
- [x] 시간 도메인 추가
  - 시작 시간을 가진다.

## 8단계 예약과 시간 관리

- [x] 예약 페이지 수정
  - `templates/admin/reservation-legacy.html` 대신 `templates/admin/reservation.html` 활용한다.
- [x] 테이블 스키마 재정의
  - 외래키 지정을 통해 reservation 테이블과 reservation_time 테이블의 관계를 설정
- [x] 예약 클래스 수정
  - String에서 ReservationTime 객체로 수정
- [x] 예약 추가 쿼리 수정
  - 예약 추가 시, 시간을 문자열(ex. "10:00") 형태로 입력하던 부분을 ReservationTime의 식별자(ex. 1)로 수정
- [x] 예약 조회 쿼리 수정
  - 조회 시 ReservationTime 정보도 함께 조회하기 위해 아래와 같이 쿼리를 수정

## 9단계 계층화 리팩터링

- [x] 레이어드 아키텍처 적용
  - 레이어별 책임과 역할에 따라 클래스 분리
  - 분리한 클래스는 스프링 빈으로 등록해서 사용
