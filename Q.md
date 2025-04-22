```
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
```

공부해오기

- [ ]
- [ ]

컨트롤러 테스트가 과연 필요할까?
- 요구사항이라면 테스트가 필요하다.
- 현재 요구사항에서 페이지 반환이 명시되어있어 필요하다.


ResponseBody는 언제 쓰는건지


RestAssured log
- log()를 작성하는 위치(given, when, then)에 따라 로그값이 달라진다.
- log는 디버깅용이다.

---

- Controller 분리 어떻게?
    - JSON
    - HTML (View ?)

---
- admin_url 테스트 에러
    - SpringBootTest(webEnvironment=DEFINED_PORT) 없는 경우 에러 발생 → 왜?
    - 컨트롤러가 포트가 못 찾는 이유
---


이건 실제 서버를 localhost:8080에 띄우고,

RestAssured가 실제 HTTP 요청을 날려서 응답을 검증합니다.

즉, 컨트롤러부터 템플릿 렌더링까지 전체 흐름을 검증하는 통합 테스트입니다.

이 경우는 웹 계층 + 도메인 로직이 엮여 있음

🤔 그런데 우리 목적은?
우리는 비즈니스 로직을 주로 테스트하고 싶다.

그렇다면 이 테스트는 너무 많은 걸 테스트하고 있는 것이에요.
