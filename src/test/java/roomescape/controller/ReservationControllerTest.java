package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dao.ReservationDao;
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

import static org.hamcrest.Matchers.is;
import static roomescape.TestConstant.TEST_DATE;
import static roomescape.TestConstant.TEST_NAME;
import static roomescape.TestConstant.TEST_START_AT;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationDao reservationDao;

    private final Map<String, Object> reservation = createReservationRequest();

    @DisplayName("예약을 생성한다.")
    @Test
    void postReservation() {
        // given
        reservationTimeDao.save(new ReservationTime(TEST_START_AT));

        // when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약을 전부 조회한다.")
    @Test
    void getReservations() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(TEST_START_AT));
        reservationDao.save(new Reservation(TEST_NAME, TEST_DATE, savedReservationTime));

        // when & then
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(TEST_START_AT));
        Reservation savedReservation = reservationDao.save(new Reservation(TEST_NAME, TEST_DATE, savedReservationTime));

        // when & then
        RestAssured.given().log().all()
                .when().delete("/reservations/" + savedReservation.getId())
                .then().log().all()
                .statusCode(200);
    }

    private Map<String, Object> createReservationRequest() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        return reservation;
    }
}
