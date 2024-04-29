package roomescape.controller.reservation;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("/initial_test_data.sql")
class ReservationTimeControllerTest {

    @Test
    @DisplayName("예약 가능한 시간을 추가한다.")
    void addReservationTime() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "15:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("예약 가능한 시간을 조회한다.")
    void getTimes() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "15:00");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(4));
    }

    @Test
    @DisplayName("id와 매칭되는 예약 가능 시간을 삭제한다.")
    void delete() {
        RestAssured.given().log().all()
                .when().delete("/times/11")
                .then().log().all()
                .statusCode(204);
    }
}
