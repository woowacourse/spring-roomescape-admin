package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationsControllerTest {
    @Autowired
    private ReservationsController reservationController;
    private final Map<String, String> reservation = new HashMap<>();
    private final Map<String, String> time = new HashMap<>();

    @BeforeEach
    void initializeReservation() {
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", "1");
    }

    @BeforeEach
    void initializeTime() {
        time.put("id", "1");
        time.put("startAt", "17:00");
    }

    @AfterEach
    void clearData() {
        reservation.clear();
        time.clear();
    }

    @Test
    void get_Reservations_Test() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void delete_Reservations_Test() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(time)
                .when().post("/times");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

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

    @Test
    void post_Reservations_Test() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(time)
                .when().post("/times");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void check_JdbcTemplate_Injection_Test() {
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
