package roomescape.acceptance;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import roomescape.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationTimeAcceptanceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("예약 시간 전체 조회")
    @Test
    void get_reservationTimes() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약 시간 생성")
    @Test
    void post_reservationTime() {
        ReservationTime reservationTime = new ReservationTime(null, "10:00");
        ReservationTime expectedCreatedTime = new ReservationTime(1L, "10:00");

        ReservationTime createdTime = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1")
                .extract().as(ReservationTime.class);

        assertThat(createdTime).isEqualTo(expectedCreatedTime);
        assertThat(countReservationTimes()).isEqualTo(1);
    }

    @DisplayName("예약 시간 삭제")
    @Test
    void delete_reservationTime() {
        insertDefaultData();

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);

        assertThat(countReservationTimes()).isZero();
    }

    private void insertDefaultData() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES (?)", "10:00");
    }

    private Integer countReservationTimes() {
        return jdbcTemplate.queryForObject("SELECT count(1) from reservation_time", Integer.class);
    }

}
