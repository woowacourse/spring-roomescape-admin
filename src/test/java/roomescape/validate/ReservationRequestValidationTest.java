package roomescape.validate;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.ReservationAcceptanceTest;

@ReservationAcceptanceTest
class ReservationRequestValidationTest {

    @Test
    @DisplayName("이름이 비어있으면 예약 생성 실패 (400)")
    void should_fail_when_name_is_blank_test() {

        Map<String, Object> params = new HashMap<>();
        params.put("name", "");
        params.put("date", "2026-04-29");
        params.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("날짜가 없으면 예약 생성 실패 (400)")
    void should_fail_when_date_is_missing_test() {

        Map<String, Object> params = new HashMap<>();
        params.put("name", "홍길동");
        params.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("시간 ID가 null이면 예약 생성 실패 (400)")
    void should_fail_when_timeId_is_null_test() {

        Map<String, Object> params = new HashMap<>();
        params.put("name", "홍길동");
        params.put("date", "2026-04-29");
        params.put("timeId", null);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("올바른 요청이면 예약 생성은 성공 (200)")
    void should_create_reservation_successfully_test() {

        Map<String, String> time = new HashMap<>();
        time.put("startAt", "10:00");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(time)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        Map<String, Object> params = new HashMap<>();
        params.put("name", "홍길동");
        params.put("date", "2026-04-29");
        params.put("timeId", 1L);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

}
