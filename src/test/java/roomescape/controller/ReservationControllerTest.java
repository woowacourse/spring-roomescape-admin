package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import roomescape.controller.dto.ReservationResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReservationControllerTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUpTestPort() {
        RestAssured.port = port;
    }

    @DisplayName("저장된 모든 예약 리스트를 조회한다.")
    @Test
    void getAllReservationsTest() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    private int getTotalReservationsCount() {
        List<ReservationResponse> reservations = RestAssured.given().port(port)
                .when().get("/reservations")
                .then().extract().body()
                .jsonPath().getList("", ReservationResponse.class);
        return reservations.size();
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addReservationTest() {
        Map<String, String> params = Map.of(
                "name", "브라운",
                "date", "2024-08-05",
                "time", "15:40"
        );

        int initialReservationsCount = getTotalReservationsCount();
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        assertThat(getTotalReservationsCount()).isEqualTo(initialReservationsCount + 1);
    }

    @DisplayName("예약자명이 공백일 경우 400 상태 코드와 함께 오류 메시지를 응답한다.")
    @Test
    void addReservationWithBlankNameTest() {
        Map<String, String> params = Map.of(
                "name", "   ",
                "date", "2024-08-05",
                "time", "15:40"
        );

        String message = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .extract().body().jsonPath()
                .get("message");

        assertThat(message).isEqualTo("이름은 공백일 수 없습니다.");
    }

    @DisplayName("예약 날짜와 시각 정보를 잘못된 형식으로 요청할 경우 400 상태 코드와 함께 오류 메시지를 응답한다.")
    @ParameterizedTest
    @MethodSource("inValidDateTime")
    void addReservationWithInValidDateTimeTest(Map<String, String> params) {
        String message = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .extract().body().jsonPath()
                .get("message");

        assertThat(message).isEqualTo("날짜/시간 포맷이 잘못되었습니다.");
    }

    static Stream<Arguments> inValidDateTime() {
        return Stream.of(
                Arguments.of(
                        Map.of(
                                "name", "브라운",
                                "date", "1234-111-22",
                                "time", "15:40"
                        )
                ),
                Arguments.of(
                        Map.of(
                                "name", "웨지",
                                "date", "2024-08-05",
                                "time", "26:99"
                        )
                )
        );
    }

    @DisplayName("과거의 시간을 예약하는 경우 400 상태 코드와 함께 오류 메시지를 응답한다.")
    @Test
    void addReservationWithPastTime() {
        Map<String, String> params = Map.of(
                "name", "페드로",
                "date", "2023-08-05",
                "time", "15:40"
        );

        String message = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400)
                .extract().body().jsonPath()
                .get("message");

        assertThat(message).isEqualTo("과거의 시각은 예약할 수 없습니다.");
    }

    @DisplayName("지정한 예약을 삭제한다.")
    @Test
    void removeReservationTest() {
        Map<String, String> params = Map.of(
                "name", "브라운",
                "date", "2024-08-05",
                "time", "15:40"
        );

        ReservationResponse reservationResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .extract().as(ReservationResponse.class);

        Long newReservationId = reservationResponse.id();
        int initialReservationsCount = getTotalReservationsCount();

        RestAssured.given().log().all()
                .when().delete("/reservations/" + newReservationId)
                .then().log().all()
                .statusCode(200);

        assertThat(getTotalReservationsCount()).isEqualTo(initialReservationsCount - 1);
    }

    @DisplayName("삭제할 id에 해당하는 예약이 없는 경우 204 를 응답한다.")
    @Test
    void removeNotExistReservationTest() {
        int neverExistId = 0;
        RestAssured.given().log().all()
                .when().delete("/reservations/" + neverExistId)
                .then().log().all()
                .statusCode(204);
    }
}
