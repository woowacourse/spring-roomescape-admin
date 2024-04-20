## 1단계 - 홈 화면

- [x] localhost:8080/admin 요청 시 어드민 메인 페이지가 응답
  - 어드민 메인 페이지는 templates/admin/index.html 파일을 이용하세요.
- [x] welcome page 응답

## 2단계 - 예약 조회

- [x] /admin/reservation 요청 시 예약 관리 페이지가 응답 
  - 페이지는 templates/admin/reservation-legacy.html 파일을 이용하세요.
- [x] API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API 구현

## 3단계 - 예약 추가 / 취소

- [x] 예약 추가 API 구현
- [x] 삭제 API 구현

## 4단계 - 데이터베이스 적용하기

- [x] h2 데이터베이스 설정
- [x] schema.sql 설정

## 5단계 - 데이터 조회하기

- [x] 예약 조회 API 가 DB 를 사용하도록 구현

## 6단계 - 데이터 추가/삭제하기

- [x] 예약 추가 API 가 DB 를 사용하도록 구현
- [x] 예약 삭제 API 가 DB 를 사용하도록 구현

## 7단계 - 시간 관리 기능

- [x] 시간 관리 페이지 응답
- [ ] 시간 추가 API 구현
- [ ] 시간 조회 API 구현
- [ ] 시간 삭제 API 구현