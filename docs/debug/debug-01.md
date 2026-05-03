기대한 동작

- 시간에 조회 테스트를 작성했고, 정상적으로 ControllerTest가 동작하기를 바람.

실제 동작

- 각각의 테스트를 단독으로 돌릴 때에는 돌아가지만 전체를 돌리니 동작하지 않음.

현재 가설

- 테스트 환경이 무언가 잘못되어서 생성되었던 것을 삭제하지 않거나, 중복해서 뭔가 처리하는 것 같음.

확인할 가장 작은 실험

- 로그 분석
- 테스트 setUp() 부분에 문제가 있을 것 같아서 이 부분 검색

해결책

다음과 같이 로그에서 찾을 수 있었음.
현재 RESERVATION_TIME을 제거해주는 로직이 없어서 실패

```

Caused by: org.h2.jdbc.JdbcSQLSyntaxErrorException: Table "RESERVATION_TIME" already exists; SQL statement:
CREATE TABLE reservation_time (
id BIGINT NOT NULL AUTO_INCREMENT,
start_at VARCHAR(255) NOT NULL,
PRIMARY KEY (id)
)

```
