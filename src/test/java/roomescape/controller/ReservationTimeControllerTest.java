package roomescape.controller;

import static org.hamcrest.Matchers.is;
import static roomescape.TestConstant.TEST_START_AT;

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
import roomescape.dao.ReservationTimeDao;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    private final Map<String, String> times = createReservationTimeRequest();

    @DisplayName("예약시간을 생성한다.")
    @Test
    void postTime() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(times)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약시간을 전부 조회한다.")
    @Test
    void getReservationTimes() {
        // given
        reservationTimeDao.save(new ReservationTime(TEST_START_AT));

        // when & then
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약시간을 삭제한다.")
    @Test
    void delete() {
        // given
        ReservationTime savedReservationTime = reservationTimeDao.save(new ReservationTime(TEST_START_AT));

        // when & then
        RestAssured.given().log().all()
                .when().delete("/times/" + savedReservationTime.getId())
                .then().log().all()
                .statusCode(200);
    }

    private Map<String, String> createReservationTimeRequest() {
        Map<String, String> times = new HashMap<>();
        times.put("startAt", "10:00");

        return times;
    }
}
