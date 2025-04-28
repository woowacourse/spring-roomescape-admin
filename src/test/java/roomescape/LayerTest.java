package roomescape;

import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.common.SpringBootTestBase;
import roomescape.controller.ReservationApiController;

class LayerTest extends SpringBootTestBase {

    @Autowired private ReservationApiController reservationApiController;

    @Test
    void 레이어드_아키텍처를_적용했는지_확인한다() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationApiController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }
        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
