package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.ReservationTime;
import roomescape.dto.response.ReservationResponse;

@SpringBootTest(
        webEnvironment = WebEnvironment.DEFINED_PORT,
        properties = "spring.datasource.url=jdbc:h2:mem:testdb"
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    private static final ReservationTime TEST_RESERVATION_TIME = new ReservationTime(1L, LocalTime.MIDNIGHT);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update(
                "insert into reservation_time (id, start_at) values (?, ?)",
                TEST_RESERVATION_TIME.getId(),
                TEST_RESERVATION_TIME.getTime()
        );
    }

    @Test
    void 예약_목록_요청을_성공한다() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 예약_추가_요청을_성공한다() {
        Map<String, String> params = saveReservationData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 요청으로_추가된_예약_정보를_응답한다() {
        Map<String, String> params = saveReservationData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .body("id", is(1));
    }

    @Test
    void 예약_추가로_목록_크기가_증가한다() {
        createAndSendReservation();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void 예약_삭제_요청을_성공한다() {
        createAndSendReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_삭제_ID가_일치하지_않는_경우_요청에_실패한다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/2")
                .then().log().all()
                .statusCode(404);
    }

    @Test
    void 예약_삭제_요청으로_데이터가_삭제된다() {
        createAndSendReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1");

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void DB_테이블_레코드_수와_API_응답_크기가_일치한다() {
        List<ReservationResponse> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", ReservationResponse.class);

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);

        assertThat(reservations.size()).isEqualTo(count);
    }

    @Test
    void 예약_추가_후_DB_테이블_레코드가_증가한다() {
        createAndSendReservation();

        Integer count = jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);

        assertThat(count).isEqualTo(1);
    }

    @Test
    void 예약_삭제_후_DB_테이블_레코드가_감소한다() {
        createAndSendReservation();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(*) from reservation", Integer.class);

        assertThat(countAfterDelete).isEqualTo(0);
    }

    private void createAndSendReservation() {
        Map<String, String> params = saveReservationData();

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations");
    }

    private Map<String, String> saveReservationData() {
        return Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", "1"
        );
    }
}
