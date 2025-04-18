package roomescape.reservation;

import static org.hamcrest.Matchers.is;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationApiTest {

    @DisplayName("어드민 페이지로 접근할 수 있다.")
    @Test
    void test1() {
        RestAssured.given().log().all()
                .when().get("/admin")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("어드민이 예약 관리 페이지에 접근한다.")
    @Test
    void test2() {
        RestAssured.given().log().all()
                .when().get("/admin/reservation")
                .then().log().all()
                .statusCode(200);
    }

    @DisplayName("모든 예약 정보를 반환한다.")
    @Test
    void test3() {
        RestAssured.given().log().all()
                .when().get("/reservations")
                .then().log().all()
                .statusCode(200)
                .body("size()", is(0));
    }

    @DisplayName("예약 정보를 추가한다.")
    @Test
    void test4() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "브라운");
        params.put("date", "2023-08-05");
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
    }

    @DisplayName("예약 삭제")
    @Nested
    class delete {

        @DisplayName("예약을 삭제한다.")
        @Test
        void test1() {
            Map<String, String> params = new HashMap<>();
            params.put("name", "브라운");
            params.put("date", "2023-08-05");
            params.put("time", "15:40");

            RestAssured.given().log().all()
                    .contentType(ContentType.JSON)
                    .body(params)
                    .when().post("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("id", is(1));

            RestAssured.given().log().all()
                    .when().delete("/reservations/1")
                    .then().log().all()
                    .statusCode(204);

            RestAssured.given().log().all()
                    .when().get("/reservations")
                    .then().log().all()
                    .statusCode(200)
                    .body("size()", is(0));
        }

        @DisplayName("존재하지 않는 예약을 삭제할 경우 NOT_FOUND 반환")
        @Test
        void test2() {
            RestAssured.given().log().all()
                    .when().delete("/reservations/4")
                    .then().log().all()
                    .statusCode(404);
        }
    }
}
