package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("classpath:initReservationTime.sql")
class ReservationTimeControllerTest {

    private final Map<String, String> times = createReservationTimeRequest();

    @DisplayName("예약시간을 생성한다.")
    @Test
    void postTime() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(times)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약시간을 전부 조회한다.")
    @Test
    void getReservationTimes() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약시간을 삭제한다.")
    @Test
    void delete() {
        RestAssured.given().log().all()
                .when().delete("/times/2")
                .then().log().all()
                .statusCode(200);
    }

    private Map<String, String> createReservationTimeRequest() {
        Map<String, String> times = new HashMap<>();
        times.put("startAt", "10:00");

        return times;
    }
}
