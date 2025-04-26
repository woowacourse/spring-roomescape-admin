package roomescape.time;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.MethodMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ReservationTimeTest {

    @LocalServerPort
    private int port;

    @DirtiesContext(methodMode = MethodMode.AFTER_METHOD)
    @DisplayName("startAt 관련 api 테스트")
    @Test
    void 칠단계() {
        // given
        Map<String, String> params = new HashMap<>();
        params.put("startAt", "10:00");

        // when & then
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().port(port).log().all()
                .when().get("/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().port(port).log().all()
                .when().delete("/times/1")
                .then().log().all()
                .statusCode(200);
    }
}
