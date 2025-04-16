package roomescape;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class MissionStepTest {

    private static final String DATE_FORMAT = "%d-%02d-%02d";

    @Test
    void 일단계() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void 이단계() {
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
    void 삼단계() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", makeTodayMessage());
        params.put("time", "15:40");

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(params)
                .when().post("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("id", is(1));

        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(1));

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

    @Nested
    class 예약_요청_유효성_검증 {

        @Test
        void 이름_null_검증_테스트() {
            Map<String, String> params = new HashMap<>();
            params.put("name", null);
            params.put("date", makeTodayMessage());
            params.put("time", "15:40");

            createBadReservation(params);
        }

        @Test
        void 이름_size_검증_테스트() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "잠실에사는비행기데코피크민");
            params.put("date", makeTodayMessage());
            params.put("time", "15:40");

            createBadReservation(params);
        }

        @Test
        void 날짜_null_검증_테스트() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "밍트");
            params.put("date", null);
            params.put("time", "15:40");

            createBadReservation(params);
        }

        @Test
        void 과거_날짜_검증_테스트() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "밍트");
            params.put("date", makeYesterdayMessage());
            params.put("time", "15:40");

            createBadReservation(params);
        }

        @Test
        void 시간_검증_테스트() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "밍트");
            params.put("date", makeTodayMessage());
            params.put("time", null);

            createBadReservation(params);
        }

        private void createBadReservation(final Map<String, String> params) {
            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(400);
        }
    }

    private String makeYesterdayMessage() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        return String.format(DATE_FORMAT, yesterday.getYear(), yesterday.getMonthValue(), yesterday.getDayOfMonth());
    }

    private String makeTodayMessage() {
        LocalDate today = LocalDate.now();
        return String.format(DATE_FORMAT, today.getYear(), today.getMonthValue(), today.getDayOfMonth());
    }
}
