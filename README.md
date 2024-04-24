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

## 4단계 - 데이터베이스 적용

- [x] h2 데이터베이스를 연동한다.
- [x] 예약 테이블을 생성한다.

## 5단계 - 데이터 조회

- [x] `예약 목록 조회 API` 호출 시 데이터베이스에서 예약을 조회한다.

## 6단계 - 데이터 추가/삭제

- [x] `예약 추가 API` 호출 시 데이터베이스에 예약을 저장한다.
- [x] `예약 삭제 API` 호출 시 데이터베이스에서 예약을 삭제한다.

## 7단계 - 시간 관리 기능

- [x] `/admin/time` 요청 시 시간 관리 페이지를 응답한다.
    - `templates/admin/time.html`
- [x] 예약 시간 테이블을 생성한다.
- [x] 시간 관리 페이지 로드 시 `시간 목록 조회 API`를 호출한다.
- [x] 시간 추가 버튼을 누르면 `시간 추가 API`를 호출한다.
- [x] 삭제 버튼을 누르면 `시간 삭제 API`를 호출한다.

## 8단계 - 예약과 시간 관리

- [x] 예약 테이블과 예약 시간 테이블 간 다:1 관계를 추가한다.
- [x] `/admin/reservation` 요청 시 리뉴얼된 예약 관리 페이지를 응답한다.
- [x] `예약 추가 API`의 시간 필드를 예약 시간 타입으로 수정한다.
- [x] `예약 목록 조회 API`의 시간 필드를 예약 시간 타입으로 수정한다.

## 9단계 - 계층화 리팩터링

- [ ] 레이어드 아키텍처를 적용한다.
- [ ] 분리한 클래스를 스프링 빈으로 등록해서 사용한다.

# API 명세

## 예약

### 예약 목록 조회 API

Request

```
GET /reservations HTTP/1.1
```

Response

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

### 예약 추가 API

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
HTTP/1.1 201
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

### 예약 삭제 API

Request

```
DELETE /reservations/1 HTTP/1.1
```

Response

```
HTTP/1.1 204
```

## 시간

### 시간 목록 조회 API

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

### 시간 추가 API

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
HTTP/1.1 201
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

### 시간 삭제 API

Request

```
DELETE /times/1 HTTP/1.1
```

Response

```
HTTP/1.1 204
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

# 논의 및 학습한 점

## DTO 사용

- API의 응답값으로 도메인을 사용할지, DTO를 사용할지? API마다 서로 다른 DTO를 사용할지, 하나의 DTO를 사용할지?
    - 결론: API마다 서로 다른 DTO를 사용하자
    - 이유:
        - API 응답값으로 도메인을 사용할 경우, 해당 도메인에 관련된 API들은 전부 도메인에 의존하게 된다.
            - 만일 도메인의 필드가 하나라도 변경되면, 클라이언트한테 제공하는 인터페이스인 API들이 일파만파 영향을 받게 된다.
            - 마찬가지로 여러 API들이 응답값으로 하나의 DTO를 사용할 경우, 도메인을 사용한 경우와 똑같은 사이드 이펙트가 발생한다.
        - 따라서, 각 API들은 응답값으로 서로 다른 DTO를 사용하는 편이 인터페이스(API)의 변경을 최소화할 수 있다.

## `@JsonFormat` 사용

- 직렬화 : 객체 -> JSON (response) -> JsonFormat(Jackson) 사용 가능
- 역직렬화 : JSON -> 객체 (request) -> DateTimeFormat(Spring), JsonFormat (Jackson) 사용 가능

직렬화 할 땐 Jackson 라이브러리만 사용해서 DateTimeFormat은 사용 불가하다.
역직렬화 할 땐 Spring이 기본 JSON 컨버터로 Jackson 을 가져 JsonFormat과 DateTimeFormat 둘다 사용 가능하다.

[참고](https://jojoldu.tistory.com/361)

## 레이어 분리해준 이유

- Web : request/responseDTO로 웹과 상호작용
- Service : request/responseDTO를 domain으로 매핑해 웹과 데이터저장공간 사이를 연결
- Dao : domain으로 데이터저장공간과 상호작용

## Dao라는 이름이 이상한걸까? -> No

- 고민한 부분
    - ReservationMemoryDao 클래스를 ReservationMemoryRepository 로 이름을 변경할지 고민했다.
    - 그 이유는 Dao는 영속성과 관련되고, Repository는 도메인과 관련된다고 알고 있었는데 ReservationMemoryDao는 영속성을 가진 저장소에 접근하고 있지 않았기 때문이다.
- 결론
    - 공식문서를 찾아보며 Dao가 꼭 영속성과 관련될 필욘 없단 점을 깨달아, 해당 클래스를 표현할 때 Dao와 Repository 중엔 Dao가 더 적합한 네이밍이란 결론을 내렸다.

### Dao에 대한 오해

어떤 사람들은 Dao는 영속성 메커니즘에 접근해 CRUD를 처리하는 패턴이라 정의내리기도 한다.
Data Access Object라는 이름 자체가 persistence를 나타낸다고 하는 블로그 글도 보았는데 공감하기 어려웠다.
그래서 최대한 공식문서에서 말하는 Dao에 대해 찾아보게 되었다.

### Oracle이 설명하는 Dao

[관련 문서](https://www.oracle.com/java/technologies/data-access-object.html)
> A data access object can represent data that is not stored in a database.
> The sample application uses the DAO pattern to represent XML data sources as objects.
> Sample application screens are defined in an XML file which is interpreted by the class ScreenDefinitionDAO.

위 문장을 보면 Dao에서 접근할 데이터 저장소가 꼭 영속성을 가진 대상일 필요는 없음을 알 수 있다.
ReservationMemoryDao 가 저장소로 바라보고 있는 List<Reservation>가 Dao의 데이터 저장소로 손색없다는 뜻이다.
