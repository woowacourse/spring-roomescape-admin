package roomescape.acceptance;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.fixture.ReservationFixture;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class DatabaseTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void test_connection() {
        try (Connection connection = jdbcTemplate.getDataSource()
                                                 .getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("ROOMESCAPE");
            assertThat(connection.getMetaData()
                                 .getTables(null, null, "RESERVATION", null)
                                 .next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Test
    void validate_database_and_web_read_is_consistency() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

        List<Reservation> reservations = ReservationFixture.예약_조회();

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations).hasSize(count);
    }

    @Test
    void validate_database_and_web_create_delete_is_consistency() {
        ReservationFixture.예약_생성(new ReservationRequest("브라운", "2023-08-05", "10:30"));


        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        ReservationFixture.예약_삭제();

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isZero();
    }

}
