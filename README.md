## 1. 요구사항 정리
- [x] /admin 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 templates/admin/index.html 파일을 이용하세요.
- [ ] /admin/reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- [ ] 페이지는 templates/admin/reservation-legacy.html 파일을 이용하세요.
- [ ] API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

## 1-1. API 명세 
**예약 조회 API**
- 요청
```
 GET /reservations HTTP/1.1
```
- 응답 
```
HTTP/1.1 200 
Content-Type: application/json

[
    {
        "id": 1,
        "name": "브라운",
        "date": "2023-01-01",
        "time": "10:00"
    },
    {
        "id": 2,
        "name": "브라운",
        "date": "2023-01-02",
        "time": "11:00"
    }
]

```

## 2. 기능목록

### 2-1. HTTP 요청
- [x] /admin
  - [x] GET 요청
- [ ] /admin/reservation
  - [ ] GET 요청


### 2-2. 메인 로직 실행
- [ ] 예약 리스트를 반환한다.
  - [ ] 예약 정보를 담는 DTO를 생성한다.

### 2-3. HTTP 응답
- [x] templates/admin/index.html 파일 응답
  - [x] 200(OK) 상태코드 응답
- [ ] templates/admin/reservation-legacy.html 파일 응답
  - [ ] 200(OK) 상태코드 응답
