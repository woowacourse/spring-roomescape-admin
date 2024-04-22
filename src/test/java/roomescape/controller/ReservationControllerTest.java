package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;
import roomescape.fixture.Fixture;
import roomescape.repository.ReservationRepository;
import roomescape.repository.ReservationTimeRepository;

class ReservationControllerTest extends BaseControllerTest {

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    private ReservationTime reservationTime;
    private Reservation reservation1, reservation2;

    @BeforeEach
    void setUp() {
        reservationTime = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);
        reservation1 = reservationRepository.save(Fixture.reservation("브라운", 2024, 4, 22, reservationTime));
        reservation2 = reservationRepository.save(Fixture.reservation("구름", 2024, 4, 23, reservationTime));
    }

    @Test
    void getAllReservations() {
        Response response = RestAssured.given().log().all()
                .when().get("/reservations");

        List<ReservationResponse> reservationResponses = getReservationResponses(response);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(200);
            softly.assertThat(reservationResponses).hasSize(2);
            softly.assertThat(reservationResponses).containsExactly(
                    ReservationResponse.from(reservation1),
                    ReservationResponse.from(reservation2)
            );
        });
    }

    @Test
    void addReservation() {
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(makeReservationRequest(reservationTime.getId()))
                .when().post("/reservations");

        ReservationResponse reservationResponse = getReservationResponse(response);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(201);
            softly.assertThat(response.getHeader("Location"))
                    .isEqualTo("/reservations/" + reservationResponse.id());
        });
    }

    @Test
    void deleteReservation() {
        ReservationTime savedReservationTime = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);

        Response response = RestAssured.given().log().all()
                .when().delete("/reservations/" + savedReservationTime.getId());

        assertThat(response.getStatusCode()).isEqualTo(204);
    }

    private String makeReservationRequest(Long timeId) {
        try {
            ReservationRequest request = new ReservationRequest(
                    "브라운",
                    LocalDate.of(2024, 4, 22),
                    timeId
            );
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private ReservationResponse getReservationResponse(Response response) {
        return response.jsonPath().getObject(".", ReservationResponse.class);
    }

    private List<ReservationResponse> getReservationResponses(Response response) {
        return response.jsonPath().getList(".", ReservationResponse.class);
    }
}
