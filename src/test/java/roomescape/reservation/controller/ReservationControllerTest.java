package roomescape.reservation.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.util.ControllerTest;
import roomescape.reservation.domain.ReservationTime;
import roomescape.reservation.repository.ReservationRepository;
import roomescape.reservation.repository.ReservationTimeRepository;

class ReservationControllerTest extends ControllerTest {
    @Autowired
    ReservationTimeRepository reservationTimeRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @DisplayName("예약 생성, 삭제 시 200을 반환한다.")
    @Test
    void createAndDelete() {
        //given
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        reservationTimeRepository.save(new ReservationTime(1L, LocalTime.MIDNIGHT));

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약 조회 시 200을 반환한다.")
    @Test
    void find() {
        //given
        String url = "/reservations";

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get(url)
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("존재하지 않은 예약 삭제 시 404를 반환한다.")
    @Test
    void reservationNotFound() {
        //given & when & then
        RestAssured.given().log().all()
                .when().delete("/reservations/6")
                .then().log().all()
                .statusCode(404);
    }
}
