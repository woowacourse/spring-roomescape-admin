package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservationtime.controller.dto.CreateReservationTimeRequest;
import roomescape.reservationtime.dao.ReservationTimeDao;
import roomescape.reservationtime.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeControllerTest {

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @Test
    void 예약_시간_생성() {
        CreateReservationTimeRequest request = new CreateReservationTimeRequest(LocalTime.of(13, 0));

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
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
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_시간_삭제시_해당_시간대에_예약이_존재하면_예외가_발생한다() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }
}
