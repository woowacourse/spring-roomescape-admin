# 기능 목록

- 웹
  - [x] 관리자 예약 페이지
- API
  - [x] [GET] 전체 예약 조회
  - [x] [POST] 특정 예약 추가
  - [x] [DELETE] 특정 예약 삭제

- 예약
  - [x] 검증 
    - [x] 예약자 이름/날짜/시간 공백 불가능
    - [x] 중복 ID 불가능


<br>

---

## 📝 API
#### [GET] /reservations   
예약 전체 조회

**응답 예시**
```json
{
  "id":1,
  "name":"빅뱅",
  "date":"2025-04-02",
  "time":"01:21:00"
}
```
id: 예약 번호  
name : 예약자 이름   
date : 예약 날짜   
time : 예약 시간   

---

#### [POST] /reservations      
예약 생성   

**응답 예시**
```json
{
  "id":1,
  "name":"빅뱅",
  "date":"2025-04-02",
  "time":"01:21:00"
}
```

id: 예약 번호  
name : 예약자 이름   
date : 예약 날짜   
time : 예약 시간

---

#### [DELETE] /reservations/{id}   
특정 예약 삭제

**요청 파라미터**

id: 예약 번호  
