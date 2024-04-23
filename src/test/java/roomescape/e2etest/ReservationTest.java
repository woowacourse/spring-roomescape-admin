package roomescape.e2etest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.model.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private Map<String, String> timeParams;
    private Map<String, String> params;

    @BeforeEach
    public void setUp() {
        timeParams = new HashMap<>();
        timeParams.put("startAt", "10:00");

        params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");
    }

    @DisplayName("/reservations 가 200 statusCode 를 응답한다")
    @Test
    void testGetReservations() {
        RestAssured.given().log().all()
                .when().get("http://localhost:" + port + "/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("reservation 추가 및 삭제를 올바르게 진행한다")
    @Test
    void testReservationCreateAndDelete() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("http://localhost:" + port + "/times")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("http://localhost:" + port + "/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("http://localhost:" + port + "/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("http://localhost:" + port + "/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @DisplayName("추가된 reservation 이 db 에 저장된다")
    @Test
    void testReservationInsert() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "15:40");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "1");
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(reservations().size()).isEqualTo(count);
    }

    private List<Reservation> reservations() {
        return RestAssured.given().log().all()
                .when().get("http://localhost:" + port + "/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);
    }

    @DisplayName("reservation 이 db 에서 삭제된다")
    @Test
    void testReservationDelete() {
        RestAssured.given().log().all()
                .when().delete("http://localhost:" + port + "/reservations/1")
                .then().log().all()
                .statusCode(204);
        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(0);
    }
}
