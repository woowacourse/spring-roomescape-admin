package roomescape;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.domain.reservation.Reservation;
import roomescape.domain.time.ReservationTime;
import roomescape.repository.reservation.ReservationDao;
import roomescape.repository.time.ReservationTimeDao;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;
    @Autowired
    private ReservationDao reservationDao;

    @Test
    void 홈_화면을_응답할_수_있다() {
        RestAssured.given().log().all()
            .when().get("/")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    void 예약_조회_화면을_응답할_수_있다() {
        RestAssured.given().log().all()
            .when().get("/admin/reservation")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    void 예약_목록_데이터를_조회할_수_있다() {
        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(0));
    }

    @Test
    void 예약을_생성할_수_있다() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", 1);

        reservationTimeDao.save(new ReservationTime(LocalTime.now().withNano(0)));

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/reservations")
            .then().log().all()
            .statusCode(200);

        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1))
            .body("[0].id", is(1))
            .body("[0].name", is("브라운"))
            .body("[0].date", is("2023-08-05"))
            .body("[0].time.id", is(1));
    }

    @Test
    void 예약을_삭제할_수_있다() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.now().withNano(0));
        reservationTimeDao.save(reservationTime);
        reservationDao.save(new Reservation("r1", LocalDate.now(), reservationTime));

        RestAssured.given().log().all()
            .when().delete("/reservations/1")
            .then().log().all()
            .statusCode(200);

        RestAssured.given().log().all()
            .when().delete("/reservations/0")
            .then().log().all()
            .statusCode(404);

        RestAssured.given().log().all()
            .when().get("/reservations")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(0));

    }

    @Test
    void 존재하지_않는_예약을_삭제할_수_없다() {
        RestAssured.given().log().all()
            .when().delete("/reservations/0")
            .then().log().all()
            .statusCode(404);
    }

    @Test
    void 칠단계() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(params)
            .when().post("/times")
            .then().log().all()
            .statusCode(200);

        RestAssured.given().log().all()
            .when().get("/times")
            .then().log().all()
            .statusCode(200)
            .body("size()", is(1));

        RestAssured.given().log().all()
            .when().delete("/times/1")
            .then().log().all()
            .statusCode(200);
    }

    @Test
    void 팔단계() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        reservationTimeDao.save(new ReservationTime(LocalTime.now().withNano(0)));

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

