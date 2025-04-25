package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.controller.ReservationTimeController;
import roomescape.model.Reservation;
import roomescape.service.dto.reservationtime.request.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeController reservationTimeController;
    @Autowired
    private ReservationController reservationController;


    @DisplayName("/admin/reservation 경로 요청시 200 OK를 반환한다.")
    @Test
    void requestSuccessReservation() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/reservations 경로 요청시 200 OK를 반환한다.")
    @Test
    void requestSuccessReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약을 추가할 수 있다.")
    @Test
    void saveReservation() {
        //given
        Map<String, Object> reservation = creatReservationFixture();

        //when
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        //then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @DisplayName("예약을 조회 할 수 있다.")
    @Test
    void readAllReservation() {
        //given
        saveReservation();

        //when
        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        //then
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @DisplayName("예약번호에 따른 예약 정보를 삭제할 수 있다.")
    @Test
    void deleteReservation() {
        //given
        saveReservation();

        //when
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        //then
        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }

    private Map<String, Object> creatReservationFixture() {
        saveReservationTimeFixture();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);
        return reservation;
    }

    private void saveReservationTimeFixture() {
        reservationTimeController.save(new ReservationTimeRequest(LocalTime.of(10, 0)));
    }

    @DisplayName("예약시간을 추가할 수 있다.")
    @Test
    void saveReservationTime() {
        //given
        Map<String, String> reservationTime = createReservationTimeFixture();

        //when //then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    private Map<String, String> createReservationTimeFixture() {
        Map<String, String> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:00");
        return reservationTime;
    }

    @DisplayName("예약시간을 조회할 수 있다.")
    @Test
    void readAllReservationTime() {
        //given
        saveReservationTime();

        //when //then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약시간 번호에 따른 예약시간을 삭제할 수 있다.")
    @Test
    void deleteReservationTime() {
        //given
        saveReservationTime();

        //when //then
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("ReservationController 클래스에 JdbcTemplate 필드가 존재하는지 확인한다.")
    @Test
    void hasJdbcTemplate() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }
        assertThat(isJdbcTemplateInjected).isFalse();
    }

}

