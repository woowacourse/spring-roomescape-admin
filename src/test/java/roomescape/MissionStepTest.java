package roomescape;

import static org.hamcrest.core.Is.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.Clock;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import roomescape.common.Constant;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class MissionStepTest {
    private final Map<String, String> CREATE_RESERVATION_PARAMS = Map.of(
            "name", "브라운",
            "date", "2025-08-05",
            "time", "15:40"
    );

    @Test
    void 어드민_페이지를_조회시_200을_반환한다() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 예약_정보를_조회한다() {
        for (int i = 0; i < 3; i++) {
            createReservationData();
        }
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(3));
    }

    @Test
    void 예약_정보를_생성한다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(CREATE_RESERVATION_PARAMS)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @Test
    void 예약_정보를_삭제한다() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(CREATE_RESERVATION_PARAMS)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

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

    private void createReservationData() {
        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(CREATE_RESERVATION_PARAMS)
                .when().post("/reservations");
    }
}
