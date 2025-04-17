package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationApiControllerTest {

    private static void createAndSendReservation() {
        Map<String, String> params = createReservationData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations");
    }

    private static Map<String, String> createReservationData() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");
        return params;
    }

    @Test
    void 예약_목록_요청을_성공한다() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 예약_추가_요청을_성공한다() {
        Map<String, String> params = createReservationData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 요청으로_추가된_예약_정보를_응답한다() {
        Map<String, String> params = createReservationData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .body("id", is(1));
    }

    @Test
    void 예약_추가로_목록_크기가_증가한다() {
        createAndSendReservation();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void 예약_삭제_요청을_성공한다() {
        createAndSendReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_삭제_ID가_일치하지_않는_경우_요청에_실패한다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    void 예약_삭제_요청으로_데이터가_삭제된다() {
        createAndSendReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1");

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}
