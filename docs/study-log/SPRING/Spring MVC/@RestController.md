간단하게나마 공부를 한건 그냥 Controller 어노테이션과의 차이점은 Controller는 View를 넘겨줄 때 HTML을 결과 응답으로 넘겨주고, RestController는 JSON과 같은 양식으로 응답하는거라고 공부함.
->  Controller는 페이지 렌더링 담당

> 근데 Controller도 JSON을 반환 가능 한거면 왜 안쓰는거임?
> API 전용이면 @RestController가 더 안전하고 실수가 방지 됨.
> - `@Controller`  
    → 기본: View 반환  
    → JSON 쓰려면 매번 `@ResponseBody` 붙여야 함
 >   
> - `@RestController`  
    → 기본: JSON 반환  
    → 실수할 여지가 없음
---
## 개요
REST Controller는 @RestController 어노테이션이 달린 클래스로, 들어오는 HTTP 요청을 처리하고 뷰가 아닌 데이터 객체로 반환함.
- @Controller와 @ResponseBody의 기능을 결합함
- 주로 RESTful API를 구축하는데 사용
- 응답은 메시지 변환기를 통해 JSON 또는 XML로 변환

