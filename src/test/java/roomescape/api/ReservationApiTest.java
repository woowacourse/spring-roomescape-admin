package roomescape.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationApiTest {

    @Test
    void 예약과_시간_연결() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        Map<String, String> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void 예약_등록_성공_테스트() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        Map<String, String> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("name", equalTo("브라운"))
                .body("date", equalTo("2023-08-05"))
                .body("time.start_at", equalTo("10:00:00"));
    }

    @Test
    void 예약_조회_성공_테스트() {

        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        Map<String, String> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("[0].name", equalTo("브라운"))
                .body("[0].date", equalTo("2023-08-05"))
                .body("[0].time.start_at", equalTo("10:00:00"));
    }

    @Test
    void 예약_삭제_성공_테스트() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        RestAssured.given().contentType(ContentType.JSON)
                .body(Map.of("startAt", "10:00"))
                .when().post("/times")
                .then().statusCode(200);

        RestAssured.given().contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().statusCode(200);

        RestAssured.given()
                .when().delete("/reservations/1")
                .then().statusCode(200);

        RestAssured.given()
                .when().get("/reservations")
                .then().statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void 존재하지_않는_예약시간으로_등록시_400_에러가_난다() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 1);

        RestAssured.given().contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().statusCode(400);
    }

    @ParameterizedTest
    @MethodSource("reservationFailCase")
    void 예약시_이름_날짜_시간ID_없이_예약하면_400_에러가_난다(Map<String, Object> reservationRequestDto) {
        RestAssured.given().contentType(ContentType.JSON)
                .body(Map.of("startAt", "10:00"))
                .when().post("/times")
                .then().statusCode(200);

        RestAssured.given().contentType(ContentType.JSON)
                .body(reservationRequestDto)
                .when().post("/reservations")
                .then().statusCode(400);
    }

    static Stream<Arguments> reservationFailCase() {
        return Stream.of(
                Arguments.of(Map.of( "date", "2023-09-09", "timeId", 1)),
                Arguments.of(Map.of("name", "브라운", "timeId", 1)),
                Arguments.of(Map.of("name", "브라운", "date", "2023-09-09"))
        );
    }
}
