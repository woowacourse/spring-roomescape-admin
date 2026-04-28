# 🚀(미션) 방탈출 예약 관리
> 방탈출 카페 관리자가 전화, 현장 예약을 직접 등록, 관리하는 상황에 필요한 예약 관리 API를 만든다.

### 목표
- 마감기한 지키기!!

### 전체 요구사항 
- 단계 2·3에서는 레벨1에서 학습했던 JUnit만 활용한 단위 테스트를 작성한다.
  - 새로운 테스트 도구나 기법(Spring Boot Test, Mock, RestAssured 등)을 도입하지 않는다.
  - 요구사항 테스트에서 RestAssured가 주어진 경우 그대로 사용하되, 그 위에 새 테스트 기법을 쌓지 않는다.

---
## 🚀1단계: 웹 요청-응답

### 요구사항
- DB없이 메모리(`List`+`AtomicLong`)로 예약 상태를 관리한다.
- 서버를 재시작하면 데이터는 모두 사라진다.

### 예약 CRUD API
- [x] 예약 조회
  - 메서드/URL: `GET /reservations`
  - 요청 본문: x
  - 응답: `[{id, name, date, time}, ...]`
- [ ] 예약 추가
  - 메서드/URL: `POST /reservations`
  - 요청 본문: `{name, date, time}`
  - 응답: `{id, name, date, time}`
- [ ] 예약 삭제
  - 메서드/URL: `DELETE /reservations/{id}`
  - 요청 본문: x
  - 응답: `200 OK`
- [x] 예약_조회 테스트를 통과했는가?
- [ ] 예약_추가_및_삭제 테스트를 통과했는가?
