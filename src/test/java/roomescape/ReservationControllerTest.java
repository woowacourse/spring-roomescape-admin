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
import roomescape.reservation.controller.dto.CreateReservationRequest;
import roomescape.reservation.dao.ReservationDao;
import roomescape.reservation.domain.Reservation;
import roomescape.reservationtime.dao.ReservationTimeDao;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @Autowired
    ReservationDao reservationDao;

    @Test
    void 예약_생성() {
        ReservationTime reservationTime = ReservationTime.createWithoutId(LocalTime.of(13, 0));
        ReservationTime savedTime = reservationTimeDao.save(reservationTime);
        CreateReservationRequest request = new CreateReservationRequest("김철수",
                LocalDate.of(2026, 5, 2), savedTime.getId());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(1));
    }

    @Test
    void 예약_생성시_예약_시간이_없는_경우_예외가_발생한다() {
        CreateReservationRequest request = new CreateReservationRequest("김철수",
                LocalDate.of(2026, 5, 2), 1);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .body("message", is("존재하지 않는 예약 시간입니다."));
    }

    @Test
    void 예약_조회() {
        ReservationTime reservationTime = ReservationTime.createWithoutId(LocalTime.of(13, 0));
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        reservationDao.save(Reservation.createWithoutId("방탈출", LocalDate.of(2026, 5, 2),
                savedReservationTime));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("[0].id", is(1));
    }

    @Test
    void 예약_삭제() {
        ReservationTime reservationTime = ReservationTime.createWithoutId(LocalTime.of(13, 0));
        ReservationTime savedReservationTime = reservationTimeDao.save(reservationTime);
        reservationDao.save(Reservation.createWithoutId("방탈출", LocalDate.of(2026, 5, 2),
                savedReservationTime));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    void 존재하지_않는_예약_삭제시_성공한다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }
}
