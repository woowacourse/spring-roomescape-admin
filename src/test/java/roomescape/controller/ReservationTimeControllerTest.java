package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @LocalServerPort
    int port;

    @BeforeEach
    void initializePort() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("시간 생성을 요청하면 200 OK를 응답한다.")
    void postReservationTest() {
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
    @DisplayName("시간 읽기를 요청하면 200 OK를 응답한다.")
    void getReservationTest() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/times")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("시간 삭제를 요청하면 200 OK를 응답한다.")
    void deleteReservationTest() {
        String timeSql = "insert into reservation_time (start_at) values (?)";
        jdbcTemplate.update(timeSql, "11:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }

}
