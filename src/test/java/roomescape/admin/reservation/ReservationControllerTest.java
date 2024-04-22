package roomescape.admin.reservation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.admin.reservation.controller.ReservationController;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    private ReservationController reservationController;

    @Test
    @DisplayName("예약 목록을 조회한다.")
    void findAllTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @TestFactory
    @DisplayName("예약을 추가하고 삭제합니다.")
    Collection<DynamicTest> testReservationCRD() {
        Map<String, Object> reservation = getReservationParameter();
        Map<String, String> time = getTimeParameter();

        return List.of(
                DynamicTest.dynamicTest("예약 시간을 추가한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(time)
                            .when().post("/times")
                            .then().log().all()
                            .statusCode(200);
                }),

                DynamicTest.dynamicTest("예약을 추가한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(reservation)
                            .when().post("/reservations")
                            .then().log().all()
                            .statusCode(200);
                }),

                DynamicTest.dynamicTest("예약을 조회했을 때 하나이다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }),

                DynamicTest.dynamicTest("예약을 조회했을 때 브라운 이름이 존재한다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("name", contains("브라운"));
                }),

                DynamicTest.dynamicTest("예약을 삭제한다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/reservations/1")
                            .then().log().all()
                            .statusCode(204);
                }),

                DynamicTest.dynamicTest("예약을 삭제한 후 조회 했을 때 갯수가 0이다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(0));
                })
        );
    }

    private Map<String, Object> getReservationParameter() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);
        return reservation;
    }

    private Map<String, String> getTimeParameter() {
        Map<String, String> time = new HashMap<>();
        time.put("startAt", "10:00");
        return time;
    }

    @Test
    @DisplayName("삭제할 번호가 없으면 실패 메시지를 준다.")
    void deleteReservationWhenNotMatchedId() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(404)
                .body(equalTo("삭제할 예약이 존재하지 않습니다."));
    }

    @Test
    @DisplayName("ReservationController에 JdbcTemplate 필드가 없다.")
    void checkNoJdbcTemplateInReservationController() {
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
