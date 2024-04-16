## API 명세서

### 어드민 메인 페이지 접근
- http method: GET
- uri: /admin

### 어드민 예약 페이지 접근
- http method: GET
- uri: /admin/reservation

### 모든 예약 조회
- http method: GET
- uri: /reservations
- response body
  - id
  - name
  - date
  - time

### 예약 추가
- http method: POST
- uri: /reservations
- request body
  - name
  - date
  - time

### 예약 삭제
- http method: DELETE
- uri: /reservations/{id}

## 기능 목록

### 예약
- 예약 정보는 이름, 날짜, 시간으로 이뤄져있다.
