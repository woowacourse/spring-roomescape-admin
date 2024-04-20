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
import roomescape.controller.dto.ReservationRequest;
import roomescape.controller.dto.ReservationResponse;
import roomescape.controller.dto.ReservationTimeResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationAcceptanceTest {

    @LocalServerPort
    private int port;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @DisplayName("전체 예약 조회")
    @Test
    void get_reservations() {
        insertDefaultData();

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약 추가")
    @Test
    void post_reservation() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES ?", "15:00");
        ReservationRequest request = new ReservationRequest("2023-08-05", "브라운", 1L);
        ReservationResponse expectedResponse = new ReservationResponse(1L, "브라운", "2023-08-05",
                new ReservationTimeResponse(1L, "15:00"));

        ReservationResponse actualResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .extract().as(ReservationResponse.class);

        assertThat(actualResponse).isEqualTo(expectedResponse);
        assertThat(countReservation()).isEqualTo(1);
    }

    @DisplayName("예약 삭제")
    @Test
    void delete_reservation() {
        insertDefaultData();

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        Integer countAfterDelete = countReservation();
        assertThat(countAfterDelete).isZero();
    }

    private void insertDefaultData() {
        jdbcTemplate.update("INSERT INTO reservation_time (start_at) VALUES ?", "15:00");
        jdbcTemplate.update("INSERT INTO reservation (name, date, time_id) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", 1L);
    }

    private Integer countReservation() {
        return jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    }

}
