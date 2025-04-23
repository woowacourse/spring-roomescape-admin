package roomescape.user.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ReservationRestControllerTest {

    private final String TIME1 = "10:00";
    private final String TIME2 = "11:00";

    @BeforeEach
    void setUp() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", TIME1);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());

        Map<String, String> params2 = new HashMap<>();
        params2.put("startAt", TIME2);
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params2)
                .when().post("/times")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 예약_정보_저장에_성공하는_경우_ok를_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "헤일러");
        params.put("date", "2025-04-15");
        params.put("time", TIME1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 요청_형식이_맞지_않아_예약_정보_저장에_실패하는_경우_bad_request를_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "헤일러");
        params.put("date", "2025 04 15");
        params.put("time", "10 00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void 예약_정보_삭제에_성공한_경우_ok를_반환한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "헤일러");
        params.put("date", "2025-04-15");
        params.put("time", TIME2);

        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.OK.value())
                .extract().response();
        Long id = response.jsonPath().getLong("id");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/reservations/" + id)
                .then().log().all()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void 예약_정보를_삭제한다() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", TIME2);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

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
    void 삭제할_예약_정보가_없는_경우_not_found를_반환한다() {

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
