# 방탈출 어드민 페이지 작성하기

## 1 단계

- [x] 어드민 페이지를 응답할 수 있도록 구현한다

## 2 단계

- [x] GET 메서드를 만들어 예약 목록을 조회 기능을 만든다.

## 3 단계

- [x] POST 메서드를 만들어 예약 추가 기능을 만든다.
- [x] DELETE 메서드를 만들어 예약 삭제 기능을 만든다.

## 4 단계

- [x] h2 데이터베이스를 연결한다.

## 5 단계

- [x] reservation 테이블에서 예약조회기능을 구현한다.

## 6 단계

- [x] reservation 테이블에서 예약추가 기능을 구현한다.
- [x] reservation 테이블에서 예약삭제 기능을 구현한다.

## 7 단계

- [x] reservation_time 테이블에서 예약시간조회 기능을 구현한다.
- [x] reservation_time 테이블에서 예약시간추가 기능을 구현한다.
- [x] reservation_time 테이블에서 예약시간삭제 기능을 구현한다.

## 8 단계

- [x] 예약 요청 시, 시간 대신 시간id로 예약할 수 있게 변경한다.
- [ ] 예약 시에 정해진 시간만 고를 수 있도록 변경한다.

## Database Schema

### RESERVATION

| id(pk) | name | date | time_id(fk) |
|--------|------|------|-------------|

time_id는 RESERVATION_TIME의 id의 외래키임

### RESERVATION_TIME

| id(pk) | start_at |
|--------|----------|
			
			