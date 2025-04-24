package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.api.ReservationRestController;
import roomescape.dto.ReservationGetResponse;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRestController reservationRestController;

    @Test
    void 일단계_어드민_홈화면이_성공적으로_반환된다() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 이단계_어드민_예약_페이지가_로딩된_후_예약_목록을_조회한다() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0)); // 아직 생성 요청이 없으니 Controller에서 임의로 넣어준 Reservation 갯수 만큼 검증하거나 0개임을 확인하세요.
    }

//    @Test
//    void 삼단계_예약의_추가와_삭제가_이루어진다() {
//        Map<String, String> params = new HashMap<>();
//        params.put("name", "브라운");
//        params.put("date", "2023-08-05");
//        params.put("time", "15:40");
//
//        RestAssured.given().log().all()
//                .contentType(ContentType.JSON)
//                .body(params)
//                .when().post("/reservations")
//                .then().log().all()
//                .statusCode(200)
//                .body("id", is(1));
//
//        RestAssured.given().log().all()
//                .when().get("/reservations")
//                .then().log().all()
//                .statusCode(200)
//                .body("size()", is(1));
//
//        RestAssured.given().log().all()
//                .when().delete("/reservations/1")
//                .then().log().all()
//                .statusCode(200);
//
//        RestAssured.given().log().all()
//                .when().get("/reservations")
//                .then().log().all()
//                .statusCode(200)
//                .body("size()", is(0));
//    }

    @Test
    void 사단계_데이터베이스를_적용한다() {
        try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
            assertThat(connection).isNotNull();
            assertThat(connection.getCatalog()).isEqualTo("DATABASE");
            assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
        } catch (SQLException exception) {
            throw new RuntimeException(exception);
        }
    }

//    @Test
//    void 오단계_예약을_추가한다() {
//        jdbcTemplate.update("INSERT INTO reservation(name, date, time) VALUES(?, ?, ?)", "브라운", "2023-08-05", "15:40");
//
//        List<ReservationGetResponse> reservationGetResponses = RestAssured.given().log().all()
//                .when().get("/reservations")
//                .then().log().all()
//                .statusCode(200).extract()
//                .jsonPath().getList(".", ReservationGetResponse.class);
//
//        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) from reservation", Integer.class);
//        assertThat(reservationGetResponses.size()).isEqualTo(count);
//    }

//    @Test
//    void 육단계_예약을_추가하고_삭제한다() {
//        Map<String, String> params = new HashMap<>();
//        params.put("name", "브라운");
//        params.put("date", "2023-08-05");
//        params.put("time", "10:00");
//
//        RestAssured.given().log().all()
//                .contentType(ContentType.JSON)
//                .body(params)
//                .when().post("/reservations")
//                .then().log().all()
//                .statusCode(200);
//
//        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation", Integer.class);
//        assertThat(count).isEqualTo(1);
//
//        RestAssured.given().log().all()
//                .when().delete("/reservations/1")
//                .then().log().all()
//                .statusCode(200);
//
//        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation", Integer.class);
//        assertThat(countAfterDelete).isEqualTo(0);
//    }

    @Test
    void 칠단계_예약가능시간을_등록_삭제_조회한다() {
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
    void 팔단계_ReservationTime_객체를_활용하여_API가_동작한다() {
        Map<String, Object> timeParams = new HashMap<>();
        timeParams.put("startAt", LocalTime.of(10, 0));
        RestAssured.given()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("/times");

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

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
    void 구단계_컨트롤러에서_JdbcTemplate_필드가_제거되었는지_검사한다() {
        boolean isJdbcTemplateInjected = false;

        for (Field field : reservationRestController.getClass().getDeclaredFields()) {
            if (field.getType().equals(JdbcTemplate.class)) {
                isJdbcTemplateInjected = true;
                break;
            }
        }

        assertThat(isJdbcTemplateInjected).isFalse();
    }
}
