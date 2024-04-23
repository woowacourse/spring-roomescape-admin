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
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.ReservationApiController;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationApiController reservationApiController;

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
    void 칠단계_페이지() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }

    @TestFactory
    Stream<DynamicTest> 칠단계() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        return Stream.of(
                dynamicTest("예약 시간대 1개를 등록한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(params)
                            .when().post("/times")
                            .then().log().all()
                            .statusCode(200);
                }),
                dynamicTest("등록한 예약 시간대를 조회한다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/times")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }),
                dynamicTest("등록한 예약을 삭제한다", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/times/1")
                            .then().log().all()
                            .statusCode(204);
                }));
    }

    @TestFactory
    Stream<DynamicTest> 팔단계() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
        return Stream.of(
                dynamicTest("예약 1개를 추가한다.", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(reservation)
                            .when().post("/reservations")
                            .then().log().all()
                            .statusCode(200);
                }),
                dynamicTest("예약이 추가되었는지 확인한다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }));
    }

    @Test
    void 구단계() {
        boolean isJdbcTemplateInjected = false;
        for (Field field : reservationApiController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }
        assertThat(isJdbcTemplateInjected).isFalse();
    }
}