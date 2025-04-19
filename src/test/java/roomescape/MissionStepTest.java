package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    @Test
    void home() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void readReservations() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    void add() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);

        String dummyName = "브라운";
        String dummyDateText = localDateTime.toLocalDate().toString();
        String dummyTimeText = localDateTime.toLocalTime().toString();

        Map<String, String> params = new HashMap<>();
        params.put("name", dummyName);
        params.put("date", dummyDateText);
        params.put("time", dummyTimeText);

        ValidatableResponse response = RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        response.body("id", is(1));
        response.body("name", is(dummyName));
        response.body("date", is(dummyDateText));
        response.body("time", is(dummyTimeText));
    }

    @Test
    void delete() {
        LocalDateTime localDateTime = LocalDateTime.now().plusDays(1);

        String dummyName = "브라운";
        String dummyDateText = localDateTime.toLocalDate().toString();
        String dummyTimeText = localDateTime.toLocalTime().toString();

        Map<String, String> params = new HashMap<>();
        params.put("name", dummyName);
        params.put("date", dummyDateText);
        params.put("time", dummyTimeText);

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }
}
