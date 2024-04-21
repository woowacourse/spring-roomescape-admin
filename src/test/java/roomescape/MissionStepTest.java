package roomescape;


import static org.assertj.core.api.Assertions.assertThat;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationApiController;
import roomescape.controller.ReservationTimeApiController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationApiController reservationController;

    @Autowired
    private ReservationTimeApiController reservationTimeController;

    @DisplayName("4단계: 데이터베이스 연결을 확인한다.")
    @Test
    void connectionTest() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION_TIME", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("9단계: 예약 Api 컨트롤러에서의 클래스 분리를 확인한다.")
    @Test
    void classSeparationTest() {
        Field[] fieldsInReservationController = reservationController.getClass().getFields();
        Field[] fieldsInReservationTimeController = reservationTimeController.getClass().getDeclaredFields();

        assertThat(hasFieldsRelatedInDatabase(fieldsInReservationController)).isFalse();
        assertThat(hasFieldsRelatedInDatabase(fieldsInReservationTimeController)).isFalse();
    }

    private boolean hasFieldsRelatedInDatabase(Field[] fields) {
        List<Class<?>> classes = List.of(JdbcTemplate.class, SimpleJdbcInsert.class);

        return Arrays.stream(fields)
                .anyMatch(field -> classes.contains(field.getType()));
    }
}
