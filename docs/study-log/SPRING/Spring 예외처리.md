다음 예제에서 볼 수 있듯이, @Controller 및 @ControllerAdvice 클래스에는 컨트롤러 메서드에서 발생하는 예외를 처리하기 위한 @ExceptionHandler 메서드를 포함할 수 있습니다:
```java
import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class SimpleController {

	@ExceptionHandler(IOException.class)
	public ResponseEntity<String> handle() {
		return ResponseEntity.internalServerError().body("Could not read file storage");
	}

}
```

### 예외 매핑
예외는 전파되는 최상위 예외(예: 직접 발생된 IOException)와 일치할 수도 있고, 래퍼 예외 내의 중첩된 원인(예: IllegalStateException으로 래핑된 IOException)과 일치할 수도 있습니다. 
5.3 버전부터는 임의의 원인 수준에서 일치시킬 수 있지만, 이전 버전에서는 직접적인 원인만 고려되었습니다.

예외 유형을 매칭할 때는 앞의 예제에서 보듯이, 대상 예외를 메서드 인수로 선언하는 것이 좋습니다. 
여러 예외 메서드가 일치할 경우, 일반적으로 원인 예외 매칭보다 루트 예외 매칭이 우선시됩니다. 
보다 구체적으로 말하면, ExceptionDepthComparator는 던져진 예외 유형으로부터의 깊이를 기준으로 예외를 정렬하는 데 사용됩니다.

```java
@ExceptionHandler({FileSystemException.class, RemoteException.class})
public ResponseEntity<String> handleIOException(IOException ex) {
	return ResponseEntity.internalServerError().body(ex.getMessage());
}
```

일반적으로 인자 시그니처를 가능한 한 구체적으로 정의하여 루트 예외와 원인 예외 유형 간의 불일치 가능성을 줄일 것을 권장합니다. 다중 매칭 메서드를 개별 @ExceptionHandler 메서드로 분리하고, 각 메서드의 시그니처를 통해 하나의 특정 예외 유형만 매칭하도록 하는 것을 고려해 보십시오.

다중 @ControllerAdvice 구성을 사용하는 경우, 해당 순서에 따라 우선순위가 지정된 @ControllerAdvice에 주요 루트 예외 매핑을 선언할 것을 권장합니다. 원인(cause)보다는 루트 예외(root) 매칭이 선호되지만, 이는 특정 컨트롤러나 @ControllerAdvice 클래스의 메서드 내에서 정의됩니다. 즉, 우선순위가 더 높은 @ControllerAdvice 빈에 대한 원인 매칭이 우선순위가 더 낮은 @ControllerAdvice 빈에 대한 모든 매칭(예: 루트)보다 우선합니다.

마지막으로, @ExceptionHandler 메서드 구현체는 주어진 예외 인스턴스를 원래 형태로 재투척(rethrow)하여 해당 예외 처리를 중단할 수 있습니다. 이는 루트 레벨 매칭에만 관심이 있거나, 정적으로 결정할 수 없는 특정 컨텍스트 내의 매칭에만 관심이 있는 시나리오에서 유용합니다. 재투척된 예외는 마치 해당 @ExceptionHandler 메서드가 처음부터 매칭되지 않았던 것처럼 나머지 해결 체인을 통해 전파됩니다.

Spring MVC에서 @ExceptionHandler 메서드에 대한 지원은 DispatcherServlet 수준의 HandlerExceptionResolver 메커니즘을 기반으로 구축됩니다.

> 만약에 @ControllerAdvice 가 있고 Controller안에 @ExceptionHandler가 둘 다 있으면 어디서 먼저 처리 되는거지?
> **Controller 내부 `@ExceptionHandler`가 먼저 실행되고, 없으면 `@ControllerAdvice`가 처리한다.**
> 
> 뭐가 더 장점이 있는거지? 그냥 트레이드 오프 정도인건가?
> **맞음. Controller 내부 처리와 `@ControllerAdvice`는 우열이 아니라 “지역 처리 vs 전역 처리” 트레이드오프다.**