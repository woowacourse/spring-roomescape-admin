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

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Test
    @DisplayName("예약 시간을 생성한다")
    void createTime() {
        final Map<String, Object> startTime = new HashMap<>();
        startTime.put("startAt", "10:00");
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(startTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    @DisplayName("예약을 시간을 조회한다")
    void getReservations() {
        createTime();
        RestAssured.given().log().all().when().get("/times")
                .then().log().all().statusCode(200).body("size()", is(1));
    }

    @Test
    @DisplayName("예약 시간을 삭제한다")
    void deleteReservation() {
        createTime();
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all().statusCode(204);
    }
}
