package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void readReservations() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "냥인", "2024-04-21", "15:40");

        final List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        final Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void createReservation() {
        final Map<String, String> params = createReservations();

        assertPostRequestStatusCodeOKAndId(params, "/reservations", 1);

        assertGetRequestStatusCodeOKAndSize("/reservations", 1);
    }

    @Disabled
    @Test
    @DisplayName("예약을 삭제한다.")
    void deleteReservation() {
        final Map<String, String> params = createReservations();
        assertPostRequestStatusCodeOKAndId(params, "/reservations", 1);

        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(200);

        assertGetRequestStatusCodeOKAndSize("/reservations", 0);
    }

    private Map<String, String> createReservations() {
        final Map<String, String> reservations = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "time", "15:40"
        );
        return reservations;
    }

    private void assertGetRequestStatusCodeOKAndSize(final String path, final int size) {
        RestAssured.given().log().all()
                .when().get(path)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(size));
    }

    private void assertPostRequestStatusCodeOKAndId(final Map<String, String> params, final String path, final int id) {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post(path)
                .then().log().all()
                .statusCode(200)
                .body("id", is(id));
    }
}
