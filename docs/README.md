## 🏃 방탈출 예약 관리

## 💡 기능 요구사항

## 🎫 API Docs

| Method   | Endpoint             | Description  | File Path                                 |
|----------|----------------------|--------------|-------------------------------------------| 
| `GET`    | `/admin`             | 어드민 페이지 요청   | `templates/admin/index.html`              |
| `GET`    | `/admin/reservation` | 예약 관리 페이지 요청 | `templates/admin/reservation-legacy.html` |
| `GET`    | `/reservations`      | 예약 정보 요청     |                                           |
| `POST`   | `/reservations`      | 예약 추가        |                                           |
| `DELETE` | `/reservations/{id}` | 예약 취소        |                                           |

## 🗃️ Database
```sql
CREATE TABLE reservation
(
    id   BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    date VARCHAR(255) NOT NULL,
    time VARCHAR(255) NOT NULL
);
```
``` mermaid
erDiagram
    reservation {
        id BIGINT
        name VARCHAR(255)
        date VARCHAR(255)
        time VARCHAR(255)
    }
```
