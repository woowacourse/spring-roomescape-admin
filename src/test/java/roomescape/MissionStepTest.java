package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MissionStepTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 이단계() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @TestFactory
    Stream<DynamicTest> 삼단계() {
        Map<String, String> param = new HashMap<>();
        param.put("name", "브라운");
        param.put("date", "2023-08-05");
        param.put("time", "15:40");
        return Stream.of(
                dynamicTest("예약이 없는 상태에서 예약 1개를 등록한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(param)
                            .when().post("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("id", is(1));
                }),
                dynamicTest("예약 1개가 등록되었는지 확인한다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }),
                dynamicTest("예약 id 1을 삭제한다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/reservations/1")
                            .then().log().all()
                            .statusCode(200);
                }),
                dynamicTest("예약이 제거되었는지 확인한다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(0));
                })
        );
    }

    @Test
    void 사단계() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void 오단계() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:45");

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }
}