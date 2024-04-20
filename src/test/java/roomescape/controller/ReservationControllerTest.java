package roomescape.controller;

import static org.hamcrest.core.Is.is;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.repository.ReservationTimeRepository;

class ReservationControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Test
    void getReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void addAndDeleteReservation() {
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        ReservationTime savedReservationTime = reservationTimeRepository.save(reservationTime);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(makeReservationRequest(savedReservationTime.getId()))
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        reservationTimeRepository.deleteById(savedReservationTime.getId());
    }

    private String makeReservationRequest(Long timeId) {
        try {
            ReservationRequest request = new ReservationRequest(
                    "브라운",
                    LocalDate.of(2023, 8, 5),
                    timeId
            );
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
