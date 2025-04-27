package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;

/*
새로운 테스트 도구나 기법(e.g. Spring Boot Test, Mock, RestAssured 등)을 도입하지 않고, Junit만 활용한 단위 테스트를 경험해봅시다.
요구사항에서 RestAssured를 활용한 테스트가 주어진 경우 `그대로 사용하고`,
RestAssured 기반의 테스트 코드를 더 발전 시키지 않습니다. 대신 Junit을 활용해 단위 테스트에 집중해보세요.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {
    @Autowired
    private ReservationController reservationController;

    @Test
    void 구단계() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
