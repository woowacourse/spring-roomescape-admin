package roomescape.reservation;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReservationApiTest {

    private static final Map<String, String> RESERVATION_BODY = new HashMap<>();
    private static final Map<String, String> TIME_BODY = new HashMap<>();

    private final JdbcTemplate jdbcTemplate;
    private final int port;

    public ReservationApiTest(
            @LocalServerPort final int port,
            @Autowired final JdbcTemplate jdbcTemplate
    ) {
        this.port = port;
        this.jdbcTemplate = jdbcTemplate;
    }

    @BeforeAll
    static void initParams() {
        RESERVATION_BODY.put("name", "브라운");
        RESERVATION_BODY.put("date", "2023-08-05");
        RESERVATION_BODY.put("timeId", "1");

        TIME_BODY.put("startAt", "10:00");
    }

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM RESERVATION");
        jdbcTemplate.update("DELETE FROM RESERVATION_TIME");
        jdbcTemplate.update("ALTER TABLE RESERVATION ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE RESERVATION_TIME ALTER COLUMN id RESTART WITH 1");
    }

    @DisplayName("예약을 생성하고, 200 OK를 응답")
    @Test
    void post() {
        // given
        givenCreateReservationTime();

        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @DisplayName("존재하지 않는 시간을 선택하면, 400을 응답한다.")
    @Test
    void post2() {
        // given
        givenCreateReservationTime();
        givenDeleteReservationTime();

        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @DisplayName("존재하는 모든 예약과 200 OK를 응답")
    @Test
    void get1() {
        // given
        givenCreateReservationTime();
        givenCreateReservation();

        // when & then
        RestAssured.given().port(port).log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약이 존재하지 않는다면 200 OK와 빈 컬렉션 응답")
    @Test
    void get2() {
        // given & when & then
        RestAssured.given().port(port).log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("주어진 아이디에 해당하는 예약이 있다면 200 OK 응답")
    @Test
    void remove1() {
        // given
        givenCreateReservationTime();
        givenCreateReservation();

        // when & then
        RestAssured.given().port(port).log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("주어진 아이디에 해당하는 예약이 없다면 404로 응답한다.")
    @Test
    void remove2() {
        // given & when & then
        RestAssured.given().port(port).log().all()
                .when().delete("/reservations/1000")
                .then().log().all()
                .statusCode(404);
    }

    private void givenCreateReservationTime() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    private void givenCreateReservation() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    private void givenDeleteReservationTime(){
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}
