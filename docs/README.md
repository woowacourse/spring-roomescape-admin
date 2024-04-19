## 방탈출 요구사항

## API Docs

| Method   | Endpoint             | Description  | File Path                                 |
|----------|----------------------|--------------|-------------------------------------------| 
| `GET`    | `/admin`             | 어드민 페이지 요청   | `templates/admin/index.html`              |
| `GET`    | `/admin/reservation` | 예약 관리 페이지 요청 | `templates/admin/reservation-legacy.html` |
| `GET`    | `/reservations`      | 예약 정보 요청     |                                           |
| `POST`   | `/reservations`      | 예약 추가        |                                           |
| `DELETE` | `/reservations/{id}` | 예약 취소        |                                           |
