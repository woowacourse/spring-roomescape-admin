package roomescape.reservationtime.acceptance;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservationtime.controller.ReservationTimeController;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeAcceptanceTest {

    @Autowired
    private ReservationTimeController reservationTimeController;

    @Test
    @DisplayName("시간을 조회하는 API를 요청한다.")
    void getReservationTimes() {
        // given
        var params = Map.of(
                "startAt", "10:00"
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");

        // when
        List<ReservationTime> reservationTimes = RestAssured
                .given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTime.class);

        // then
        ReservationTime expected = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );

        assertThat(reservationTimes.getFirst()).isEqualTo(expected);
        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("시간을 생성하는 API를 요청한다.")
    void createReservationTime() {
        // given
        var params = Map.of(
                "startAt", "10:00"
        );

        // when
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");

        // then
        List<ReservationTime> reservationTimes = RestAssured
                .given()
                .when().get("/times")
                .then().extract()
                .jsonPath().getList(".", ReservationTime.class);

        ReservationTime expected = new ReservationTime(
                1L,
                LocalTime.of(10, 0)
        );

        assertThat(reservationTimes.getFirst()).isEqualTo(expected);
        assertThat(reservationTimes.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("시간을 삭제하는 API를 요청한다.")
    void deleteReservationTime() {
        // given
        var params = Map.of(
                "startAt", "10:00"
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");

        // when
        RestAssured
                .given().log().all()
                .when().delete("/times/1");

        // then
        List<ReservationTime> reservationTimes = RestAssured
                .given()
                .when().get("/times")
                .then().extract()
                .jsonPath().getList(".", ReservationTime.class);

        assertThat(reservationTimes.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("서비스 계층 분리를 확인한다.")
    void separateServiceLayer() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationTimeController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
