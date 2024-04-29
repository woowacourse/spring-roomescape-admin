package roomescape;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class EndToEndTest {

    @DisplayName("가능한 시간을 저장, 조회 및 삭제할 수 있다.")
    @TestFactory
    Stream<DynamicTest> timeTest() {
        return Stream.of(
                dynamicTest("시간을 저장할 수 있다.", () -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("startAt", "10:00");

                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(params)
                            .when().post("/times")
                            .then().log().all()
                            .statusCode(200);
                }),

                dynamicTest("시간을 조회할 수 있다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/times")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }),

                dynamicTest("시간을 삭제할 수 있다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/times/1")
                            .then().log().all()
                            .statusCode(200);

                    RestAssured.given().log().all()
                            .when().get("/times")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(0));
                })
        );
    }

    @DisplayName("예약을 저장, 조회 및 삭제할 수 있다.")
    @TestFactory
    Stream<DynamicTest> reservationTest() {
        return Stream.of(
                dynamicTest("예약을 저장할 수 있다.", () -> {
                    Map<String, String> params = new HashMap<>();
                    params.put("startAt", "10:00");

                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(params)
                            .when().post("/times")
                            .then().log().all()
                            .statusCode(200);

                    Map<String, Object> reservation = new HashMap<>();
                    reservation.put("name", "브라운");
                    reservation.put("date", "2023-08-05");
                    reservation.put("timeId", 1);

                    RestAssured.given().log().all()
                            .contentType(ContentType.JSON)
                            .body(reservation)
                            .when().post("/reservations")
                            .then().log().all()
                            .statusCode(200);
                }),

                dynamicTest("예약을 조회할 수 있다.", () -> {
                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(1));
                }),

                dynamicTest("예약을 삭제할 수 있다.", () -> {
                    RestAssured.given().log().all()
                            .when().delete("/reservations/1")
                            .then().log().all()
                            .statusCode(200);

                    RestAssured.given().log().all()
                            .when().get("/reservations")
                            .then().log().all()
                            .statusCode(200)
                            .body("size()", is(0));
                })
        );
    }
}
