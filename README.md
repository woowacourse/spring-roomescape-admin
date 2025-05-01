# 방탈출 예약 관리 1단계 ~ 3단계 : 페어 프로그래밍

## 🚀 1단계 - 홈 화면

- `localhost:8080` 요청 시 welcome 페이지가 응답할 수 있도록 구현한다.
    - welcome 페이지는 `static/index.html` 파일을 이용한다.


- `localhost:8080/admin`요청 시 어드민 메인 페이지가 응답할 수 있도록 구현한다.
    - 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용한다.

## 🚀 2단계 - 예약 조회

- `/admin/reservation`요청 시 예약 관리 페이지가 응답할 수 있도록 구현한다.
    - 예약 관리 페이지는 `templates/admin/reservation-legacy.html`파일을 이용한다.


- API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API를 구현한다.

## 🚀 3단계 - 예약 추가 / 취소

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현한다.

# 방탈출 예약 관리 4단계 ~ 9단계 : 개별 진행

## 🚀 4단계 - 데이터베이스 적용하기

- build.gradle 파일을 이용하여 `spring-boot-stater-jdbc`, `h2` 두 의존성을 추가한다.
- 데이터베이스 테이블 생성을 위해 `resources/schema.sql` 파일을 생성하고 예약 테이블을 생성하는 쿼리를 작성한다.
- h2 데이터베이스의 console 기능을 활성화하고, datasource url을 `jdbc:h2:mem:database`로 지정한다.

## 🚀 5단계 - 데이터 조회하기

- 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터 베이스를 활용 하도록 수정한다.
- 테스트) 데이터베이스에 예약 하나 추가 후 예약 조회 API를 통해 조회한 예약 수와 데이터 베이스 쿼리를 통해 조회한 예약 수가 같은지 비교

## 🚀 6단계 - 데이터 추가/삭제하기

- 예약 추가/취소 API 처리 로직에서 데이터 베이스를 활용하도록 수정한다.
- 예약 관리 기능이 정상 동작 하도록 기능을 완성한다.

## 🚀 7단계 - 시간 관리 기능

- `/admin/time` 요청 시 시간 관리 페이지가 응답할 수 있도록 구현한다.
    - 시간 관리 페이지는 `templates/admin/time.html` 파일을 이용한다.


- API 명세를 따라 데이터 베이스를 활용해 시간 추가 / 조회 / 삭제 API를 구현한다.

## 🚀 8단계 - 예약과 시간 관리

- 기존에 구현한 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정한다.


- 예약 페이지 파일 수정한다.
    - templates/admin/reservation-legacy.html 대신 templates/admin/reservation.html 파일을 활용한다.


- 테이블 스키마에서 외래키 지정을 통해 reservation 테이블과 reservation_time 테이블의 관계를 설정한다.


- 예약 클래스의 시간 타입을 ReservationTime 객체로 수정한다.


- 예약 추가 / 조회 쿼리 수정
    - 예약 추가 시, 시간을 문자열(ex. "10:00") 형태로 입력하던 부분을 ReservationTime의 식별자(ex. 1)로 수정한다.
    - 예약 조회 시 ReservationTime 정보도 함께 조회할 수 있게 쿼리를 수정한다.

## 🚀 9단계 - 계층화 리팩터링

- 레이어드 아키텍처를 적용하여 레이어별 책임과 역할에 따라 클래스 분리를 한다.
- 분리한 클래스는 매번 새로 생성하지 않고 스프링 빈으로 등록해서 사용한다.
- ReservationController 에 있던 데이터베이스 관련 로직을 다른 클래스로 분리한다.
