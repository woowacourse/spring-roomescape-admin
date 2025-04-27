package roomescape.reservation.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.controller.ReservationController;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationAcceptanceTest {

    @Autowired
    private ReservationController reservationController;

    @Test
    @DisplayName("예약을 조회하는 API를 요청한다.")
    void getReservations() {
        // given
        var reservationTimeParams = Map.of(
                "startAt", "10:00"
        );

        var reservationParams = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1L
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTimeParams)
                .when().post("/times");

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations");

        // when
        List<Reservation> reservations = RestAssured
                .given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        // then
        Reservation expected = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                new ReservationTime(
                        1L,
                        LocalTime.of(10, 0)
                )
        );

        assertThat(reservations.getFirst()).isEqualTo(expected);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 생성하는 API를 요청한다.")
    void createReservation() {
        // given
        var reservationTimeParams = Map.of(
                "startAt", "10:00"
        );

        var reservationParams = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1L
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTimeParams)
                .when().post("/times");

        // when
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations");

        // then
        List<Reservation> reservations = RestAssured
                .given()
                .when().get("/reservations")
                .then().extract()
                .jsonPath().getList(".", Reservation.class);

        Reservation expected = new Reservation(
                1L,
                "브라운",
                LocalDate.of(2023, 8, 5),
                new ReservationTime(
                        1L,
                        LocalTime.of(10, 0)
                )
        );

        assertThat(reservations.getFirst()).isEqualTo(expected);
        assertThat(reservations.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 삭제하는 API를 요청한다.")
    void deleteReservation() {
        // given
        var reservationTimeParams = Map.of(
                "startAt", "10:00"
        );

        var reservationParams = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", 1L
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTimeParams)
                .when().post("/times");

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations");

        // when
        RestAssured
                .given().log().all()
                .when().delete("/reservations/1");

        // then
        List<Reservation> reservations = RestAssured
                .given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        assertThat(reservations.size()).isEqualTo(0);
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
                .when().post("/reservations");
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
                .when().post("/reservations")
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
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }
}
