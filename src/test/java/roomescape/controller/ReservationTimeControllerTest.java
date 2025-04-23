package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:testdb"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeControllerTest {

    @Test
    void 예약_시간_추가_요청을_성공한다() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_시간_조회_요청을_성공한다() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_시간_추가로_목록_크기가_증가한다() {
        createAndSendReservationTime();

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_시간_삭제_요청을_성공한다() {
        createAndSendReservationTime();

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_시간_삭제_ID가_일치하지_않는_경우_요청에_실패한다() {
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    void 예약_시간_삭제_요청으로_데이터가_삭제된다() {
        createAndSendReservationTime();

        RestAssured.given().log().all()
                .when().delete("/times/1");

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    private void createAndSendReservationTime() {
        Map<String, String> params = createReservationTimeData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times");
    }

    private Map<String, String> createReservationTimeData() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");
        return params;
    }
}
