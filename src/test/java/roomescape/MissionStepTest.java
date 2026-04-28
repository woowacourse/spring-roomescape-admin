package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    @DisplayName("예약을 조회한다.")
    void 예약_조회() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0)); // 아직 생성 요청이 없으니 0개
    }

    @Test
    @DisplayName("예약을 추가한다.")
    void 예약_추가() {
        Map<String, String> params = Map.of(
                "name", "Ace",
                "date", "2026-04-28",
                "time", "10:00"
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    @DisplayName("예약을 삭제한다.")
    void 예약_삭제() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(Map.of("name", "Ace", "date", "2026-04-29", "time", "10:00")).
                when().post("/reservations")
                .then().statusCode(200);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(204);

        RestAssured.given()
                .when().get("/reservations")
                .then()
                .body("size()", is(0));
    }
}
