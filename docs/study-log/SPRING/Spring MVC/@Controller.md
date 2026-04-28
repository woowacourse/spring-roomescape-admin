## 개요
@Controller는 주로 View를 반환하기 위해 사용됨.
Controller가 반환환 뷰의 이름으로부터 View를 렌더링하기 위해서는 ViewResolver가 사용되며, ViewResolver 설정에 맞게 View를 찾아 렌더링합니다.

하지만 Spring MVC의 컨트롤러를 사용하면서 Data를 반환해야 하는 경우도 있음. 컨트롤러에서 데이터를 반환하기 위해 @ResponseBody 어노테이션을 활용해주어야 함.

컨트롤러를 통해 객체를 반환할 때에는 일반적으로 ResponseEntity로 감싸서 반환함. 그리고 객체를 반환하기 위해서는 viewResolver 대신에 HttpMessageConverter가 동작함. 

HttpMessageConverter에는 여러 Converter가 등록되어 있고, 반환해야하는 데이터에 따라 사용되는 Converter가 달라짐. 단순 문자열인 경우에는 StringHttpMessageConverter가 사용되고, 객체인 경우에는 MappingJackson2HttpMessageConverter가 사용되며, 데이터 종류에 따라 서로 다른 MessageConverter가 작동하게 됨. 

Spring은 클라이언트의 HTTP Accept 헤더와 서버의 컨트롤러 반환 타입 정보 둘을 조합해 적합한 HttpMessageConverter를 선택하여 이를 처리함. MessageConverter가 동작하는 시점은 HandlerAdapter와 Controller가 요청을 주고 받는 시점이다. 그림의 4번에서는 메세지를 객체로, 6번에서는 객체를 메세지로 변환하는데 메세지 컨버터가 사용됨.

ResponseEntity로 감싸서 반환하고 있고, json으로 반환하기 위해 @ResponseBody라는 어노테이션을 붙여주고 있음. detailView 함수에서는 View를 전달해주고 있기 때문에 String을 반환값으로 설정해줌.

>다른사람 보니까 @ResponseBody 어노테이션을 안쓰던데?
>음.. 안된다고 하네