# 방탈출 예약관리 프로그램

- 웹 페이지를 사용하여 방 탈출 예약 관리를 해주는 프로그램입니다.

# 기능 요구 사항

## 1단계

- [x] admin 페이지 띄우기

## 2단계

- [x] 예약 관리 페이지 응답하기
- [x] 예약 목록 조회 API 구현하기

## 3단계

- [x] 예약 추가 API 구현하기
- [x] 예약 삭제 API 구현하기

## 4단계

### h2 데이터베이스 연동을 위한 준비 작업하기.

- [x] h2, jdbc gradle 의존성 추가
- [x] 테이블 스키마 sql 파일 정의
- [x] h2 데이터베이스 console 기능 활성화
- [x] datasource url 다음으로 설정 `jdbc:h2:mem:database`

## 5단계

### 예약 조회 API 처리 로직에서 데이터베이스를 활용하도록 수정하기.

- [x] 예약 추가 후 조회한 예약 수와 데이터베이스 쿼리를 통해 조회한 예약 수가 같은지 비교하는 테스트 추가
- [x] 위 테스트를 성공시키도록 코드 수정

## 6단계

### 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정하기.

- [x] 기존에 사용하던 List 및 AtomicLong 을 제거
- [x] 예약 관리 기능이 정상 동작하도록 기능 완성

## 7단계

### API 명세를 따라 시간 관리 API 구현하기.

- [x] `/admin/time` 페이지 응답하기
- [x] 페이지는 `templates/admin/time.html` 파일 이용
- [x] 시간 추가 API 구현하기
- [x] 시간 조회 API 구현하기
- [x] 시간 삭제 API 구현하기
- [x] 데이터베이스 time 스키마 생성 sql 추가

## 8단계

### 예약 기능에서 시간을 시간 테이블에 저장된 값만 선택할 수 있도록 수정하기.

- [ ] 예약, 시간 관리 API 수정  
  8단계의 API 명세를 참고하여, 요구사항이 반영되도록 API 수정하세요.
- [ ] 예약 페이지 파일 수정  
  templates/admin/reservation-legacy.html 대신 templates/admin/reservation.html 파일을 활용하세요.
- [ ] 테이블 스키마 재정의  
  외래키 지정을 통해 reservation 테이블과 reservation_time 테이블의 관계를 설정해주세요.
- [ ] 예약 클래스 수정
  시간 타입을 String -> ReservationTime 객체로 수정하세요.
- [ ] 예약 추가 쿼리 수정
  예약 추가 시, 시간을 문자열(ex. "10:00") 형태로 입력하던 부분을 ReservationTime 식별자(ex. 1)로 수정해주세요.
- [ ] 예약 조회 쿼리 수정
  조회 시 ReservationTime 정보도 함께 조회하기 위해 쿼리를 수정해주세요.

---

# API 명세

## 시간 추가 API

### request

```text
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

### response

```text
HTTP/1.1 200
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

## 시간 조회 API

### request

```text
GET /times HTTP/1.1
```

### response

```text
HTTP/1.1 200 
Content-Type: application/json

[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

## 시간 삭제 API

### request

```text
DELETE /times/1 HTTP/1.1
```

### response

```text
HTTP/1.1 200
```

## 예약 추가 API

### request

```text
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

### response

```text
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

## 예약 조회 API

### request

```text
GET /reservations HTTP/1.1
```

### response

```text
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

