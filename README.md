## 기능 요구 사항

### 메인 페이지

- [x] 홈
    - `/admin` 요청 시 메인페이지를 응답한다.

### 예약 관리 페이지

- [x] 조회
    - GET `/reservation` 요청 시 `/admin/reservation.html`이 렌더링된다.
    - 예약번호, 예약자, 날짜, 시간이 조회된다.
    - GET `/reservations` 요청 시 예약 목록이 조회된다.
- [x] 추가
    - POST `/reservations` 요청 시 예약이 생성된다.
    - 시간 테이블에 저장된 값만 선택할 수 있다.
- [x] 삭제
    - DELETE `/reservations/{id}` 요청 시 예약이 삭제된다.

### 시간 관리 페이지

- [x] 조회
    - GET `/time` 요청 시 `/admin/time.html`이 렌더링된다.
    - GET `/times` 요청 시 시간 목록이 조회된다.
    - 순서, 시간이 조회된다.
- [x] 추가
    - POST `/times` 요청 시 예약이 생성된다.
- [x] 삭제
    - DELETE `/times/{id}` 요청 시 예약이 삭제된다.