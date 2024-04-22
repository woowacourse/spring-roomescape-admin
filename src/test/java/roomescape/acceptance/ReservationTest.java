package roomescape.acceptance;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.controller.dto.ReservationRequest;
import roomescape.domain.Reservation;
import roomescape.domain.TimeSlot;
import roomescape.repository.ReservationRepository;
import roomescape.repository.TimeSlotRepository;
import roomescape.service.dto.ReservationCreationDto;
import roomescape.service.dto.TimeSlotDto;

class ReservationTest extends AcceptanceTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private TimeSlotRepository timeSlotRepository;

    @AfterEach
    void tearDown() {
        reservationRepository.deleteAll();
    }

    @Test
    @DisplayName("전체 예약을 조회한다.")
    void getAllReservationsTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("예약을 성공적으로 추가한다.")
    void addReservationTest() {
        TimeSlotDto timeCreationDto = new TimeSlotDto(LocalTime.parse("12:00"));
        TimeSlot timeSlot = timeSlotRepository.create(timeCreationDto);
        ReservationRequest request = new ReservationRequest("브라운", "2023-08-05", timeSlot.getId());

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("예약을 성공적으로 삭제한다.")
    void deleteReservationTest() {
        TimeSlotDto timeCreationDto = new TimeSlotDto(LocalTime.parse("12:00"));
        TimeSlot timeSlot = timeSlotRepository.create(timeCreationDto);
        ReservationCreationDto reservationCreationDto = new ReservationCreationDto(
                "웨지", "2024-04-20", TimeSlotDto.from(timeSlot)
        );
        Reservation reservation = reservationRepository.addReservation(reservationCreationDto);

        RestAssured.given().log().all()
                .when().delete("/reservations/" + reservation.getId())
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}
