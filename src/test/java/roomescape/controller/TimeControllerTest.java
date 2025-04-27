package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class TimeControllerTest extends BaseControllerTest {

    Map<String, String> params = new HashMap<>();

    @BeforeEach
    void setUp() {
        params.put("startAt", "10:00");
        truncateTables();
    }

    @Test
    @DisplayName("시간 추가 테스트")
    void saveReservationTimeTest() {

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("시간 삭제 테스트")
    void deleteReservationTimeTest() {

        int id = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("id");

        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .statusCode(200);

    }

    @Test
    @DisplayName("중복된 시간 추가 테스트")
    void alreadyExistReservationTimeTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("없는 시간 삭제 테스트")
    void deleteFailTest() {
        int id = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract()
                .path("id");

        RestAssured.given().log().all()
                .when().delete("/times/" + (id+1))
                .then().log().all()
                .statusCode(404);
    }

    @AfterEach
    void afterEach() {
        params.clear();
    }

}
