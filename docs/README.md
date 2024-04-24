## API 명세서

| REQ    | RES           | ENDPOINT           | DESCRIPTION   | FILEPATH                | BODY                                |
|--------|---------------|--------------------|---------------|-------------------------|-------------------------------------|
| GET    |               | /admin             | 어드민 메인 페이지 접근 | /admin/index.html       |                                     |
| GET    |               | /admin/reservation | 어드민 예약 페이지 접근 | /admin/reservation.html |                                     |
| GET    |               | /admin/time        | 어드민 시간 페이지 접근 | /admin/time.html        |
| GET    |               | /reservations      | 모든 예약 조회      |                         |                                     |
|        | 200 OK        |                    | 모든 예약 조회      |                         | {id, name, date, time{id, startAt}} |
| POST   |               | /reservations      | 예약 추가         |                         | name, date, timeId                  |
|        | 200 OK        |                    | 예약 추가         |                         | id, name, date, time{id, startAt}   |
| DELETE |               | /reservations/{id} | 예약 삭제         |                         |                                     |
|        | 200 OK        |                    | 예약 삭제 성공      |                         |                                     |
|        | 404 NOT FOUND |                    | 예약 삭제 실패      |                         |                                     |
| GET    |               | /times             | 모든 시간 조회      |                         |                                     |
|        | 200 OK        |                    | 모든 시간 조회      |                         | {id, startAt}                       |
| POST   |               | /times             | 시간 추가         |                         | startAt                             |
|        | 200 OK        |                    | 시간 추가         |                         | id, startAt                         |
| DELETE |               | /times/{id}        | 시간 삭제         |                         |                                     |
|        | 200 OK        |                    | 시간 삭제 성공      |                         |                                     |
|        | 404 NOT FOUND |                    | 시간 삭제 실패      |                         |                                     |
