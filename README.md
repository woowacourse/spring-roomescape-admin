
# 1~3단계 요구 사항
- [x] `localhost:8080/admin` get 요청 시 어드민 메인 페이지가 응답할 수 있다.
- [x] `localhost:8080` get 요청 시 어드민 메인 페이지가 응답할 수 있다.
- [x] `/admin/reservation` get 요청 시 예약 관리 페이지가 응답할 수 있다.
- [x] 예약 관리 페이지 응답 시, 현재 예약 목록을 함께 보여준다.
- [x] `/admin/reservation` post 요청 시 예약을 추가한다.
- [x] `/reservations/{id}` delete 요청 시 예약을 삭제한다.
  - [x] id 값이 없는 경우 예외를 발생시킨다.

# 4단계 요구사항
- [x] JdbcTemplate을 이용하여 DataSource객체에 접근하기
- [x] DataSource 객체를 이용하여 Connection 확인하기
- [x] Connection 객체를 이용하여 데이터베이스 이름 검증
- [x] Connection 객체를 이용하여 테이블 이름 검증

# 5단계 요구사항
- [x] db의 reservation을 조회 할 수 있다.
