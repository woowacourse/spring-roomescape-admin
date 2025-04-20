package roomescape;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.entity.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationRepositoryTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void 오단계() {
        jdbcTemplate.update("INSERT INTO reservation (name, dateTime) VALUES (?, ?)",
                "브라운",
                LocalDateTime.of(2023, 8, 5, 15, 40).toString());

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }
}
