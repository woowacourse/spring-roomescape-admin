package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Time;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeControllerTest {

    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public @Autowired TimeControllerTest(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void timeTest() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void insertTest() {
        Map<String, String> params = new HashMap<>();
        params.put("start_at", "10:00");

        var response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all().extract();

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.as(Time.class).getId()).isEqualTo(1L);
        assertThat(response.as(Time.class).getStartAt()).isEqualTo("10:00");
    }

    @Test
    void findAllTest() {
        List<Time> times = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Time.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);

        assertThat(times.size()).isEqualTo(count);
    }
}
