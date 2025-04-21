package roomescape.reservation.controller;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약을 조회하는 API를 요청한다.")
    void getReservations() {
        // given
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        // when
        List<Reservation> reservations = RestAssured
                .given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("예약을 생성하는 API를 요청한다.")
    void createReservation() {
        // given
        var params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "time", "15:40"
        );

        // when & then
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @Test
    @DisplayName("예약을 삭제하는 API를 요청한다.")
    void deleteReservation() {
        // given
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");

        // when
        RestAssured
                .given().log().all()
                .when()
                .delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        // then
        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);
        assertThat(count).isEqualTo(0);
    }

    @Test
    @DisplayName("예약 생성시 이름이 비어있으면 예외가 발생한다.")
    void cannotCreateReservationWhenNameIsBlank() {
        // given
        var params = Map.of(
                "name", "",
                "date", "2023-08-05",
                "time", "15:40"
        );

        // when & then
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 생성시 날짜가 null이면 예외가 발생한다.")
    void cannotCreateReservationWhenDateIsNull() {
        // given
        var params = new HashMap<String, String>();
        params.put("name", "브라운");
        params.put("date", null);
        params.put("time", "15:40");

        // when & then
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 생성시 시간이 null이면 예외가 발생한다.")
    void cannotCreateReservationWhenTimeIsNull() {
        // given
        var params = new HashMap<String, String>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", null);

        // when & then
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(400);
    }
}
