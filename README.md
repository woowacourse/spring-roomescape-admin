# 방탈출 예약 관리

## 1단계 - 홈 화면

### 기능 요구사항
- [X] localhost:8080/admin 에 접속하면 어드민 메인 페이지를 띄운다

## 2단계 - 예약 조회
- [x] localhost:8080/admin/reservation 에 접속하면 예약 관리 페이지를 띄운다.
- [x] 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API 를 구현한다.

## 3단계 - 예약 추가 / 취소
- [x] 예약 추가 API 를 구현한다.
- [x] 예약 삭제 API 를 구현한다.
  - 예약 번호로 삭제한다.

## 4단계 - 데이터베이스 적용하기
- [ ] h2 데이터베이스를 연동한다.
  - [ ] url은 jdbc:h2:mem:database 으로 지정한다.