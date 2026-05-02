package roomescape.reservation;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationControllerTest {

    @Test
    void 존재하지_않는_예약시간_id가_입력되면_Not_Found_에러를_반환한다() {
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", 999);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(404);
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    void 이름에_빈_값이_입력되면_Bad_Request_에러를_반환한다(String name) {
        Long timeId = 예약시간_생성();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", name);
        reservation.put("date", "2023-08-05");
        reservation.put("timeId", timeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @ParameterizedTest
    @ValueSource(strings = {"2023-13-05", "abc"})
    void 양식에_맞지_않은_날짜가_입력되면_Bad_Request_에러를_반환한다(String date) {
        Long timeId = 예약시간_생성();
        Map<String, Object> reservation = new HashMap<>();
        reservation.put("name", "브라운");
        reservation.put("date", date);
        reservation.put("timeId", timeId);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservation)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    private Long 예약시간_생성() {
        Map<String, Object> reservationTime = new HashMap<>();
        reservationTime.put("startAt", "10:00");

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(reservationTime)
                .when().post("/times")
                .then().log().all()
                .statusCode(201)
                .extract()
                .jsonPath()
                .getLong("id");
    }
}
