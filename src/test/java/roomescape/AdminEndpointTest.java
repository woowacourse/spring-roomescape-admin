package roomescape;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.model.Reservation;
import roomescape.repository.ReservationRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AdminEndpointTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("관리자 메인 페이지 응답")
    @Test
    void adminPageLoad() {
        HttpRestTestTemplate.assertGetOk("/admin");
    }

    @DisplayName("예약 페이지 응답")
    @Test
    void reservationPageLoad() {
        List<Reservation> reservations = reservationRepository.findAll();
        int reservationSize = reservations.size();

        HttpRestTestTemplate.assertGetOk("/admin/reservation");
        HttpRestTestTemplate.assertGetOk("/reservations", "size()", reservationSize);
    }

    @DisplayName("예약 추가 삭제 시나리오")
    @Test
    void reservationAddAndRemoveScenario() {
        ReservationRequest reservationRequest = new ReservationRequest("브라운", LocalDate.parse("2023-08-05"), LocalTime.parse("15:40"));
        int initialCount = 0;

        HttpRestTestTemplate.assertPostCreated(reservationRequest, "/reservations", "id", 1);
        Integer countAfterInsert = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterInsert).isEqualTo(initialCount + 1);

        HttpRestTestTemplate.assertDeleteNoContent("/reservations/1");
        Integer countAfterDelete = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);
        assertThat(countAfterDelete).isEqualTo(countAfterInsert - 1);
    }

    @DisplayName("예약을 추가한 후에도 db의 예약 개수와 api 응답의 예약 개수가 동일")
    @Test
    void apiResponseCountEqualsToDataBaseReservationCount() {
        jdbcTemplate.update("INSERT INTO reservation (name, date, time) VALUES (?, ?, ?)", "브라운", "2023-08-05", "15:40");

        List<Reservation> reservations = RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200).extract()
                .jsonPath().getList(".", Reservation.class);
        Integer count = jdbcTemplate.queryForObject("SELECT count(1) from reservation", Integer.class);

        assertThat(reservations).hasSize(count);
    }
}
