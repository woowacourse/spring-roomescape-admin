package roomescape.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class ReservationTimeAcceptanceTest extends BasicAcceptanceTest {
    @DisplayName("예약 시간을 저장했다가 삭제한다")
    @TestFactory
    Stream<DynamicTest> saveReservationTime_and_delete() {
        return Stream.of(
                dynamicTest("예약 시간 관리 페이지에 접속한다", this::accessReservationTimePage),
                dynamicTest("예약 시간을 저장한다", this::addReservationTime),
                dynamicTest("예약 시간을 모두 조회한다 (총 1개)", () -> checkReservationCount(1)),
                dynamicTest("예약 시간을 삭제한다", () -> deleteReservationTime(1)),
                dynamicTest("예약 시간을 모두 조회한다 (총 0개)", () -> checkReservationCount(0))
        );
    }

    private void accessReservationTimePage() {
        RestAssured.given().log().all()
                .when().get("/admin/time")
                .then().log().all()
                .statusCode(200);
    }

    private void addReservationTime() {
        Map<String, String> params = Map.of("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
    }

    private void checkReservationCount(int expectedCount) {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(expectedCount));
    }

    private void deleteReservationTime(final int reservationId) {
        RestAssured.given().log().all()
                .when().delete("/times/" + reservationId)
                .then().log().all()
                .statusCode(204);
    }
}
