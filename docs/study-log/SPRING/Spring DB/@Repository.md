묵시적인 빈 등록을 위해서 사용되는 stereo type annotation으로 통상 Repository(DAO) 역할을 하는 빈에 선언함.

Repository는 DDD에서 나온 개념으로 스토리지를 캡슐화하고 검색 등을 사용되는 친구.
> @Component로 다 쓰면 되는데 왜 @Repository, @Service, @Controller 등으로 사용할까?
> @Repository는 DAO의 메소드에서 발생할 수 있는 unchecked exception들을 스프링의 DataAccessException으로 처리할 수 있기 때문이다. 또한 가독성에서도 해당 애노테이션을 갖는 클래스가 무엇을 하는지 단 번에 알 수 있다.
> 
> unchecked exception은 뭐지?
> 컴파일러가 처리 강제하지 않는 RuntimeException 계열 예외임. -> 그니까 컴파일러가 처리하지 않는 예외란건가?
> `try-catch`나 `throws` 없어도 컴파일 통과함.
> 
> DataAccessException은 뭐지?
> 스프링이 DB 관련 예외를 공통 RuntimeException으로 통일한 최상위 예외임
> 
> 원래는 SQLException으로 다 처리하는건데, 이걸 DataAccessExcpetion을 스프링에서 만들어서 처리한다는건가?
> ㅇㅇ 맞음.
> 
>  원래는 우리가 직접 catch랑 에러분기를 나눠야하는데, `DuplicateKeyException`이런 에러처럼 스프링이 상세하게 만들어놓은거지?
>  
>  장점이 어떤 오류가 나왔는지 보는건가?
>  단순히 “오류를 보기 쉬운 것”이 아니라 **DB 독립 + 의미 기반 분기 + 코드 단순화**가 핵심 장점이다.


