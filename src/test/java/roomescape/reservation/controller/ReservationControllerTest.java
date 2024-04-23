package roomescape.reservation.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.reservation.dto.ReservationRequest;
import roomescape.reservation.dto.ReservationTimeRequest;
import roomescape.reservation.dto.ReservationTimeResponse;
import roomescape.reservation.service.ReservationService;
import roomescape.reservation.service.ReservationTimeService;
import roomescape.util.ControllerTest;

@DisplayName("예약 API 통합 테스트")
class ReservationControllerTest extends ControllerTest {
    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationTimeService reservationTimeService;

    ReservationTimeResponse reservationTimeResponse;

    @BeforeEach
    void setUp() {
        reservationTimeResponse = reservationTimeService.create(new ReservationTimeRequest(LocalTime.MIDNIGHT));
        reservationService.create(new ReservationRequest(
                "choco",
                LocalDate.of(2023, 4, 23),
                reservationTimeResponse.id())
        );
    }

    @DisplayName("예약 생성 시 200을 반환한다.")
    @Test
    void create() {
        //given
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", reservationTimeResponse.id());

        //when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약 삭제 시 200을 반환한다.")
    @Test
    void delete() {
        //given & when &then
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("예약 조회 시 200을 반환한다.")
    @Test
    void find() {
        //given & when & then
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("존재하지 않은 예약 삭제 시 400를 반환한다.")
    @Test
    void reservationNotFound() {
        //given & when & then
        RestAssured.given().log().all()
                .when().delete("/reservations/6")
                .then().log().all()
                .statusCode(400);
    }
}
