package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.dto.request.ReservationTimeRequest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class JdbcReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void insertReservationTime() {
        String sql = "INSERT INTO reservation_time (start_at) VALUES(?)";
        ReservationTimeRequest reservationRequest = new ReservationTimeRequest(LocalTime.of(10, 0));

        jdbcTemplate.update(sql, reservationRequest.startAt());
    }

    @DisplayName("시간 추가 API 테스트")
    @Test
    void insert() {
        ReservationTimeRequest reservationRequest = new ReservationTimeRequest(LocalTime.of(10, 0));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationRequest)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("모든 시간 조회 API 테스트")
    @Test
    void findAllReservationTimes() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("시간 삭제 API 테스트")
    @Test
    void delete() {
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}
