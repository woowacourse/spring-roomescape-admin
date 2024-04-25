# 기능 구현

## 1단계 - 홈 화면

- [x] `/admin` 요청 시 어드민 메인 페이지를 응답한다.
    - `templates/admin/index.html`

## 2단계 - 예약 조회

- [x] `/admin/reservation` 요청 시 예약 관리 페이지를 응답한다.
    - `templates/admin/reservation-legacy.html`
- [x] 예약 관리 페이지 로드 시 `예약 목록 조회 API`를 호출한다.

## 3단계 - 예약 추가 / 취소

- [x] 예약 추가 버튼을 누르면 `예약 추가 API`를 호출한다.
- [x] 삭제 버튼을 누르면 `예약 삭제 API`를 호출한다.

## 4단계 - 데이터베이스 적용하기

- h2 데이터베이스를 연동한다.
  - [x] gradle 의존성 추가
  - [x] 테이블 스키마 정의
  - [x] 데이터베이스 설정

## 5단계 - 데이터 조회하기

- 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 데이터베이스를 활용한다.

## 6단계 - 데이터 추가/삭제하기

- 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용한다.
  - [x] 기존에 사용하던 List 및 AtomicLong 을 제거한다.

## 7단계 - 시간 관리 기능

- [x] 예약 시간 테이블 스키마 정의 
- [x] `admin/time` 요청 시 예약 시간 관리 페이지를 응답한다.
  - `templates/admin/time.html`
- API 명세를 따라 시간 관리 API를 구현
  - [x] `GET /times` 요청 하면 `시간 조회 API` 를 호출한다. 
  - [x] 추가 버튼을 누르면 `시간 추가 API`를 호출한다.
  - [x] 삭제 버튼을 누르면 `시간 삭제 API`를 호출한다.

## 8단계 - 예약과 시간 관리

- [x] API 명세를 따라 시간 관리 API를 수정한다.
- 시간 테이블에 저장된 시간 값만 선택할 수 있도록 한다.
  - [x] 외래키 지정을 통해`reservation` 테이블과 `reservation_time` 테이블의 관계를 설정한다.
  - [x] 시간 타입을 `String` 에서 `ReservationTime` 객체로 수정한다.
  - [x] 예약 추가 시, 시간을 문자열(ex. "10:00") 형태로 입력하던 부분을 ReservationTime 의 식별자(ex. 1)로 수정한다.


# API 명세

## 예약 조회 API

Request

```
GET /reservations HTTP/1.1
```

Response

```
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

## 예약 추가 API

Request

```
POST /reservations HTTP/1.1
content-type: application/json

{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```

Response

```
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

## 예약 삭제 API

Request

```
DELETE /reservations/1 HTTP/1.1
```

Response

```
HTTP/1.1 200
```

## 시간 추가 API

Request

```
POST /times HTTP/1.1
content-type: application/json

{ 
  "startAt": "10:00" 
}
```

Response

```
HTTP/1.1 200
Content-Type: application/json

{
  "id": 1,
  "startAt": "10:00"
}
```

## 시간 조회 API

Request
```
GET /times HTTP/1.1
```

Response

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

## 시간 삭제 API

Request

```
DELETE /times/1 HTTP/1.1
```

Response

```
HTTP/1.1 200
```


# 팀 컨벤션

## 네이밍 컨벤션

- 조작행위 : save(저장) / update(수정) / delete(삭제) / find(조회)
- 컨트롤러 메서드명 : (조작 행위) - [여러 건] - (자원) - [조건]
    - 저장 - 자원 : saveUser()
    - 수정 - 자원 : updateUser()
    - 삭제 - 자원 : deleteUser()
    - 조회 - 자원 : findUser()
    - 조회 - 여러건 - 자원 : findAllUser()
    - 조회 - 자원 - 이름으로 : findUserByName()
    - 조회 - 여러건 - 자원 - 이름으로 : findAllUserByName()
- 요청 DTO : (자원) - (조작 행위)
    - 자원 - 저장 : UserSaveRequest
    - 자원 - 수정 : UserUpdateRequest
    - 자원 - 삭제 : UserDeleteRequest
- 응답 DTO : (자원) - (조작 행위) - [여러 건] - [조건] (단건은 생략)
    - 자원 - 조회 : UserFindResponse
    - 자원 - 조회 - 여러건 : UserFindAllResponse
    - 자원 - 조회 - 이름으로 : UserFindByNameResponse
    - 자원 - 조회 - 여러건 - 이름으로 : UserFindAllByNameResponse

# 논의한 점

- API의 응답값으로 도메인을 사용할지, DTO를 사용할지? API마다 서로 다른 DTO를 사용할지, 하나의 DTO를 사용할지?
    - 결론: API마다 서로 다른 DTO를 사용하자
    - 이유:
        - API 응답값으로 도메인을 사용할 경우, 해당 도메인에 관련된 API들은 전부 도메인에 의존하게 된다.
            - 만일 도메인의 필드가 하나라도 변경되면, 클라이언트한테 제공하는 인터페이스인 API들이 일파만파 영향을 받게 된다.
            - 마찬가지로 여러 API들이 응답값으로 하나의 DTO를 사용할 경우, 도메인을 사용한 경우와 똑같은 사이드 이펙트가 발생한다.
        - 따라서, 각 API들은 응답값으로 서로 다른 DTO를 사용하는 편이 인터페이스(API)의 변경을 최소화할 수 있다.
