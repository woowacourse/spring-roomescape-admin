package roomescape.admin.reservation.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Test
    @DisplayName("예약시간을 추가하고 삭제합니다.")
    void testReservationTimeCRD() {
        Map<String, String> reservationTimeParameters = new HashMap<>();
        reservationTimeParameters.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTimeParameters)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("삭제할 번호가 없으면 실패 메시지를 준다.")
    void deleteReservationWhenNotMatchedId() {
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200)
                .body(equalTo("삭제할 값이 존재하지 않습니다."));
    }
}
