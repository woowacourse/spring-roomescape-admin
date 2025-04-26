package roomescape;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.Reservation;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationAPITest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약 목록 조회 요청 시 예약 목록을 반환한다.")
    void searchReservationTest() {
        // given
        addReservation("브라운", "2023-08-05", "1");

        // when & then
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약 목록 조회 요청 시 데이터베이스에서 데이터를 반환한다.")
    void searchReservationDataBaseTest() {
        // given
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, "브라운", "2023-08-05", 1);

        // when
        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM  reservation", Integer.class);

        // then
        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    @DisplayName("예약 추가 시 추가된 내역을 반환한다.")
    void addReservationTest() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 추가 시 데이터베이스에 저장된다.")
    void addReservationDataBaseTest() {
        // given
        String sql = "INSERT INTO reservation (name, date, time_id) values (?, ?, ?)";

        // when
        jdbcTemplate.update(sql, "브라운", "2023-08-05", 1);
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation", Integer.class);

        // then
        assertThat(count).isEqualTo(1);
    }

    @Test
    @DisplayName("예약을 취소하면 목록에서 삭제된다.")
    void deleteReservationTest() {
        // given
        addReservation("브라운", "2023-08-05", "1");

        // when & then
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 취소 시 데이터베이스에서 삭제된다.")
    void deleteReservationDataBaseTest() {
        // given
        String sql = "INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, "브라운", "2023-08-05", 1);

        // when
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT COUNT(1) FROM reservation", Integer.class);

        // then
        assertThat(countAfterDelete).isEqualTo(0);
    }

    private void addReservation(String name, String date, String timeId) {
        Map<String, String> params = new HashMap<>();
        params.put("name", name);
        params.put("date", date);
        params.put("timeId", timeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약 추가, 조회 시 시간을 함께 선택한다.")
    void selectTimeTest() {
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
}
