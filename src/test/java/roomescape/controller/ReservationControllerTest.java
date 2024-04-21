package roomescape.controller;

import static org.assertj.core.api.Assertions.assertThat;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import roomescape.dto.ReservationDto;

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
        List<ReservationDto> reservations = RestAssured.given().port(port)
                .when().get("/reservations")
                .then().extract().body()
                .jsonPath().getList("", ReservationDto.class);
        return reservations.size();
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void addReservationTest() {
        Map<String, String> params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
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

        assertThat(message).isEqualTo("이름은 공백일 수 없습니다.");
    }

    @DisplayName("지정한 예약을 삭제한다.")
    @Test
    void reservationAddRemoveTest() {
        Map<String, String> params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "time", "15:40"
        );

        ReservationDto reservationResponse = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .extract().as(ReservationDto.class);

        Long newReservationId = reservationResponse.id();
        int initialReservationsCount = getTotalReservationsCount();

        RestAssured.given().log().all()
                .when().delete("/reservations/" + newReservationId)
                .then().log().all()
                .statusCode(200);

        assertThat(getTotalReservationsCount()).isEqualTo(initialReservationsCount - 1);
    }
}
