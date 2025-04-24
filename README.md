# 방탈출 관리

- 서버를 실행뒤 브라우저 검색창에 localhost:8080으로 접속하실 수 있습니다
  /admin : 어드민 메인페이지
- /admin/reservation : 어드민 예약 관리 페이지
- /admin/time : 어드민 예약 시간 관리 페이지

## 제공 기능

- [x] 예약 시간 추가
- [x] 예약 현황 확인
- [x] 예약 추가
- [x] 예약 삭제

# 예약 시스템 API 명세서

## 목차

- [관리자 페이지](#관리자-페이지)
- [예약 API](#예약-api)
- [예약 시간 API](#예약-시간-api)

## 관리자 페이지

### 기본 URL

```
/admin
```

| 메서드 | 경로           | 설명           | 응답                |
|-----|--------------|--------------|-------------------|
| GET | /            | 관리자 메인 페이지   | admin/index       |
| GET | /reservation | 예약 관리 페이지    | admin/reservation |
| GET | /time        | 예약 시간 관리 페이지 | admin/time        |

## 예약 API

### 기본 URL

```
/reservations
```

| 메서드    | 경로    | 설명          | 요청 데이터            | 응답 데이터                         | 응답 코드          |
|--------|-------|-------------|-------------------|--------------------------------|----------------|
| GET    | /     | 전체 예약 목록 조회 | -                 | List\<ReservationResponseDto\> | 200 OK         |
| POST   | /     | 새로운 예약 추가   | AddReservationDto | -                              | 201 Created    |
| DELETE | /{id} | 특정 예약 삭제    | -                 | -                              | 204 No Content |

### 데이터 구조

#### ReservationResponseDto

```json
{
  "id": "Long",
  "name": "String",
  "time": "String",
  "date": "LocalDate"
}
```

#### AddReservationDto

```json
{
  "name": "String",
  // 예약자 이름
  "timeId": "Long",
  // 예약 시간 ID
  "date": "LocalDate"
  // 예약 날짜
}
```

## 예약 시간 API

### 기본 URL

```
/times
```

| 메서드    | 경로    | 설명             | 요청 데이터                | 응답 데이터                             | 응답 코드          |
|--------|-------|----------------|-----------------------|------------------------------------|----------------|
| GET    | /     | 전체 예약 시간 목록 조회 | -                     | List\<ReservationResponseTimeDto\> | 200 OK         |
| POST   | /     | 새로운 예약 시간 추가   | AddReservationTimeDto | -                                  | 201 Created    |
| DELETE | /{id} | 특정 예약 시간 삭제    | -                     | -                                  | 204 No Content |

### 데이터 구조

#### ReservationResponseTimeDto

```json
{
  "id": "Long",
  "time": "String"
  // 예: "13:00", "14:30"
}
```

#### AddReservationTimeDto

```json
{
  "time": "String"
  // 추가할 예약 시간
}
```

## 에러 응답

| 상태 코드                     | 설명                  |
|---------------------------|---------------------|
| 400 Bad Request           | 요청 데이터 유효성 검증 실패    |
| 404 Not Found             | 해당 ID의 리소스를 찾을 수 없음 |
| 500 Internal Server Error | 서버 내부 오류            |

## 참고 사항

- 모든 POST 요청의 성공 응답에는 새로 생성된 리소스의 URI가 Location 헤더에 포함됩니다. (http 표준)
- 날짜 형식은 (YYYY-MM-DD)을 따릅니다.
- 모든 API 요청 및 응답의 Content-Type은 `application/json`입니다. (페이지 제외)


