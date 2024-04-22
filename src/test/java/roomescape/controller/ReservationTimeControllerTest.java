package roomescape.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeRequest;
import roomescape.dto.ReservationTimeResponse;
import roomescape.fixture.Fixture;
import roomescape.repository.ReservationTimeRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "classpath:truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ReservationTimeControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    private ReservationTime reservationTime1, reservationTime2;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        reservationTime1 = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);
        reservationTime2 = reservationTimeRepository.save(Fixture.RESERVATION_TIME_2);
    }

    @Test
    void getAllReservationTimes() {
        Response response = RestAssured.given().log().all()
                .when().get("/times");

        List<ReservationTimeResponse> reservationTimeResponses = getReservationTimeResponses(response);

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(200);
            softly.assertThat(reservationTimeResponses).hasSize(2);
            softly.assertThat(reservationTimeResponses).containsExactly(
                    ReservationTimeResponse.from(reservationTime1),
                    ReservationTimeResponse.from(reservationTime2)
            );
        });
    }

    @Test
    void addReservationTime() {
        Response response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(makeReservationTimeRequest())
                .when().post("/times");

        SoftAssertions.assertSoftly(softly -> {

            softly.assertThat(response.getStatusCode()).isEqualTo(201);
            softly.assertThat(response.getHeader("Location"))
                    .isEqualTo("/times/" + response.jsonPath().getLong("id"));
        });
    }

    @Test
    void deleteReservationTime() {
        ReservationTime reservationTime = reservationTimeRepository.save(Fixture.RESERVATION_TIME_1);

        Response response = RestAssured.given().log().all()
                .when().delete("/times/" + reservationTime.getId());

        SoftAssertions.assertSoftly(softly -> {
            softly.assertThat(response.getStatusCode()).isEqualTo(204);
        });
    }

    private String makeReservationTimeRequest() {
        try {
            ReservationTimeRequest request = new ReservationTimeRequest(
                    LocalTime.of(15, 40)
            );
            return mapper.writeValueAsString(request);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<ReservationTimeResponse> getReservationTimeResponses(Response response) {
        return response.jsonPath().getList(".", ReservationTimeResponse.class);
    }
}
