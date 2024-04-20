package roomescape.acceptance;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

public class ReservationAcceptanceTest implements AcceptanceTest {

    @Test
    @DisplayName("[Step2, Step5] 예약 목록을 조회한다.")
    void getReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("[Step3, Step6] 예약을 추가하고 삭제한다.")
    @TestFactory
    Stream<DynamicTest> createThenDeleteReservation() {
        return Stream.of(
                dynamicTest("예약을 하나 생성한다.", this::createOneReservation),
                dynamicTest("예약이 하나 생성된 예약 목록을 조회한다.", this::getReservationsWithSizeOne),
                dynamicTest("예약을 하나 삭제한다.", this::deleteOneReservation),
                dynamicTest("예약 목록을 조회한다.", this::getReservations)
        );
    }

    void createOneReservation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2030-08-05");
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/reservations/1");
    }

    void getReservationsWithSizeOne() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    void deleteOneReservation() {
        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);
    }
}
