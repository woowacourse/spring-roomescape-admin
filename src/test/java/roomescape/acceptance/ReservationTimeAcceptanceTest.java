package roomescape.acceptance;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.support.AcceptanceTest;

class ReservationTimeAcceptanceTest extends AcceptanceTest {
    private static final String PATH = "/times";

    @DisplayName("[7단계 - 시간 관리 기능]")
    @Test
    void step7() {
        // 시간을 등록한다.
        Map<String, String> params = Map.of(
                "startAt", "10:00"
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post(PATH)
                .then().log().all()
                .statusCode(201);

        // 등록된 시간을 조회한다.
        RestAssured.given().log().all()
                .when().get(PATH)
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        // 시간을 삭제한다.
        RestAssured.given().log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(204);
    }
}
