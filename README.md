# spring-roomescape

# 구현 요구사항

1. 홈 화면
   - [x] localhost:8080/admin 요청 시 아래 화면과 같이 어드민 메인 페이지가 응답
   - [X] 필요한 의존성을 찾아서 추가
   - 
2. 예약 조회
   - [ ] /admin/reservation 요청 시 예약 관리 페이지가 응답
   - [ ] Thymeleaf 템플릿엔진을 활용하여 페이지를 응답
   - [ ] /admin/reservation 요청 시 templates/admin/reservation-legacy.html을 응답
   - [ ] 예약 페이지 요청과 예약 목록 조회 요청을 처리하는 메서드를 구현
   - [ ] 별도의 데이터베이스가 없기 때문에 예약 목록을 아래와 같이 처리
3. 예약 추가/취소
   - [ ] API 명세를 따라 예약 추가 API 와 삭제 API를 구현
   - [ ] 추가/취소 API 요청과 응답을 처리하는 컨트롤러 메서드 구현을 위해서는 Spring MVC가 제공하는 Annotation을 잘 활용
