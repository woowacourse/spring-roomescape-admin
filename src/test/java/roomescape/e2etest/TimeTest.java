package roomescape.e2etest;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class TimeTest {

    @LocalServerPort
    int port;

    private Map<String, String> timeParams;

    @BeforeEach
    public void setUp() {
        timeParams = new HashMap<>();
        timeParams.put("startAt", "10:00");
    }

    @DisplayName("time 추가 및 삭제를 올바르게 진행한다")
    @Test
    void testTimeCreateAndDelete() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(timeParams)
                .when().post("http://localhost:" + port + "/times")
                .then().log().all()
                .statusCode(201);

        RestAssured.given().log().all()
                .when().get("http://localhost:" + port + "/times")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("http://localhost:" + port + "/times/1")
                .then().log().all()
                .statusCode(204);
    }
}
