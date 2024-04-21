package roomescape.database;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.reservation.Reservation;

@SpringBootTest
class RoomescapeJdbcTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 사단계_커넥션과_데이터베이스명_및_테이블명을_확인한다() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 오단계_예약_데이터를_테이블에_삽입한다() {
        String name = "브라운";
        LocalDateTime reservationDateTime = LocalDateTime.of(2023, 8, 5, 15, 40);
        jdbcTemplate.update("INSERT INTO reservation (name, reservation_date_time) VALUES (?, ?)", name,
                reservationDateTime);

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }
}
