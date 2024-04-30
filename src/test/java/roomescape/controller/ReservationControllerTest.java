package roomescape.controller;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @LocalServerPort
    int serverPort;

    @BeforeEach
    public void beforeEach() {
        RestAssured.port = serverPort;
    }

    @Test
    @DisplayName("예약 내역 조회 API 작동을 확인한다")
    void checkReservations() {
        RestAssured.given().log().all()
                .when().get("reservations")
                .then().log().all()
                .statusCode(200).body("size()", is(0));
    }

    @TestFactory
    @DisplayName("예약 추가와 삭제의 작동을 확인한다")
    Stream<DynamicTest> reservationCreateAndDelete() {
        Map<String, String> reservationParams = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "timeId", "1"
        );

        Map<String, String> reservationTimeParams = Map.of(
                "id", "1",
                "startAt", "10:00"
        );

        return Stream.of(
                dynamicTest("예약 시간을 추가한다", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(reservationTimeParams)
                            .when().post("/times")
                            .then().log().all()
                            .statusCode(201);
                }),

                dynamicTest("예약을 추가한다", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON).body(reservationParams)
                            .when().post("/reservations")
                            .then().log().all()
                            .statusCode(201)
                            .header("Location", "/reservations/1");
                }),

                dynamicTest("예약이 정상적으로 추가되었는지 확인한다", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200).body("size()", is(1));
                }),

                dynamicTest("id가 1인 예약을 삭제한다", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/reservations/1")
                            .then().log().all()
                            .statusCode(204);
                }),

                dynamicTest("예약이 정상적으로 삭제되었는지 확인한다", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200).body("size()", is(0));
                })
        );
    }
}
