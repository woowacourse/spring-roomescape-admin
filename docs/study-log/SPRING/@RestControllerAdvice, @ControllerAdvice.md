### 개념
@ExceptionHandler, @InitBinder 및 @ModelAttribute 메서드는 해당 메서드가 선언된 @Controller 클래스 또는 그 클래스 계층 구조에만 적용됩니다. 
반면, @ControllerAdvice 또는 @RestControllerAdvice 클래스에 선언된 경우, 해당 메서드는 모든 컨트롤러에 적용됩니다. 
또한 5.3 버전부터는 @ControllerAdvice 내의 @ExceptionHandler 메서드를 사용하여 모든 @Controller 또는 다른 핸들러에서 발생하는 예외를 처리할 수 있습니다.

@ControllerAdvice는 @Component 메타 어노테이션이 적용되어 있으므로, 컴포넌트 스캐닝을 통해 Spring 빈으로 등록될 수 있습니다.
@RestControllerAdvice는 @ControllerAdvice와 @ResponseBody를 결합한 단축 어노테이션으로, 사실상 예외 핸들러 메서드가 응답 본문에 렌더링되는 @ControllerAdvice와 동일합니다.

시작 시 RequestMappingHandlerMapping과 ExceptionHandlerExceptionResolver는 컨트롤러 어드바이스 빈을 감지하여 런타임에 적용합니다. @ControllerAdvice의 전역 @ExceptionHandler 메서드는 @Controller의 로컬 메서드보다 나중에 적용됩니다. 반면, 전역 @ModelAttribute 및 @InitBinder 메서드는 로컬 메서드보다 먼저 적용됩니다.

기본적으로 @ControllerAdvice와 @RestControllerAdvice는 @Controller 및 @RestController를 포함한 모든 컨트롤러에 적용됩니다. 주석의 속성을 사용하여 적용 대상인 컨트롤러 및 핸들러의 범위를 좁힐 수 있습니다.

### 사용 방법

#### 1. Exception을 관리할 클래스에 @ControllerAdvice를 달아준다.

```null
@ControllerAdvice
public class CustomExceptionHandler {

}
```

#### 2. 해당 클래스에 범위를 지정할 @ExceptionHandler를 메서드에 달아준다.

```null
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<> handleAll(Exception ex){
        
    }
}
```

#### 3. 메서드에 해당 범위에 맞는 Excpetion이 컨트롤러에서 Throw 됐을 때 컨트롤러가 리턴할 값을 지정해준다.

```null
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseEntity> handleAll(Exception ex){
        ErrorResponseEntity response = 
        new ErrorResponseEntity(ErrorCode.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
```

#### ResetControllerAdvice 사용
```java
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException ex) {
        ErrorResponse error = new ErrorResponse("USER_NOT_FOUND", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
```
https://keeeeeepgoing.tistory.com/175 <- 여기 잘 나와 있는 듯?