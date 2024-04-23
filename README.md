# spring-roomescape-admin


## 1. 기능 요구 사항

1. h2 데이터베이스에 데이터를 저장. 이를 위한 준비 작업으로 데이터 베이스를 연동
2. 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 DB를 활용하도록 수정
3. 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정
4. 기존에 사용하던 List 및 AtomicLong 을 제거
5. 예약 관리 기능이 정상 동작하도록 기능을 완성

## 2. 세부 요구 사항

### 2-1. h2 DB setting
- [x] gradle 의존성 추가: build.gradle 파일을 이용하여 다음 두 의존성을 추가
   - [x] `spring-boot-stater-jdbc`
   - [x] `h2`
- [x] 테이블 스키마 정의
  - [x] 데이터베이스 테이블 생성을 위해 resources/schema.sql 파일을 생성하고 예약 테이블을 생성하는 쿼리를 작성
- [x] 데이터베이스 설정
  - [x] h2 데이터베이스의 console 기능을 활성화
  - [x] datasource url을 다음과 같이 지정 - jdbc:h2:mem:database

### 2-2. DB 적용 기능

- [x] DB에 존재하는 예약 정보 전건 조회
- [ ] DB에 존재하는 예약 정보 단건 조회
- [ ] DB에 존재하는 예약 정보 삭제
