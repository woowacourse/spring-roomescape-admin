# 방탈출 예약 관리

## 🚀 1단계 - 홈 화면

- `localhost:8080` 요청 시 welcome 페이지가 응답할 수 있도록 구현한다.
    - welcome 페이지는 `static/index.html` 파일을 이용한다.


- `localhost:8080/admin`요청 시 어드민 메인 페이지가 응답할 수 있도록 구현한다.
    - 어드민 메인 페이지는 `templates/admin/index.html` 파일을 이용한다.

## 🚀 2단계 - 예약 조회

- `/admin/reservation`요청 시 예약 관리 페이지가 응답할 수 있도록 구현한다.
    - 예약 관리 페이지는 `templates/admin/reservation-legacy.html`파일을 이용한다.


- API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API를 구현한다.

**예약 조회 API**

```http request
// Request
GET /reservations HTTP/1.1

// Response
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

## 🚀 3단계 - 예약 추가 / 취소

- API 명세를 따라 예약 추가 API 와 삭제 API를 구현한다.

**예약 추가 API**

```http request
// Request
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}

// Response
HTTP/1.1 200 
Content-Type: application/json

{
    "id": 1,
    "name": "브라운",
    "date": "2023-08-05",
    "time": "15:40"
}
```

**예약 삭제 API**

```http request
// Request
DELETE /reservations/1 HTTP/1.1

// Response
HTTP/1.1 200

## 4단계 - 데이터베이스 정의하기

- h2 데이터베이스에 데이터를 저장하려 합니다. 
- 이를 위한 준비 작업으로 데이터 베이스를 연동해주세요.

## 5단계 - 데이터베이스 조회하기

- 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용하도록 수정하세요.

## 6단계 - 예약 추가/삭제 API를 데이터베이스에 추가/삭제 하는 것으로 변경하기

- 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정하세요.
- 기존에 사용하던 List 및 AtomicLong 을 제거하세요.
- 예약 관리 기능이 정상 동작하도록 기능을 완성하세요.

## 7단계 - 시간 관리 기능

- 방탈출 시간표가 정해져 있는데 직접 입력하기 번거로워서 선택하는 방식으로 수정하려합니다.
- API 명세를 따라 시간 관리 API를 구현하세요.
- 페이지는 templates/admin/time.html 파일을 이용하세요.

#### 시간 생성
```http request
// Request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}

// Response
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 모두 조회
```http request
// request
GET /times HTTP/1.1

// response
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

### 시간 삭제 API
```http request
// request
DELETE /times/1 HTTP/1.1

// response
HTTP/1.1 200
```

## 8단계 - 예약과 시간 관리

### 요구사항
기존에 구현한 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정하세요.

### 변경된 API 명세

```http request
// 예약 추가 API
// Request
POST /reservations HTTP/1.1
content-type: application/json

{
"date": "2023-08-05",
"name": "브라운",
"timeId": 1
}

// Response
HTTP/1.1 200
Content-Type: application/json

{
"id": 1,
"name": "브라운",
"date": "2023-08-05",
"time" : {
    "id": 1,
    "startAt" : "10:00"
    }
}
```
``` http request
// 예약 조회 API
// Request
GET /reservations HTTP/1.1

// Response
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

## 9 단계 - 계층화 리팩터링

### 요구사항
- 레이어드 아키텍처를 적용하여 레이어별 책임과 역할에 따라 클래스 분리를 해보세요.
- 분리한 클래스는 매번 새로 생성하지 않고 스프링 빈으로 등록해서 사용해보세요.

