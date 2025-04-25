package roomescape.reservation.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationControllerTest {

    @Autowired
    private ReservationController reservationController;

    @Test
    @DisplayName("예약을 추가하면 응답에 ID가 포함되고, 전체 조회 시 포함된다.")
    void createReservation() {
        setUpReservation();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

    }

    @Test
    @DisplayName("예약 ID를 통해 해당 예약을 조회한다.")
    void getReservationById() {
        setUpReservation();

        RestAssured.given().log().all()
                .when().get("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 삭제 후, 조회 결과에 나타나지 않아야 한다.")
    void deleteReservationById() {
        setUpReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    private void setUpReservation() {
        createReservationTime();
        createNewReservation();
    }

    private void createReservationTime() {
        Map<String, Object> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    private void createNewReservation() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("데이터베이스 관련 로직이 컨트롤러로부터 분리되어 있는지 확인한다.")
    void isSeperatedDBLogic() {
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
