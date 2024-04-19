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
import roomescape.Reservation;

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
        Reservation reservation = new Reservation(null, "브라운", "2023-08-05", "15:40");
        Reservation expectedReservation = new Reservation(1L, "브라운", "2023-08-05", "15:40");

        Reservation createdReservation = RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1")
                .extract().as(Reservation.class);

        assertThat(createdReservation).isEqualTo(expectedReservation);
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
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)",
                "브라운", "2023-08-05", "15:40");
    }

    private Integer countReservation() {
        return jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
    }

}
