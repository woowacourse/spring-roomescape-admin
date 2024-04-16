# 방탈출 예약 관리 기능 목록

## step1

- `localhost:8080/admin` 요청 시 어드민 메인 페이지로 응답한다.
    - 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용한다.
- `/admin/reservation` 요청 시 예약 관리 페이지로 응답한다.
    - 예약 관리 페이지에서 예약 목록을 조회할 수 있다.
    - 관리 페이지에서 예약 추가와 삭제를 할 수 있다.
    - 페이지는 `templates/admin/reservation-legacy.html` 파일을 이용한다.
