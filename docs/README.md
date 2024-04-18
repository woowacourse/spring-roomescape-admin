## 방탈출 API

| Method | Endpoint             | Description  | File Path                                 | Controller Type   |
|--------|----------------------|--------------|-------------------------------------------|-------------------|
| GET    | `/admin`             | 어드민 페이지 요청   | `templates/admin/index.html`              | `@Controller`     |
| GET    | `/admin/reservation` | 예약 관리 페이지 요청 | `templates/admin/reservation-legacy.html` | `@Controller`     |
| GET    | `/reservations`      | 예약 정보        |                                           | `@RestController` |
| POST   | `/reservations`      | 예약 추가        |                                           | `@RestController` |
| DELETE | `/reservations/{id}` | 예약 취소        |                                           | `@RestController` |
