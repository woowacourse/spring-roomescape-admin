package roomescape.controller;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.net.URI;
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
import roomescape.dao.ReservationTimeDAO;
import roomescape.dto.ReservationTimeReqDto;
import roomescape.dto.ReservationTimeResDto;
import roomescape.fixture.ReservationTimeDAOFixture;
import roomescape.model.ReservationTime;
import roomescape.service.ReservationTimeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql("/test-data.sql")
class ReservationTimeControllerTest {

    private final ReservationTimeDAO reservationTimeDAO = new ReservationTimeDAOFixture(new JdbcTemplate());
    private final ReservationTimeService reservationTimeService = new ReservationTimeService(reservationTimeDAO);
    private final ReservationTimeController reservationTimeController = new ReservationTimeController(reservationTimeService);

    @Test
    @DisplayName("시간을 받아 응답 객체에 넣어 반환한다")
    void readReservationTimes() {
        // given
        LocalTime startAt = LocalTime.of(10, 0);

        // when
        ReservationTime reservationTime = reservationTimeDAO.addAndGet(startAt);
        ReservationTimeResDto reservationTimeResDto = new ReservationTimeResDto(reservationTime.getId(), startAt);
        ResponseEntity<List<ReservationTimeResDto>> actual = reservationTimeController.readAll();
        ResponseEntity<List<ReservationTimeResDto>> expected = ResponseEntity.ok(List.of(reservationTimeResDto));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("시간을 생성하고 결과를 응답 객체로 반환한다")
    void createReservationTime() {
        // given
        LocalTime startAt = LocalTime.of(10, 0);

        // when
        ReservationTimeReqDto reservationTimeReqDto = new ReservationTimeReqDto(startAt);
        ResponseEntity<ReservationTimeResDto> actual = reservationTimeController.create(reservationTimeReqDto, UriComponentsBuilder.newInstance());
        ResponseEntity<ReservationTimeResDto> expected = ResponseEntity.created(URI.create("times/1"))
                .body(new ReservationTimeResDto(1L, startAt));

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("시간을 삭제하고 결과를 응답 객체로 반환한다")
    void deleteReservationTime() {
        // given
        ReservationTime reservationTime = reservationTimeDAO.addAndGet(LocalTime.of(10, 0));

        // when

        ResponseEntity<Void> actual = reservationTimeController.delete(reservationTime.getId());
        ResponseEntity<Void> expected = ResponseEntity.noContent().build();

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("API 엔드포인트를 통해 시간 목록을 조회한다")
    void readReservationTimesEndpoint() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    @DisplayName("API 엔드포인트를 통해 시간을 추가한다")
    void createReservationTimeEndpoint() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "11:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .body("id", is(2));

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(2));
    }

    @Test
    @DisplayName("API 엔드포인트를 통해 예약을 삭제한다")
    void deleteReservationTimeEndpoint() {
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("시간을 추가할 수 없으면 오류 상태코드를 반환한다")
    void createReservationTimeException() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(500);
    }

    @Test
    @DisplayName("시간을 삭제할 수 없으면 오류 상태코드를 반환한다")
    void deleteReservationTimeException() {
        RestAssured.given().log().all()
                .when().delete("/times/100")
                .then().log().all()
                .statusCode(500);
    }
}
