# 요구사항 명세서

## 1. 홈 화면

- [x] `localhost:8080/admin` 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
    - 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용하세요.

## 2. 예약 조회

- [x] `/admin/reservation` 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
    - 페이지는 `templates/admin/reservation-legacy.html` 파일을 이용하세요.
- [x] 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

## 3. 예약 추가 / 취소

- [x] API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
    - 아래 화면에서 예약 추가와 취소가 잘 동작해야합니다.
