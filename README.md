# 방탈출 예약

## 엔드포인트 정리

### 예약 관련
- `GET /reservations`  
  모든 예약 목록 조회

- `POST /reservations`  
  예약 생성  
  
```
{ 
    "name": "홍길동", 
    "date": "2023-08-05", 
    "time": "15:40" 
}
```

- `DELETE /reservations/{id}`  
  예약 삭제

---

### 관리자 페이지
- `GET /admin`  
  어드민 메인 페이지 렌더링

- `GET /admin/reservation`  
  예약 관리용 HTML 페이지 렌더링 (타임리프 기반)
