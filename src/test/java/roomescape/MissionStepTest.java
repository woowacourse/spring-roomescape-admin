package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Nested
    class GivenTest {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        @DisplayName("루트 페이지에 연결 가능하다.")
        @Test
        void connect_route_page() {
            RestAssured.given().log().all()
                    .when().get("/")
                    .then().log().all()
                    .statusCode(200);
        }

        @DisplayName("관리자는 모든 예약을 조회할 수 있다.")
        @Test
        void admin_can_check_all_reservation() {
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

        @DisplayName("예약을 추가할 수 있다.")
        @Test
        void add_reservation() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2023-08-05");
            params.put("timeId", "1");

            jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES ?",
                    LocalTime.of(10,0));

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201)
                    .body("id", is(1));

            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(1));

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

        @DisplayName("시간 정보를 추가, 조회, 삭제할 수 있다.")
        @Test
        void create_get_delete_time() {
            Map<String, String> params = new HashMap<>();
            params.put("startAt", "10:00");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/times")
                    .then().log().all()
                    .statusCode(201);

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

        @DisplayName("예약을 추가할 때, 이미 추가된 time을 사용한다.")
        @Test
        void use_reservation_time_in_db_when_create_reservation() {
            Map<String, Object> reservation = new HashMap<>();
            reservation.put("name", "브라운");
            reservation.put("date", "2023-08-05");
            reservation.put("timeId", 1);

            jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES ?",
                    LocalTime.of(10,0));

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(reservation)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201);

            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(1));
        }
    }

    @Nested
    class GivenDBTest {
        @Autowired
        private JdbcTemplate jdbcTemplate;

        @DisplayName("DB를 연결을 확인한다.")
        @Test
        void test_connection_DB() {
            try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
                assertThat(connection).isNotNull();
                assertThat(connection.getCatalog()).isEqualTo("DATABASE");
                assertThat(connection.getMetaData().getTables(null, null, "RESERVATION", null).next()).isTrue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        @DisplayName("DB를 이용해 예약을 조회한다.")
        @Test
        void check_reservations_in_db() {
            // ReservationTime 추가
            jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES ?",
                    LocalTime.of(10,0));

            jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)", "브라운", "2023-08-05", 1);

            List<Reservation> reservations = RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200).extract()
                    .jsonPath().getList(".", Reservation.class);

            Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

            assertThat(reservations.size()).isEqualTo(count);
        }

        @DisplayName("DB를 이용해 예약을 추가하고 삭제한다.")
        @Test
        void save_and_delete_reservation_in_db() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2023-08-05");
            params.put("timeId", "1");

            jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES ?",
                    LocalTime.of(10,0));

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(201);

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
}

