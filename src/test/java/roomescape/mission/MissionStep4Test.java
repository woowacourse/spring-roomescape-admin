package roomescape.mission;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.service.ReservationService;
import roomescape.service.ReservationTimeService;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionStep4Test {

    @Autowired
    private ReservationController reservationController;

    @Autowired
    private ReservationTimeController reservationTimeController;

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationTimeService reservationTimeService;

    @Test
    void ReservationController에는_JdbcTemplate가_존재하지_않는다() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    @Test
    void ReservationTimeController에는_JdbcTemplate가_존재하지_않는다() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationTimeController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    @Test
    void ReservationService에는_JdbcTemplate가_존재하지_않는다() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationService.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

    @Test
    void ReservationTimeService에는_JdbcTemplate가_존재하지_않는다() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationTimeService.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }

}
