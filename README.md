# spring-roomescape-admin


## 1. 기능 요구 사항

1. h2 데이터베이스에 데이터를 저장. 이를 위한 준비 작업으로 데이터 베이스를 연동
2. 예약 조회 API 처리 로직에서 저장된 예약을 조회할 때 DB를 활용하도록 수정
3. 예약 추가/취소 API 처리 로직에서 데이터베이스를 활용하도록 수정
4. 기존에 사용하던 List 및 AtomicLong 을 제거
5. 예약 가능 시간 관리 기능 구현
5. 예약 관리 기능이 정상 동작하도록 기능을 완성

## 2. 세부 요구 사항

### 2-1. h2 DB setting
- [x] gradle 의존성 추가: build.gradle 파일을 이용하여 다음 두 의존성을 추가
   - [x] `spring-boot-stater-jdbc`
   - [x] `h2`
- [x] 테이블 스키마 정의
  - [x] 데이터베이스 테이블 생성을 위해 resources/schema.sql 파일을 생성하고 예약 테이블을 생성하는 쿼리를 작성
  - [x] 예약 가능 시간 테이블 생성
  - [x] 예약 테이블이 예약 시간 테이블의 데이터를 참조 
- [x] 데이터베이스 설정
  - [x] h2 데이터베이스의 console 기능을 활성화
  - [x] datasource url을 다음과 같이 지정 - jdbc:h2:mem:database

### 2-2. DB 적용 기능

- [x] DB에 존재하는 예약 정보 전건 조회
- [x] DB에 존재하는 예약 정보 삭제

### 2-3. 예약 가능 시간 관리 기능 구현

- [x] DB에 존재하는 예약 시간 정보 전건 조회
- [x] DB에 존재하는 예약 시간 정보 단건 조회
- [x] DB에 존재하는 예약 시간 정보 삭제


## 3. API 명세
### 예약:

#### 예약 추가 API :
- Request
```http request
POST /reservations HTTP/1.1
content-type: application/json
{
    "date": "2023-08-05",
    "name": "브라운",
    "timeId": 1
}
```
- Response
```http request
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

#### 예약 조회 API :
- Request
```http request
GET /reservations HTTP/1.1
```

- Response
```json
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

#### 예약 삭제 API:
- 
- Request
```http request
DELETE /reservations/1 HTTP/1.1
```

- Response
```json lines
HTTP/1.1 204
```

### 예약 시간 API:

#### 예약 시간 추가 API:

- Request
```http request
POST /times HTTP/1.1
content-type: application/json

{
    "startAt": "10:00"
}
```

- Response
```json lines
HTTP/1.1 201
Content-Type: application/json

{
    "id": 1,
    "startAt": "10:00"
}
```

#### 예약 시간 조회 API:

- Request
```http request
GET /times HTTP/1.1
```

- Response
```json lines
HTTP/1.1 200
Content-Type: application/json
[
   {
        "id": 1,
        "startAt": "10:00"
    }
]
```

#### 예약 시간 삭제 API:
- 
- Request
```http request
DELETE /times/1 HTTP/1.1
```

- Response
```json lines
HTTP/1.1 204
```
