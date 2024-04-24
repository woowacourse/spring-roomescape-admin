package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import roomescape.config.ConsoleConfig;

@SpringBootTest(
        classes = {ConsoleConfig.class},
        webEnvironment = SpringBootTest.WebEnvironment.NONE,
        properties = "spring.main.allow-bean-definition-overriding=true"
)
class RoomescapeConsoleApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("콘솔 어플리케이션이 실행되면, 웹 어플리케이션 빈은 등록되지 않는다.")
    void webApplicationNotFoundTest() {
        assertThatThrownBy(() -> context.getBean(RoomescapeWebApplication.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }
}
