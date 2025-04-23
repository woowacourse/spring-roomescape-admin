package roomescape.reservation;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class ReservationApiTest {

    private static final Map<String, String> RESERVATION_BODY = new HashMap<>();
    private static final Map<String, String> TIME_BODY = new HashMap<>();

    private int port;

    public ReservationApiTest(
            @LocalServerPort final int port
    ) {
        this.port = port;
    }

    @BeforeAll
    static void initParams() {
        RESERVATION_BODY.put("name", "브라운");
        RESERVATION_BODY.put("date", "2023-08-05");
        RESERVATION_BODY.put("timeId", "1");

        TIME_BODY.put("startAt", "10:00");
    }

    @DisplayName("예약을 생성하고, 200 OK를 응답")
    @Test
    void post() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));
    }

    @DisplayName("존재하는 모든 예약과 200 OK를 응답")
    @Test
    void get1() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);

        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().port(port).log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));
    }

    @DisplayName("예약이 존재하지 않는다면 200 OK와 빈 컬렉션 응답")
    @Test
    void get2() {
        RestAssured.given().port(port).log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("주어진 아이디에 해당하는 예약이 있다면 200 OK 응답")
    @Test
    void remove1() {
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(TIME_BODY)
                .when().post("/times")
                .then().log().all()
                .statusCode(200);
        RestAssured.given().port(port).log().all()
                .contentType(ContentType.JSON)
                .body(RESERVATION_BODY)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().port(port).log().all()
                .when().delete("/reservations/1")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("주어진 아이디에 해당하는 예약이 없다면 404로 응답한다.")
    @Test
    void remove2() {
        RestAssured.given().port(port).log().all()
                .when().delete("/reservations/100")
                .then().log().all()
                .statusCode(404);
    }
}
