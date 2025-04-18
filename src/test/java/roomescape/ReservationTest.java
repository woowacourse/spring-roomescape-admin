package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ReservationTest {

    @Test
    @DisplayName("예약에 대한 생성, 조회, 삭제를 진행한다.")
    void createAndReadAndDeleteReservation() {
        var params = Map.of(
                "name", "브라운",
                "date", "2023-08-05",
                "time", "15:40"
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured
                .given().log().all()
                .when()
                .get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

        RestAssured
                .given().log().all()
                .when()
                .delete("/reservations/1")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .when()
                .get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @Test
    @DisplayName("예약 생성시 이름이 비어있으면 예외가 발생한다.")
    void cannotCreateReservationWhenNameIsBlank() {
        var params = Map.of(
                "name", "",
                "date", "2023-08-05",
                "time", "15:40"
        );

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 생성시 날짜가 null이면 예외가 발생한다.")
    void cannotCreateReservationWhenDateIsNull() {
        var params = new HashMap<String, String>();
        params.put("name", "브라운");
        params.put("date", null);
        params.put("time", "15:40");

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(400);
    }

    @Test
    @DisplayName("예약 생성시 시간이 null이면 예외가 발생한다.")
    void cannotCreateReservationWhenTimeIsNull() {
        var params = new HashMap<String, String>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
        params.put("time", null);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when()
                .post("/reservations")
                .then().log().all()
                .statusCode(400);
    }
}
