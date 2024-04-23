package roomescape.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IsolateWithJdbcTemplateTest {

    @Autowired
    private ReservationsController reservationsController;

    @Test
    void ReservationsController는_JdbcTemplate를_필드로_가지지_않는다() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationsController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
