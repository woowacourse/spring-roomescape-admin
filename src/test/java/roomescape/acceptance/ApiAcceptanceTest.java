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

public class ApiAcceptanceTest implements AcceptanceTest {

    @Test
    @DisplayName("[Step7] 예약 시간을 생성한다.")
    void createReservationTime() {
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .header("Location", "/times/1");
    }

    @Test
    @DisplayName("[Step7] 예약 시간 목록을 조회한다.")
    void getReservationTimes() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }


    @TestFactory
    @DisplayName("[Step7] 예약 시간을 추가하고 삭제한다.")
    Stream<DynamicTest> createThenDeleteReservationTime() {
        return Stream.of(
                dynamicTest("예약을 하나 생성한다.", this::createReservationTime),
                dynamicTest("예약이 하나 생성된 예약 목록을 조회한다.", this::getReservationTimesWithSizeOne),
                dynamicTest("예약을 하나 삭제한다.", this::deleteOneReservationTime),
                dynamicTest("예약 목록을 조회한다.", this::getReservationTimes)
        );
    }

    void getReservationTimesWithSizeOne() {
        RestAssured.given().log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    void deleteOneReservationTime() {
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }

    @Test
    @DisplayName("[Step2, Step5] 예약 목록을 조회한다.")
    void getReservations() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @TestFactory
    @DisplayName("[Step3, Step6, Step8] 예약을 추가한다.")
    Stream<DynamicTest> createReservation() {
        return Stream.of(
                dynamicTest("예약 시간을 생성한다.", this::createReservationTime),
                dynamicTest("예약을 하나 생성한다.", this::createOneReservation)
        );
    }

    @TestFactory
    @DisplayName("[Step3, Step6, Step8] 예약을 추가하고 삭제한다.")
    Stream<DynamicTest> createThenDeleteReservation() {
        return Stream.of(
                dynamicTest("예약 시간을 생성한다.", this::createReservationTime),
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
        params.put("timeId", "1");

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
