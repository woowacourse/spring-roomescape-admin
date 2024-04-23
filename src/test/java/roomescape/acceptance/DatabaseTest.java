package roomescape.acceptance;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.dto.request.ReservationRequest;
import roomescape.fixture.ReservationFixture;
import roomescape.fixture.ReservationTimeFixture;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DatabaseTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    void setup() {
        RestAssured.port = port;
    }

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
        final long reservationTimeId = ReservationTimeFixture.예약_시간_생성("10:30");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", reservationTimeId);

        List<Reservation> reservations = ReservationFixture.예약_조회();

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations).hasSize(count);
    }

    @Test
    void validate_database_and_web_create_delete_is_consistency() {
        final long reservationTimeId = ReservationTimeFixture.예약_시간_생성("10:30");
        ReservationFixture.예약_생성(new ReservationRequest("브라운", "2023-08-05", reservationTimeId));

        //결국 외부에 침범된다..

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        ReservationFixture.예약_삭제();

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(count - 1);
    }
}
