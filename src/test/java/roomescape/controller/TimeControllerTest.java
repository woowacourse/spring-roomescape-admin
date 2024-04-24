package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
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
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import roomescape.dto.time.TimeResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final Map<String, String> params = Map.of(
            "startAt", "17:00"
    );

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    @DisplayName("처음으로 등록하는 시간의 id는 1이다.")
    void firstPost() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1))
                .header("Location", "/times/1");
    }

    @Test
    @DisplayName("아무 시간도 등록 하지 않은 경우, 시간 목록 조회 결과 개수는 0개이다.")
    void readEmptyTimes() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("하나의 시간만 등록한 경우, 시간 목록 조회 결과 개수는 1개이다.")
    void readTimesSizeAfterFirstPost() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1))
                .header("Location", "/times/1");

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("하나의 시간만 등록한 경우, 시간 삭제 뒤 시간 목록 조회 결과 개수는 0개이다.")
    void readTimesSizeAfterPostAndDelete() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1))
                .header("Location", "/times/1");

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("DB에 시간 하나 추가 후 시간 조회 API를 통해 조회한 시간 수는 DB를 통해 조회한 시간 수와 같다.")
    void readDbTimes() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1");

        List<TimeResponse> times = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", TimeResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);

        assertThat(times.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("하나의 시간만 등록한 경우, DB를 조회 했을 때 조회 결과 개수는 1개이다.")
    void postTimeIntoDb() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1");

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("하나의 시간만 등록한 경우, 시간 삭제 뒤 DB를 조회 했을 때 조회 결과 개수는 0개이다.")
    void readTimesSizeFromDbAfterPostAndDelete() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1");

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }
}
