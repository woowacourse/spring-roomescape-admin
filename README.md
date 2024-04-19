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

- [x] Gradle 의존성 추가
- [x] 테이블 스키마 정의
  - `resources/schema.sql` 파일을 생성
  - 예약 테이블 쿼리 작성
- [ ] 데이터 베이스 설정
  - h2의 console 기능 활성화
  - Database url 지정 `jdbc:h2:mem:database`