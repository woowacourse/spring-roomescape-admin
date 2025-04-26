package roomescape.controller;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTimeControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("시간을 성공적으로 추가한다")
    void createTimeTest() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(4));
    }

    @Test
    @DisplayName("필드의 형식이 맞지 않으면 예외가 발생한다")
    void timeFormatDoesntMatchTest() {
        // given
        Map<String, String> invalidTime = new HashMap<>();
        invalidTime.put("startAt", "25:70");

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(invalidTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("시간 생성 시 필드가 비어있다면 예외가 발생한다")
    void timeStartAtNullTest() {
        // given
        Map<String, String> emptyStartAt = new HashMap<>();
        emptyStartAt.put("startAt", "");

        Map<String, String> nullStartAt = new HashMap<>();

        // when, then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(emptyStartAt)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(nullStartAt)
                .when().post("/times")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("모든 시간을 조회한다")
    void getAllTimesTest() {
        // when, then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("시간을 삭제한다")
    void deleteTimeTest() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then()
                .statusCode(200);

        // when
        RestAssured.given().log().all()
                .when().delete("/times/4")
                .then().log().all()
                .statusCode(200);

        // then - 삭제 확인
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    @DisplayName("존재하지 않는 시간 삭제 시 예외가 발생한다")
    void deleteNonExistTimeTest() {
        // when, then
        RestAssured.given().log().all()
                .when().delete("/times/999")
                .then().log().all()
                .statusCode(404);
    }
}
