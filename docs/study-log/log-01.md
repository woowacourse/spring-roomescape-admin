## 미션 진행 전 기초 지식 공부

---

- HTTP API
    - 요청: GET, POST
    - 응답: JSON, 200OK
- Spring MVC: 컨트롤러
    - `@RestController`: 외부에서 오는 웹 요청을 처리하는 컨트롤러
    - 매핑 어노테이션: 특정 URL로 들어온 요청을 어떤 메서드가 처리할지 연결함
        - `@GetMapping("/reservations")`: 데이터 조회
        - `@PostMapping("/reservations")`: 데이터 생성
        - `@DeleteMapping("/reservations/{id}")`: 특정 데이터 삭제
- DTO
    - 클라이언트가 보낸 JSON → 자바 객체 || 자바 객체 → JSON
    - `@RequestBody`: POST 요청 시 본문(Body)에 담긴 JSON 데이터 → 자바 객체
    - `@PathVariable`: `/reservations/1`에서 `1` 같은 경로상의 변수를 읽어올 때 사용
    - 기본 생성자: 스프링(정확히는 Jackson 라이브러리)이 JSON → 객체 생성 시 꼭 필요함
- List와 AtomicLong
    - **`List<Reservation>`:** 예약 객체들을 담아둠
    - **`AtomicLong`:**
        - 예약이 추가될 때마다 고유한 ID(1, 2, 3...)를 붙여줘야 하는데, 여러 요청이 동시에 들어와도 번호가 겹치지 않게 숫자를 증가시켜주는 도구
        - `index.incrementAndGet()`을 호출하면 숫자가 1씩 올라감
- Gradle
    - 스프링 부트로 웹 서버를 띄우려면 관련 도구들이 필요하기 때문에, `build.gradle` 파일에 의존성 추가함

    ```bash
    dependencies {
        implementation 'org.springframework.boot:spring-boot-starter-web'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        testImplementation 'io.rest-assured:rest-assured:5.3.1' // 테스트를 위한 도구
    }
    ```


- 정적 리소스: 서버에서 별도의 처리 없이 클라이언트(브라우저)에게 그대로 전달되는 파일(HTML, CSS, JS, 이미지 등)
- 동적 리소스: 서버에서 데이터를 가공하여 매번 다른 결과를 보여주는 페이지입니다. 이때 Template Engine(Thymeleaf 등)이 사용됨
- Controller: 사용자의 요청(URL)을 받아서 어떤 처리를 할지 결정하고, 결과를 반환하는 역할
- View Resolver: 컨트롤러가 반환한 이름을 보고 실제 어떤 화면(파일)을 보여줄지 찾아주는 매커니즘
- JSON: 현대적인 웹 앱이나 모바일 앱과 통신할 때 데이터를 주고받는 표준 형식

## 스프링 학습테스트

---

### MVC1

#### ResponseJsonTest

```java
package cholog;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    @GetMapping("/hello")
    public String world(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/json")
    @ResponseBody        // 이 어노테이션 누락해서 테스트 미통과함
    public Person json() {
        return new Person("brown", 20);
    }
}

```

- 테스트 미통과 원인
    - `@Controller` 어노테이션이 붙은 클래스의 메서드는 ‘어떤 화면’을 보여줄지를 결정함
    - `@ResponseBody` 어노테이션 누락 → Person 객체를 응답 body에 담아서 보내지 않고 ‘json’이라는 이름의 HTML 파일을 찾으려고 함 → 못 찾아서 500에러 던짐

#### ResponseStaticTest

- 테스트 미통과 원인
    - `src/main/resources/static/` 폴더에 `index.html`이 없어서 `404 Not Found` 던짐
- `index.html` 파일이 왜 있어야 하는가?
    - 스프링에서는 `get("/")`(루트 경로)를 호출하면 정적 리소스 위치 (`resources/static/`)에서 `index.html`파일을 찾음 (이건 기본 설정임)
- `index.html` 파일 내부에는 어떤 내용이 적혀있어야 하는가?
    - HTML 규격에만 맞으면 아무 내용이나 적혀 있어도 상관없음
    - 테스트 코드의 목적이 `statusCode().isEqualTo(200)`을 확인하는 것이라면, 파일이 존재만 하면 테스트 통과함
    - 보통은 학습 테스트의 목적에 맞게 `static.html`에 작성했던 내용을 그대로 복사해서 넣으면 됨

### MVC2

#### MemberController

| 어노테이션 | 역할 |
| --- | --- |
| `@RequestBody` | 요청 바디의 JSON을 객체로 변환 |
| `@PathVariable` | URL 경로에 담긴 값을 변수로 추출 |
| `ResponseEntity` | 결과 코드와 데이터를 담는 wrapper |

## 미션 1

---

#### Reservation

- 기본 생성자 **`public Reservation() {}`** 를 만드는 이유
    - 스프링에서 JSON 데이터를 객체로 변환할 때 기본 생성자를 사용함
- 전체 필드 생성자의 용도
    - 개발자가 코드 상에서 직접 객체를 생성할 때 사용함
- `toEntity` 메서드를 만드는 이유
    - 컨트롤러에서, 클라이언트가 보낸 데이터에 서버가 생성한 id를 합칠 때 사용함
    - `reservation` 객체의 필드값을 `set`으로 하나씩 바꾸는 대신 새로운 객체를 생성하여 반환하여 데이터가 중간에 변하는 것을 막음
- `Reservation` 객체는 DTO(클라이언트가 보낸 JSON 데이터를 담아서 돌아옴)와 엔티티(실제로 DB에 저장되는 데이터 단위) 역할을 하고 있음

#### ReservationController

- `@RestController`: `@Controller` + `@ResponseBody`
    - 이 클래스가 외부 요청을 받는 컨트롤러임을 선언함
    - 메서드의 반환값을 HTTP 응답 본문(Body)으로 직접 전송함
    - JSON 형태로 데이터를 주고받을 때 사용함
- AtomicLong
    - 멀티스레드 환경에서 원자성을 보장하는 자료형
    - 순차적인 증가를 보장함
- `ok()`: HTTP 응답 상태 코드를 200 OK로 설정함
- **`body(reservations)`**: 현재 메모리 리스트에 담긴 모든 예약 정보를 JSON 배열 형태로 응답 본문에 담아 보냄
- **`@PathVariable`**: URL 경로에 있는 `{id}` 값을 메서드의 파라미터 `id`에 할당함
- **`ResponseEntity<Void>`**: 삭제 후 돌려줄 데이터 내용이 없으므로 본문(Body)을 비워둔 채 상태 코드만 보내겠다는 의미

## 추후에 학습할 학습 키워드

---

#### 스프링 학습 테스트 mvc1

- 컨트롤러의 모든 메소드가 HTML이 아닌 JSON만 리턴한다면, 클래스 상단에 `@Controller` 대신 `@RestController`를 쓸 수 있음 → 그러면 각 메소드마다 `@ResponseBody`를 붙이지 않아도 자동으로 데이터 응답 모드로 동작함
- **`ViewResolver` vs `HttpMessageConverter`**


    | 구분 | @ResponseBody 없음 (View 방식) | @ResponseBody 있음 (API 방식) |
    | --- | --- | --- |
    | 담당 비서 | `ViewResolver` | `HttpMessageConverter` |
    | 비서의 업무 | "적절한 HTML 템플릿을 찾아라!" | "객체를 JSON/XML로 변환해서 바로 보내라!" |
    | 찾는 위치 | `src/main/resources/templates/` | (없음. 바로 응답 바디에 작성) |
- HTML 화면 없이 데이터(JSON)만 주고받는 Rest API 서버를 만든다면, 클래스 상단에 `@Controller` 대신 `@RestController`를 사용하면 모든 메소드에 `@ResponseBody`를 붙인 것과 동일하게 동작해서 코드가 깔끔해짐

#### 스프링 학습 테스트 mvc2

- REST API
- `public ResponseEntity<Void>`에서 <>는 제네릭인지?

## 미션 진행 방식. 미션 중 어려웠던 점과 질문

---

- 미션을 진행하기 전에 관련 키워드들을 가볍게 공부해서 비교적 쉬운 키워드를 익힐 수 있었지만, `@RequestBody`와 같은 어노테이션은 대략적인 의미만 파악할 수 있었고 어디서 어떻게 쓰이는지 감을 잡을 수 없었습니다.
- 어디서부터 공부해야 할지 모르겠어서 스프링 학습테스트의 문제와 정답을 비교하면서 각 어노테이션과 키워드들에 대해서 파악하였습니다. 이렇게 시작해도 괜찮을까요?
- 모든 키워드들을 공부하기엔 내용이 너무 방대하다고 생각하여 모르는 내용은 일단 ‘추후 학습할 학습 키워드’로 빼두었습니다.
- 이해가 되지 않는 부분을 노션에 적으면서 공부하고 있는데, 내용이나 방식 측면에서 적절하게 학습하고 있는지 궁금합니다.
