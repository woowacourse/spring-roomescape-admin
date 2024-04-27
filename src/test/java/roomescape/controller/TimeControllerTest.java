package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.containsString;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import roomescape.service.dto.ReservationTimeAddRequest;
import roomescape.service.dto.ReservationTimeResponse;
import roomescape.service.TimeService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(value = "classpath:data-reset.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class TimeControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private final TimeService timeService;

    @Autowired
    public TimeControllerTest(TimeService timeService) {
        this.timeService = timeService;
    }

    @BeforeEach
    public void setUpTestPort() {
        RestAssured.port = port;
    }

    @DisplayName("등록된 모든 예약 가능 시간을 조회한다.")
    @Test
    void getAllReservationTimeTest() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200);
    }

    private int getTotalReservationsCount() {
        List<ReservationTimeResponse> reservationTimes = RestAssured.given().port(port)
                .when().get("/times")
                .then().extract().body()
                .jsonPath().getList("", ReservationTimeResponse.class);
        return reservationTimes.size();
    }

    @DisplayName("새로운 예약 가능 시간을 추가한다.")
    @Test
    void addReservationTimeTest() {
        Map<String, String> params = Map.of(
                "startAt", "10:00"
        );

        int initialReservationTimesCount = getTotalReservationsCount();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        assertThat(getTotalReservationsCount()).isEqualTo(initialReservationTimesCount + 1);
    }

    @DisplayName("중복된 예약 시간을 등록할 경우 400 상태 코드와 함께 오류 메시지를 응답한다.")
    @Test
    void addDuplicatedTimeTest() {
        LocalTime duplicatedTime = LocalTime.of(12, 0);
        timeService.addReservationTime(new ReservationTimeAddRequest(duplicatedTime));

        Map<String, String> params = Map.of(
                "startAt", duplicatedTime.format(DateTimeFormatter.ofPattern("HH:mm"))
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(400)
                .body(containsString("이미 등록된 시간입니다."));
    }

    @DisplayName("시간 포맷을 잘못 등록할 경우 400 상태 코드와 함께 오류 메시지를 응답한다.")
    @ParameterizedTest
    @ValueSource(strings = {"14:00:00", "26:00", "Hello"})
    void addInValidFormatTimeTest(String invalidFormattedTime) {
        Map<String, String> params = Map.of(
                "startAt", invalidFormattedTime
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(400)
                .body(containsString("날짜/시간 포맷이 잘못되었습니다."));
    }

    @DisplayName("특정 ID에 해당하는 예약 시간을 삭제한다.")
    @Test
    void removeReservationTimeTest() {
        Map<String, String> params = Map.of(
                "startAt", "12:34"
        );

        ReservationTimeResponse reservationTimeResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON).body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200)
                .extract().as(ReservationTimeResponse.class);

        Long newReservationTimeResponseId = reservationTimeResponse.id();
        int initialReservationTimesCount = getTotalReservationsCount();

        RestAssured.given().log().all()
                .when().delete("/times/" + newReservationTimeResponseId)
                .then().log().all()
                .statusCode(204);

        assertThat(getTotalReservationsCount()).isEqualTo(initialReservationTimesCount - 1);
    }

    @DisplayName("삭제할 ID에 해당하는 예약 시간이 없을 경우 204를 응답한다.")
    @Test
    void removeNotExistReservationTimeTest() {
        int neverExistId = 0;
        RestAssured.given().log().all()
                .when().delete("/times/" + neverExistId)
                .then().log().all()
                .statusCode(204);
    }

    @DisplayName("삭제할 ID에 해당하는 예약 시간에 이미 예약이 있을 경우 400을 응답한다.")
    @Test
    void removeWhenFKConstraintFails() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(400)
                .body(containsString("해당 시간에 예약이 존재합니다."));
    }
}
