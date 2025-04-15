# 방탈출 예약 관리

### 요구사항

- `localhost:8080/admin`요청 시 아래 화면과 같이 어드민 메인 페이지가 응답할 수 있도록 구현하세요.
- 어드민 메인 페이지는**`templates/admin/index.html`**파일을 이용하세요.
- **`localhost:8080`** 요청 시 welcom page 응답

- **`/admin/reservation`** 요청 시 아래 화면과 같이 예약 관리 페이지가 응답할 수 있도록 구현하세요.
- 페이지는 **`templates/admin/reservation-legacy.html`** 파일을 이용하세요.
- 아래의 API 명세를 따라 예약 관리 페이지 로드 시 호출되는 예약 목록 조회 API도 함께 구현하세요.

### 요구사항 해결 방향성

**웹 관련 의존성**

- 현재 프로젝트는 웹 관련 gradle 의존성이 추가되어있지 않습니다.
- 필요한 의존성을 찾아서 추가해주세요.

**화면 응답**

- 현재 프로젝트 상태에서는**`localhost:8080`**요청 시 아무런 페이지를 응답하고 있지 않습니다.
- 스프링부트 프로젝트에서 welcome page를 응답하는 방법을 참고해보세요.
- 템플릿엔진을 활용하여 페이지를 응답하는 방법을 확인해보세요.

**예약 페이지**

- Thymeleaf 템플릿엔진을 활용하여 페이지를 응답하세요.
- **`/admin/reservation`** 요청 시 **`templates/admin/reservation-legacy.html`**을 응답하도록 구현해주세요.
- **`templates/admin/reservation-legacy.html`** 파일을 이동하거나 수정할 필요는 없습니다.

**예약 목록 조회 API**

- 예약 페이지 요청과 예약 목록 조회 요청을 처리하는 메서드를 구현하세요.
- 예약 생성 로직이 아직 없으니 정상 동작 확인을 위해 임의 데이터를 넣어서 확인해볼 수 있습니다.

**예약 데이터 관리**

- 별도의 데이터베이스가 없기 때문에 예약 목록을 아래와 같이 처리할 수 있습니다.

```java
public class ReservationController {

    private List<Reservation> reservations = new ArrayList<>();
}
```
