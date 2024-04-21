package roomescape;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.dto.ReservationRequest;
import roomescape.fixture.ReservationFixture;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationApiTest {
    @Test
    void get_reservations() {
        ReservationFixture.예약_생성(new ReservationRequest("조이썬", "2023-08-30", "10:30"));

        RestAssured.given()
                   .log()
                   .all()
                   .when()
                   .get("reservations")
                   .then()
                   .log()
                   .all()
                   .statusCode(200)
                   .body("size()", is(1));
    }

    @Test
    void create_reservation() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", "15:40");

        RestAssured.given()
                   .contentType(ContentType.JSON)
                   .body(params)
                   .when()
                   .post("/reservations")
                   .then()
                   .statusCode(201)
                   .body("id", is(1));
    }

    @Test
    void delete_reservation() {
        ReservationFixture.예약_생성(new ReservationRequest("조이썬", "2023-08-30", "10:30"));

        RestAssured.given()
                   .log()
                   .all()
                   .when()
                   .delete("/reservations/1")
                   .then()
                   .log()
                   .all()
                   .statusCode(204);
    }
}
