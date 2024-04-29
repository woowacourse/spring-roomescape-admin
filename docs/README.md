- `/admin` GET 요청시 `admin/index.html` 방탈출 어드민 페이지 반환
- `/admin/reservation` GET 요청 시 `admin/reservation.html` 방탈출 예약 페이지 반환
- `/admin/time` GET 요청 시 `admin/time.html` 시간 관리 페이지 반환
- `/reservations` GET 요청 시 모든 예약 응답
- `/reservations` POST 요청 시 예약 생성
- `/reservations/{id}` DELETE 요청 시 해당 id인 예약 삭제
- `/times` GET 요청 시 모든 예약 시간 응답
- `/times` POST 요청 시 예약 시간 생성
- `/times/{id}` DELETE 요청 시 해당 id인 예약 시간 삭제


---

시간, 예약 관리 콘솔 프로그램

0. 프로그램 종료
1. 예약 시간 조회
2. 예약 시간 추가  
   - 예약 시간 입력
   - ex) "{HH}:{mm}", 10:00
3. 예약 시간 삭제
   - 예약 시간 번호 입력
   - ex) 1
4. 예약 조회
5. 예약 추가
    - 예약 입력
    - ex) "{이름} / {날짜} / {예약시간번호}", john / 2021-01-14 / 1
6. 예약 삭제
    - 예약 시간 번호 입력
    - ex) 1
