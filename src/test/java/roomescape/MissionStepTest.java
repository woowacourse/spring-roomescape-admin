package roomescape;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.config.TestClockConfig;
import roomescape.controller.ReservationApiController;
import roomescape.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(TestClockConfig.class)
class MissionStepTest {
    private final Map<String, String> CREATE_RESERVATION_PARAMS = Map.of(
            "name", "브라운",
            "date", "2025-04-22",
            "timeId", "1"
    );
    private final Map<String, String> CREATE_TIME_PARAMS = Map.of(
            "startAt", "10:00"
    );

    @Autowired private JdbcTemplate jdbcTemplate;
    @Autowired private ReservationApiController reservationApiController;

    @Test
    void 어드민_페이지를_조회시_200을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_정보를_조회한다() {
        createTime();
        for (int i = 0; i < 3; i++) {
            createReservationData();
        }
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    void 예약_정보를_생성한다() {
        createTime();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(CREATE_RESERVATION_PARAMS)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void 예약_정보를_삭제한다() {
        createTime();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(CREATE_RESERVATION_PARAMS)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void jdbcTemplate을_이용해서_예약을_추가한다() {
        createTime();
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운",
                "2025-04-22",
                1L
        );

        List<ReservationResponse> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations).hasSize(count);
    }

    private void createReservationData() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(CREATE_RESERVATION_PARAMS)
                .when().post("/reservations");
    }

    private void createTime() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(CREATE_TIME_PARAMS)
                .when().post("/times");
    }

    @Test
    void db를_이용하여_예약을_추가_삭제한다() {
        createTime();
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2025-04-22");
        params.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(count).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isZero();
    }

    @Test
    void 시간을_관리한다() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

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

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약을_추가한다() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2025-04-22");
        reservation.put("timeId", 1);

        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);


        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void 레이어드_아키텍처를_적용했는지_확인한다() {
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
