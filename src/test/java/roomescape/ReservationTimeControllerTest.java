package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.controller.dto.CreateReservationTimeRequest;
import roomescape.reservationtime.dao.ReservationTimeDao;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @Autowired
    ReservationDao reservationDao;

    @Test
    void 예약_시간_생성() {
        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(13, 0));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));
    }

    @Test
    void 예약_시간_조회() {
        ReservationTime reservationTime = ReservationTime.createWithoutId(LocalTime.of(13, 0));
        reservationTimeDao.save(reservationTime);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("[0].id", is(1));
    }

    @Test
    void 예약_시간_삭제() {
        ReservationTime reservationTime = ReservationTime.createWithoutId(LocalTime.of(13, 0));
        reservationTimeDao.save(reservationTime);

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 존재하지_않는_예약_시간_삭제시_성공한다() {
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 예약_시간_삭제시_해당_시간대에_예약이_존재하면_예외가_발생한다() {
        ReservationTime reservationTime = ReservationTime.createWithoutId(LocalTime.of(13, 0));
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        reservationDao.save(Reservation.createWithoutId("방탈출", LocalDate.of(2026, 5, 2),
                savedReservationTime));

        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(400)
                .body("message", is("해당 시간에 예약이 존재합니다."));
    }
}
