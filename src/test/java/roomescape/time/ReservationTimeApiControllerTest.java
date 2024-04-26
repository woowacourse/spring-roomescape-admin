package roomescape.time;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationTimeApiControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void beforeEach() {
        RestAssured.port = port;
    }

    @TestFactory
    @DisplayName("시간을 추가하고 삭제합니다")
    Collection<DynamicTest> createAndDeleteTest() {
        Map<String, String> params = Map.of(
                "startAt", "10:00"
        );
        return List.of(
                DynamicTest.dynamicTest("시간을 저장한다", () -> {
                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(params)
                            .when().post("/times")
                            .then().log().all()
                            .statusCode(200);
                }),

                DynamicTest.dynamicTest("시간을 조회한다", () -> {
                    RestAssured.given().log().all()
                            .when().get("/times")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }),

                DynamicTest.dynamicTest("시간을 삭제한다", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/times/1")
                            .then().log().all()
                            .statusCode(200);
                })
        );
    }
}
