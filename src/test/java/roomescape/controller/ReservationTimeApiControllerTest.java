package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeApiControllerTest {

    @Autowired
    private ReservationTimeApiController timeController;

    @DisplayName("모든 예약 시간을 조회한다.")
    @Test
    void findAllReservationTimes() {
        timeController.addReservationTime(new ReservationTimeRequest("10:00"));
        timeController.addReservationTime(new ReservationTimeRequest("11:00"));

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", is(1))
                .body("[0].startAt", is(("10:00")))
                .body("[1].id", is(2))
                .body("[1].startAt", is(("11:00")));
    }

    @DisplayName("예약 시간을 추가한다.")
    @Test
    void addReservationTime() {
        Map<String, Object> params = new HashMap<>();
        params.put("startAt", "11:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1))
                .body("startAt", is("11:00"));
    }

    @DisplayName("예약 시간을 삭제한다.")
    @Test
    void deleteReservationTime() {
        timeController.addReservationTime(new ReservationTimeRequest("10:00"));

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}
