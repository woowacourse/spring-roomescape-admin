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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TimeControllerTest {

    Map<String, String> params = new HashMap<>();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        params.put("startAt", "10:00");
        jdbcTemplate.update("""
        SET REFERENTIAL_INTEGRITY FALSE;
        TRUNCATE TABLE reservation;
        ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1;
        TRUNCATE TABLE reservation_time;
        ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1;
        SET REFERENTIAL_INTEGRITY TRUE;
        """);

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
