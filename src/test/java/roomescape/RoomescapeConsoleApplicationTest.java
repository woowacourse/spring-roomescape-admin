package roomescape;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.ApplicationContext;

@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class RoomescapeConsoleApplicationTest {

    @Autowired
    private ApplicationContext context;

    @Test
    @DisplayName("프로필이 존재하지 않는다면, ConsoleApplication은 instantiate되지 않는다.")
    void consoleConfigurationTest() {
        assertThatThrownBy(() -> context.getBean(RoomescapeConsoleApplication.class))
                .isInstanceOf(NoSuchBeanDefinitionException.class);
    }
}
