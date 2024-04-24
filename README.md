# 방탈출 예약 관리

## 기능 목록
- [x] `localhost:8080/admin` 요청 시 어드민 메인 페이지가 응답할 수 있게 한다.
  - [x] 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용한다.
- [x] `/admin/reservation` 요청 시 예약 관리 페이지가 응답할 수 있게 한다.
  - [x] 페이지는 `templates/admin/reservation-legacy.html` 파일을 이용한다.
- [x] `/admin/time` 요청 시 시간 관리 페이지가 응답할 수 있게 한다.
  - [x] 페이지는 `templates/admin/time.html` 파일을 이용한다.

- [x] 필요한 의존성을 찾아서 `build.gradle`에 추가한다.

- [x] 데이터베이스를 연동한다.
  - [x] build.gradle 파일에 `spring-boot-stater-jdbc` 의존성을 추가한다.
  - [x] build.gradle 파일에 `h2` 의존성을 추가한다.
- [x] 예약 테이블 스키마를 정의한다.
  - [x] 테이블 생성을 위해 `resources/schema.sql` 파일을 생성한다.
  - [x] 예약 테이블을 생성하는 쿼리를 작성한다.
- [x] 예약 시간 테이블 스키마를 정의한다.
- [x] 데이터베이스를 설정한다.
  - [x] datasource url을 `jdbc:h2:mem:database`로 지정한다.

- [x] API 명세를 따라 예약 추가 API를 구현한다.
- [x] API 명세를 따라 예약 조회 API를 구현한다.
- [x] API 명세를 따라 예약 삭제 API를 구현한다.

- [x] API 명세를 따라 시간 추가 API를 구현한다.
- [x] API 명세를 따라 시간 조회 API를 구현한다.
- [x] API 명세를 따라 시간 삭제 API를 구현한다.

- [x] 예약 페이지 파일을 수정한다.
  - [x] `templates/admin/reservation-legacy.html` 대신 `templates/admin/reservation.html` 파일을 활용한다.
- [ ] 테이블 스키마를 재정의한다.
  - [ ] 외래키 지정을 통해 reservation 테이블과 reservation_time 테이블의 관계를 설정한다.
- [ ] 예약 클래스를 수정한다.
  - [ ] 시간 타입을 ReservationTime 객체로 수정한다.
- [ ] 예약 추가 쿼리를 수정한다.
- [ ] 예약 조회 쿼리를 수정한다.

## 커밋 컨밴션
- feat, docs, fix, refactor, test, style, chore
> EX) feat: Piece id 필드 추가
