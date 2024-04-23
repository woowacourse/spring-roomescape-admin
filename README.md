# 방탈출 예약 관리

## 기능 목록
- [x] `localhost:8080/admin` 요청 시 어드민 메인 페이지가 응답할 수 있게 한다.
  - [x] 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용한다.
- [x] 필요한 의존성을 찾아서 `build.gradle`에 추가한다.
- [x] `/admin/reservation` 요청 시 예약 관리 페이지가 응답할 수 있게 한다.
  - [x] 페이지는 `templates/admin/reservation-legacy.html` 파일을 이용한다.
- [x] API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 구현한다.
- [x] API 명세를 따라 예약 추가 API를 구현한다.
- [x] API 명세를 따라 예약 삭제 API를 구현한다.
  - Spring MVC가 제공하는 Annotation을 활용한다.
- [x] 예약 정보의 식별자를 생성할 때 AtomicLong을 활용한다.
- [x] 데이터베이스를 연동한다.
  - [x] build.gradle 파일에 `spring-boot-stater-jdbc` 의존성을 추가한다.
  - [x] build.gradle 파일에 `h2` 의존성을 추가한다.
- [x] 테이블 스키마를 정의한다.
  - [x] 테이블 생성을 위해 `resources/schema.sql` 파일을 생성한다.
  - [x] 예약 테이블을 생성하는 쿼리를 작성한다.
- [x] 데이터베이스를 설정한다.
  - [x] datasource url을 `jdbc:h2:mem:database`로 지정한다.
- [x] 저장된 예약을 조회한다.
  - 저장된 예약을 조회할 때 데이터베이스를 활용한다.
- [x] 예약 추가/취소 로직을 구현한다.
  - [x] 기존에 사용하던 List 및 AtomicLong을 제거한다.
  - 예약 추가/취소할 때 데이터베이스를 활용한다.

## 커밋 컨밴션
- feat, docs, fix, refactor, test, style, chore
> EX) feat: Piece id 필드 추가
