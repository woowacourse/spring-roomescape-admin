package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.util.UriComponentsBuilder;
import roomescape.dao.ReservationDAO;
import roomescape.dto.ReservationReqDto;
import roomescape.dto.ReservationResDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.fixture.ReservationDAOFixture;
import roomescape.model.Reservation;
import roomescape.service.ReservationService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("/test-data.sql")
class ReservationControllerTest {

    private final ReservationDAO reservationDAO = new ReservationDAOFixture(new JdbcTemplate());
    private final ReservationService reservationService = new ReservationService(reservationDAO);
    private final ReservationController reservationController = new ReservationController(reservationService);

    @Test
    @DisplayName("예약 목록을 받아 응답 객체에 넣어 반환한다")
    void readReservations() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2024, 8, 10);
        long timeId = 1;
        LocalTime startAt = LocalTime.of(10, 0);

        // when
        Reservation reservation = reservationDAO.addAndGet(name, date, timeId);
        ReservationResDto reservationResDto = new ReservationResDto(reservation.getId(), name, date, new ReservationTimeResDto(timeId, startAt));
        ResponseEntity<List<ReservationResDto>> actual = reservationController.readAll();
        ResponseEntity<List<ReservationResDto>> expected = ResponseEntity.ok(List.of(reservationResDto));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("예약을 생성하고 결과를 응답 객체로 반환한다")
    void createReservation() {
        // given
        String name = "브라운";
        LocalDate date = LocalDate.of(2024, 8, 10);
        long timeId = 1;
        LocalTime startAt = LocalTime.of(10, 0);

        // when
        ReservationReqDto reservationReqDto = new ReservationReqDto(name, date, timeId);
        ResponseEntity<ReservationResDto> actual = reservationController.create(reservationReqDto, UriComponentsBuilder.newInstance());
        ResponseEntity<ReservationResDto> expected = ResponseEntity.created(URI.create("reservations/1"))
                .body(new ReservationResDto(1L, name, date, new ReservationTimeResDto(timeId, startAt)));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("예약을 삭제하고 결과를 응답 객체로 반환한다")
    void deleteReservation() {
        // given
        Reservation reservation = reservationDAO.addAndGet("브라운", LocalDate.of(2024, 8, 10), 1);

        // when

        ResponseEntity<Void> actual = reservationController.delete(reservation.getId());
        ResponseEntity<Void> expected = ResponseEntity.noContent().build();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("API 엔드포인트를 통해 예약 목록을 조회한다")
    void readReservationsEndpoint() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("API 엔드포인트를 통해 예약을 추가한다")
    void createReservationEndpoint() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .body("id", is(2));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @DisplayName("API 엔드포인트를 통해 예약을 삭제한다")
    void deleteReservationEndpoint() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("예약을 추가할 수 없으면 오류 상태코드를 반환한다")
    void createReservationException() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "");
        params.put("date", "2023-08-05");
        params.put("timeId", "1");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(500);
    }

    @Test
    @DisplayName("예약을 삭제할 수 없으면 오류 상태코드를 반환한다")
    void deleteReservationException() {
        RestAssured.given().log().all()
                .when().delete("/reservations/100")
                .then().log().all()
                .statusCode(500);
    }
}
