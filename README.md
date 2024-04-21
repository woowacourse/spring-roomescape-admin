
# 요구 사항

- [x] `localhost:8080/admin` get 요청 시 어드민 메인 페이지가 응답할 수 있다.
- [x] `localhost:8080` get 요청 시 어드민 메인 페이지가 응답할 수 있다.
- [x] `/admin/reservation` get 요청 시 예약 관리 페이지가 응답할 수 있다.
- [x] 예약 관리 페이지 응답 시, 현재 예약 목록을 함께 보여준다.
- [x] `/admin/reservation` post 요청 시 예약을 추가한다.
- [x] `/reservations/{id}` delete 요청 시 예약을 삭제한다.
  - [x] id 값이 없는 경우 예외를 발생시킨다.

### 데이터베이스
- [x] h2 데이터베이스와 연동한다.
  - [x] gradle 의존성 추가한다.
  - [x] 테이블 생성을 위한 스키마 정의한다.
  - [x] h2 데이터베이스 콘솔 기능 활성화한다.
