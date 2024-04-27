package roomescape.time;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("시간을 조회한다.")
    @Test
    void findAll() {
        insertReservationTime("09:00");
        insertReservationTime("10:00");

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @DisplayName("시간을 추가한다.")
    @Test
    void create() {
        insertReservationTime("11:00");
        List<ReservationTime> savedTimes = reservationTimeDao.findAll();
        assertThat(savedTimes.size()).isEqualTo(1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime())
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        savedTimes = reservationTimeDao.findAll();
        assertThat(savedTimes.size()).isEqualTo(2);
    }

    @DisplayName("시간을 삭제한다.")
    @Test
    void delete() {
        long id = insertReservationTimeAndGetId("13:00");
        List<ReservationTime> savedTimes = reservationTimeDao.findAll();
        assertThat(savedTimes.size()).isEqualTo(1);

        RestAssured.given().log().all()
                .when().delete("/times/" + id)
                .then().log().all()
                .statusCode(200);

        savedTimes = reservationTimeDao.findAll();
        assertThat(savedTimes.size()).isEqualTo(0);
    }

    ReservationTime reservationTime() {
        return new ReservationTime(0, LocalTime.parse("10:00"));
    }

    void insertReservationTime(String time) {
        insertReservationTimeAndGetId(time);
    }

    long insertReservationTimeAndGetId(String time) {
        return reservationTimeDao.save(new ReservationTime(0, LocalTime.parse(time))).id();
    }
}
