package roomescape.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.request.ReservationRequest;
import roomescape.model.Reservation;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    void should_get_reservations() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void should_insert_reservation() {
        ReservationRequest request = new ReservationRequest(
                LocalDate.of(2023, 8, 5),
                "브라운",
                LocalTime.of(10, 0));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("존재하는 예약이라면 예약을 삭제할 수 있다.")
    @Test
    void should_delete_reservation_when_reservation_exist() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isZero();
    }
}
