package roomescape;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationTimeResponse;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @LocalServerPort
    int port;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationController reservationController;

    @BeforeEach
    void setup() {
        RestAssured.port = this.port;
    }

    @DisplayName("/admin 으로 GET 요청 시 OK가 응답된다.")
    @Test
    void adminGetTest() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/admin/time 으로 GET 요청 시 OK가 응답된다.")
    @Test
    void adminTimeGetTest() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("/admin/reservation 으로 GET 요청 시 OK가 응답된다.")
    @Test
    void adminReservationGetTest() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약 시간 추가 후 전체 예약 시간 조회 테스트")
    @Test
    void reservationTimePostAndGetTest() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        // 예약 시간 추가
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        // 예약 시간 조회
        List<ReservationTimeResponse> reservationTimeResponses = RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationTimeResponse.class);

        assertThat(reservationTimeResponses)
                .isEqualTo(List.of(
                        new ReservationTimeResponse(1L, "10:00")));
    }

    @DisplayName("예약 시간 추가 후 예약 시간 삭제 테스트")
    @Test
    void reservationTimePostAndDeleteTest() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        // 예약 시간 추가
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        // 예약 시간 삭제
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);

        // 예약 시간 조회
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약 추가 후 전체 예약 조회 테스트")
    @Test
    void reservationsPostAndGetTest() {
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("startAt", "10:00");

        // 예약 시간 추가
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        Map<String, String> reservationParams = new HashMap<>();
        reservationParams.put("name", "브라운");
        reservationParams.put("date", "2023-08-05");
        reservationParams.put("timeId", "1");

        // 예약 추가
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        // 예약 조회
        List<ReservationResponse> reservationResponses = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationResponse.class);

        assertThat(reservationResponses)
                .isEqualTo(List.of(
                        new ReservationResponse(1L, "브라운", "2023-08-05", new ReservationTimeResponse(1L, "10:00"))));
    }

    @DisplayName("예약 추가 후 예약 삭제 테스트")
    @Test
    void reservationsPostAndDeleteTest() {
        Map<String, String> timeParams = new HashMap<>();
        timeParams.put("startAt", "10:00");

        // 예약 시간 추가
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        Map<String, String> reservationParams = new HashMap<>();
        reservationParams.put("name", "브라운");
        reservationParams.put("date", "2023-08-05");
        reservationParams.put("timeId", "1");

        // 예약 추가
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationParams)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        // 예약 삭제
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        // 예약 조회
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("데이터베이스 연결 테스트")
    @Test
    void databaseConnectionTest() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertAll(
                    () -> assertThat(connection).isNotNull(),
                    () -> assertThat(connection.getCatalog()).isEqualTo("DATABASE"),
                    () -> assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next())
                            .isTrue()
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @DisplayName("RservationController는 JdbcTemplate을 필드로 가지지 않는다.")
    @Test
    void ReservationControllerHasNotJdbcTemplateTest() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
