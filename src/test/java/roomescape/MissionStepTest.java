package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static roomescape.fixture.TextFixture.makeTodayMessage;
import static roomescape.fixture.TextFixture.makeYesterdayMessage;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.controller.ReservationController;
import roomescape.domain.Reservation;
import roomescape.fixture.TextFixture;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Nested
    class Step1 {

        @Test
        void 홈화면을_조회한다() {
            RestAssured.given().log().all()
                    .when().get("/admin")
                    .then().log().all()
                    .statusCode(200);
        }
    }

    @Nested
    class Step2 {

        @Test
        void 예약_페이지를_조회한다() {
            RestAssured.given().log().all()
                    .when().get("/admin/reservation")
                    .then().log().all()
                    .statusCode(200);
        }

        @Test
        void 예약_목록을_조회한다() {
            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }
    }

    @Nested
    class Step3 {

        @Nested
        class 예약_추가_테스트 {

            @Test
            void 예약을_추가한다() {
                makeReservationTime();

                Map<String, String> params = new HashMap<>();
                params.put("name", "브라운");
                params.put("date", makeTodayMessage());
                params.put("timeId", "1");

                RestAssured.given().log().all()
                        .contentType(ContentType.JSON)
                        .body(params)
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
            void 이름_null_검증_테스트() {
                makeReservationTime();

                Map<String, String> params = new HashMap<>();
                params.put("name", null);
                params.put("date", makeTodayMessage());
                params.put("timeId", "1");

                createBadReservation(params);
            }

            @Test
            void 이름_size_검증_테스트() {
                makeReservationTime();

                Map<String, String> params = new HashMap<>();
                params.put("name", "잠실에사는비행기데코피크민");
                params.put("date", makeTodayMessage());
                params.put("timeId", "1");

                createBadReservation(params);
            }

            @Test
            void 날짜_null_검증_테스트() {
                makeReservationTime();

                Map<String, String> params = new HashMap<>();
                params.put("name", "밍트");
                params.put("date", null);
                params.put("timeId", "1");

                createBadReservation(params);
            }

            @Test
            void 과거_날짜_검증_테스트() {
                makeReservationTime();

                Map<String, String> params = new HashMap<>();
                params.put("name", "밍트");
                params.put("date", makeYesterdayMessage());
                params.put("timeId", "1");

                createBadReservation(params);
            }

            @Test
            void 시간_검증_테스트() {
                Map<String, String> params = new HashMap<>();
                params.put("name", "밍트");
                params.put("date", makeTodayMessage());
                params.put("timeId", null);

                createBadReservation(params);
            }

            private void createBadReservation(final Map<String, String> params) {
                RestAssured.given().log().all()
                        .contentType(ContentType.JSON)
                        .body(params)
                        .when().post("/reservations")
                        .then().log().all()
                        .statusCode(400);
            }
        }

        @Test
        void 예약을_삭제한다() {
            makeReservationTime();

            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", makeTodayMessage());
            params.put("timeId", "1");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
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
    }

    @Nested
    class Step4 {

        @Autowired
        private JdbcTemplate jdbcTemplate;

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
    }

    @Nested
    class Step5 {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        void 오단계() {
            makeReservationTime();

            String date = LocalDate.now().toString();
            jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", date, "1");

            List<Reservation> reservations = RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .extract().jsonPath().getList(".", Reservation.class);

            Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

            assertThat(reservations.size()).isEqualTo(count);
        }
    }

    @Nested
    class Step6 {

        @Autowired
        private JdbcTemplate jdbcTemplate;

        @Test
        void 육단계() {
            makeReservationTime();

            String date = LocalDate.now().toString();

            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", date);
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
            assertThat(countAfterDelete).isEqualTo(0);
        }
    }

    @Nested
    class Step7 {

        @Test
        void 예약시간을_생성한다() {
            makeReservationTime();
        }

        @Test
        void 예약시간을_삭제한다() {
            Map<String, String> params = new HashMap<>();
            String time = TextFixture.makeNowTime();
            params.put("startAt", time);

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

            RestAssured.given().log().all()
                    .when().get("/times")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }
    }

    private void makeReservationTime() {
        Map<String, String> params = new HashMap<>();
        String time = TextFixture.makeNowTime();
        params.put("startAt", time);

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
    }

    @Nested
    class Step8 {

        @Test
        void 팔단계() {
            Map<String, String> params = new HashMap<>();
            String time = TextFixture.makeNowTime();
            params.put("startAt", time);

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

            String date = LocalDate.now().toString();

            Map<String, Object> reservation = new HashMap<>();
            reservation.put("name", "브라운");
            reservation.put("date", date);
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
    }

    @Nested
    class Step9 {

        @Autowired
        private ReservationController reservationController;

        @Test
        void 구단계() {
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
}
