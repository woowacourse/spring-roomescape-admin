package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MissionStepTest {

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    void 이단계() {
        RestAssured.given().log().all()
                .when().get("/reservation")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(0));
    }

    @Test
    void 삼단계() {
        Map<String, String> params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "time", "15:40"
        );

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(1));

        RestAssured.given().log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK)
                .body("size()", is(0));
    }

    @Test
    void 리다이렉트() {
        RestAssured.given().log().all()
                .when().get("/")
                .then().log().all()
                .statusCode(HttpStatus.SC_OK);
    }
}
