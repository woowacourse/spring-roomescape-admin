## 1. 요구사항 정리
- [x] /admin 요청 시 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- [x] 어드민 메인 페이지는 templates/admin/index.html 파일을 이용하세요.
- [x] /admin/reservation 요청 시 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- [x] 페이지는 templates/admin/reservation-legacy.html 파일을 이용하세요.
- [x] API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.
- [x] API 명세를 따라 예약 추가 API 와 삭제 API를 구현하세요.
- [x] 예약 추가와 취소가 잘 동작해야합니다.
- [x] h2 데이터베이스에 연동합니다.
- [x] 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정하세요.
- [x] 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정하세요. 
  - [x] 기존에 사용하던 List 및 AtomicLong 을 제거하세요.
- [x] 예약 관리 기능이 정상 동작하도록 기능을 완성하세요.
- [x] API 명세를 따라 시간 관리 API를 구현하세요.
- [x] 페이지는 templates/admin/time.html 파일을 이용하세요.
- [x] 예약 기능의 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정하세요.
- [x] 레이어드 아키텍처를 적용하여 레이어별 책임과 역할에 따라 클래스 분리를 해보세요.
- [x] 분리한 클래스는 매번 새로 생성하지 않고 스프링 빈으로 등록해서 사용해보세요.


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
        "date": "2023-08-05",
        "time": {
            "id": 1,
            "startAt": "10:00"
        }
    }
]

```

**예약 추가 API**

- 요청
```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```
- 응답 

```
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

**예약 취소 API**
- 요청
```
DELETE /reservations/1 HTTP/1.1
```
- 응답
```
HTTP/1.1 200
```

**시간 추가 API**
- 요청
```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

- 응답
```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}

```

**시간 조회 API**
- 요청
```
GET /times HTTP/1.1
```

- 응답
```
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

**시간 삭제 API**

- 요청
```
DELETE /times/1 HTTP/1.1
```
- 응답
```
HTTP/1.1 200
```

## 2. 기능목록

### 2-1. HTTP 요청
- [x] /admin (관리자 페이지 홈화면 요청) 
  - [x] GET 요청
- [x] /admin/reservation (예약 관리 페이지 요청)
  - [x] GET 요청
- [x] /reservations (예약 추가 요청)
  - [x] POST 요청 
- [x] /reservations/{id} (예약 취소 요청)
  - [x] DELETE 요청
- [x] /times (시간 추가 요청)
  - [x] POST 요청
- [x] /times (시간 조회 요청)
  - [x] GET 요청
- [x] /times (시간 삭제 요청)
  - [x] DELETE 요청

### 2-2. 메인 로직 실행
- [x] 예약 리스트를 반환한다.
  - [x] 예약 정보를 담는 객체를 생성한다.
- [x] 예약 리스트에 예약 정보를 저장한다.
  - [x] 입력 받은 예약 정보를 가지는 Dto를 생성한다.
- [x] 예약 리스트에서 예약 정보를 삭제한다.
  - [x] 예약 리스트에서 삭제할 예약 정보 아이디를 찾는다.
- [x] 데이터 베이스와 예약 정보 연동한다.
  - [x] 데이터 베이스에서 예약 정보를 조회한다.
  - [x] 데이터 베이스에서 예약 정보를 삭제한다.
  - [x] 데이터 베이스에서 예약 정보를 등록한다.
- [x] 데이터 베이스와 시간 정보를 연동한다.
  - [x] 데이터 베이스에서 시간 정보를 조회한다.
  - [x] 데이터 베이스에서 시간 정보를 삭제한다.
  - [x] 데이터 베이스에서 시간 정보를 등록한다.
- [x] 데이터 베이스에서 예약 정보와 시간 정보에 연관관계를 추가한다.
  - [x] 예약 정보가 예약 시간을 의존한다. 
  - [x] 응답 dto 생성2

### 2-3. HTTP 응답
- [x] templates/admin/index.html 파일 응답 (관리자 페이지 홈화면 응답)
  - [x] 200(OK) 상태코드 응답
- [x] templates/admin/reservation-legacy.html 파일 응답 (예약 관리 페이지 응답)
  - [x] 200(OK) 상태코드 응답
- [x] application/json 타입 바디 데이터 전달 (예약 추가 응답)
  - [x] 201(CREATED) 상태 코드 응답
- [x] 204(NO CONTENT) 상태 코드 응답 (예약 취소 응답)
- [x] templates/admin/time.html 파일 응답 (방탈출 시간 관리 페이지 응답)
  - [x] 200(OK) 상태 코드 응답
- [x] templates/admin/reservation-legacy.html 대신 templates/admin/reservation.html로 응답한다.

