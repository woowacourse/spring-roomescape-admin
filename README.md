## 요구사항 명세

### 메인 페이지
- Method: GET
- API(URL) : /admin
- Response
  - Status Code : 200
  - Content-Type : text/html
  - Body : index.html

### 예약 관리 페이지
- Method : GET
- API(URL) : /admin/reservation
- Response
  - Status Code : 200
  - Content-Type : text/html
  - Body : reservation-legacy.html

### 예약 조회
- Method : GET
- API(URL) : /reservations
- Response
  - Status Code : 200
  - Content-Type : application/json
  - Response Parameters
    - id
    - name
    - date
    - time

### 예약 등록
- Method : POST
- API(URL) : /reservations
- Request
  - Content-Type : application/json
  - Request Parameters
    - name
    - date
    - time
- Response
  - Status Code : 200
  - Content-Type : application/json
  - Response Parameters
    - id
    - name
    - date
    - time

### 예약 취소
- Method : DELETE
- API(URL) : /reservations
- Path Variable : id
- Response
  - Status Code : 200
