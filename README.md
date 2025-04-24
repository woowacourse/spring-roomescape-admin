## 기능 목록

### Reservation
- 웹
    - [x] 관리자 예약 관리 페이지
- API
    - [x] [GET] 전체 예약 조회
    - [x] [POST] 예약 추가
    - [x] [GET] 특정 예약 조회
    - [x] [DELETE] 특정 예약 삭제
- 검증
    - [x] Reservation 추가 요청 시 예약자 이름/날짜/시간 공백 불가능
    - [x] Reservation 추가 요청 시 오늘 날짜보다 이전이면 예외

### ReservationTime
- 웹
    - [x] 관리자 예약 시간 관리 페이지
- API
    - [x] [GET] 전체 예약 시간 조회
    - [x] [POST] 예약 시간 추가
    - [x] [GET] 특정 예약 시간 조회
    - [x] [DELETE] 특정 예약 시간 삭제
- 검증
    - [x] ReservationTime 추가 요청시 예약 시간 공백 불가능


<br>

---

## 📝 API
#### [GET] /reservations
예약 전체 조회

**응답 예시**
```json
[
  {
    "id":1,
    "name":"빅뱅",
    "date":"2025-04-02",
    "time":"01:21:00"
  }
]
```
**id**: 예약 식별자

**name**: 예약자 이름

**date**: 예약 날짜

**time**: 예약 시간

---

#### [POST] /reservations
예약 추가

**요청 예시**
```json
{
  "date": "2023-08-05",
  "name": "브라운",
  "timeId": 1
}
```
**date**: 예약 날짜

**name**: 예약자 이름

**timeId**: 예약 시간 식별자

**응답 예시**
```json
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

**id**: 예약 식별자

**name**: 예약자 이름

**date**: 예약 날짜

**time.id**: 예약 시간 식별자

**time.startAt**: 예약 시간

---

#### [GET] /reservations/{id}
특정 예약 조회

**요청 파라미터**

PathVariable로 조회할 예약 id 지정

**응답 예시**
```json
{
  "id":1,
  "name":"빅뱅",
  "date":"2025-04-02",
  "time":"01:21:00"
}
```

**id**: 예약 식별자

**name**: 예약자 이름

**date**: 예약 날짜

**time**: 예약 시간

---

#### [DELETE] /reservations/{id}
특정 예약 삭제

PathVariable로 삭제할 예약 **id** 지정

---

#### [GET] /times
예약 시간 전체 조회

```json
[
  {
    "id": 1,
    "startAt": "10:00"
  },
  {
    "id": 2,
    "startAt": "11:00"
  }
]
```
**id**: 예약 시간 식별자

**startAt**: 예약 시간

---

#### [POST] /times
예약 시간 추가

**요청 예시**
```json
{
  "startAt": "10:00"
}
```
**startAt**: 예약 시간

**응답 예시**
```json
{
  "id": 1,
  "startAt": "10:00"
}
```
**id**: 예약 시간 식별자

**startAt**: 예약 시간


---

#### [DELETE] /times/{id}
예약 시간 삭제

PathVariable로 삭제할 예약 시간 **id** 지정
