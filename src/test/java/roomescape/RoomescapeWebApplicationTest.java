package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import roomescape.config.WebConfig;
import roomescape.controller.console.ReservationConsoleController;

@SpringBootTest(
        classes = WebConfig.class,
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
class RoomescapeWebApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("WebApplication이 동작하면 ConsoleApplication은 생성되지 않는다.")
    void consoleNotFoundTest() {
        assertThatThrownBy(() -> context.getBean(RoomescapeConsoleApplication.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Test
    @DisplayName("WebApplication이 동작하면 콘솔 기반 컨트롤러는 생성되지 않는다.")
    void consoleControllerNotFoundTest() {
        assertThatThrownBy(() -> context.getBean(ReservationConsoleController.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }
}
