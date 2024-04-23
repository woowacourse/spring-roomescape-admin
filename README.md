# 방탈출 예약 관리

## 프로그램 설명
- 방탈출 예약을 관리할 수 있는 웹 애플리케이션을 만든다.
- 방탈출 예약을 조회할 수 있다.
- 방탈출 예약을 추가할 수 있다.
- 방탈출 예약을 취소할 수 있다.

## 1단계 기능 요구사항

- [x] `GET /localhost:8080/admin` 요청 시, 어드민 메인 페이지를 응답한다.

## 2단계 기능 요구사항

- [x] `GET /admin/reservation` 요청 시, 예약 관리 페이지를 응답한다.
- [x] 예약 관리 페이지 로드 시 예약 목록 조회 API 호출한다.

## 3단계 기능 요구사항

- [x] `POST /reservations` 요청 시, 예약을 추가한다.
- [x] `DELETE /reservations/{id}` 요청 시, 예약을 취소한다.
- [x] 방탈출 예약을 추가한다
  - [x] 사용자는 이름, 예약날짜, 예약 시간을 입력한다.
    - [x] 이름은 1글자 이상의 영/한글로 구성된다.
    - [x] 예약 날짜는 현재 날짜 이후의 날짜이다.
    - [x] 예약 시간은 운영시간(오전 9시 - 오후 10시) 사이의 시간만 가능하다. 

## 4-6 단계 기능 요구사항
- [x] h2 데이터베이스를 통해 데이터를 저장한다.
  - [x] 예약 조회 API 처리 시, 저장된 예약 조회 시 데이터베이스를 활용한다.
  - [x] 예약 추가 API 처리 시, 저장된 예약 조회 시 데이터베이스를 활용한다.
  - [x] 예약 취소 API 처리 시, 저장된 예약 조회 시 데이터베이스를 활용한다.
    - [x] 기존의 List/AtomicLong을 사용하지 않는다

## 7-9 단계 기능 요구사항
- [ ] 


---

## API

### 예약 목록 조회 API

- Request
```
GET /reservations HTTP/1.1
```

- Response
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

### 예약 추가 API


- Request
```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "time": "15:40"
}
```

- Response
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

### 예약 취소 API


- Request
```
DELETE /reservations/1 HTTP/1.1
```

- Response
```
HTTP/1.1 200
```

### 시간 추가 API
- Request
```
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

- Response
```
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 조회 API

- Request
```
GET /times HTTP/1.1
```

- Response
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

### 시간 삭제 API
- Request
```
DELETE /times/1 HTTP/1.1
```

- Response
```
HTTP/1.1 200
```


---

## 1단계 1차 리뷰 반영 사안

- Controller의 분리
  - ReservationController : RestController
  - AdminController : 뷰 반환 컨트롤러
- RestController 반환값 통일 -> ResponseEntity
- 값 객체 포장을 통한 검증 기능 추가
  - 이름은 1글자 이상의 영/한글로 구성된다.
  - 예약 날짜는 현재 날짜 이후의 날짜이다.
  - 예약 시간은 운영시간(오전 9시 - 오후 10시) 사이의 시간만 가능하다.

- Reservation 초기화 시 null 체크 : by Objects.requireNonNull
- Dto 분리 : ReservationRequestDto/ReservationResponseDto
- Reservations 내 자료구조 변경 : List > Map